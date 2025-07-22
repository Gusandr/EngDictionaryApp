package com.example.engdictionaryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.ui.AnswerIndicator
import com.example.engdictionaryapp.ui.util.QuestionIndicator
import com.example.engdictionaryapp.ui.util.VariantViewHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainContentFragment : Fragment() {
    private var _binding: ActivityLearnWordBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("binding is null!")

    private lateinit var questionIndicator: QuestionIndicator

    @Inject
    @JvmSuppressWildcards
    lateinit var questionIndicatorFactory: (ActivityLearnWordBinding) -> QuestionIndicator

    @Inject
    lateinit var learnWordTrainer: LearnWordTrainer

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityLearnWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionIndicator = questionIndicatorFactory(binding)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                questionIndicator.showNextQuestion(state = state)
            }
        }

        initializeButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeButtons() {
        val variants = VariantViewHelper.getVariants(binding)
        val context = requireContext()

        with(binding) {
            btnContinue.setOnClickListener {
                layoutResult.isVisible = false
                variants.forEach { variant ->
                    AnswerIndicator(
                        context = context,
                        variant = variant,
                        binding = binding
                    ).markAnswer(null)
                }
                viewModel.nextQuestion()
            }

            btnSkip.setOnClickListener {
                viewModel.nextQuestion()
            }

            ibClose.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, CloseFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}