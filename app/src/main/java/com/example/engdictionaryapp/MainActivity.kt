package com.example.engdictionaryapp

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.engdictionaryapp.databinding.ActivityCloseBinding
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.ui.AnswerIndicator
import com.example.engdictionaryapp.ui.CloseScreen
import com.example.engdictionaryapp.ui.util.QuestionIndicator
import com.example.engdictionaryapp.ui.util.VariantViewHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var privateBinding: ActivityLearnWordBinding? = null
    val binding get() = privateBinding ?: throw IllegalStateException("Binding must not be null!")

    private lateinit var closeComposeView: ComposeView
    private lateinit var closeContainer: FrameLayout
    private lateinit var rootContainer: FrameLayout

    private lateinit var questionIndicator: QuestionIndicator

    @Inject
    @JvmSuppressWildcards
    lateinit var questionIndicatorFactory: (ActivityLearnWordBinding) -> QuestionIndicator

    @Inject
    lateinit var learnWordTrainer: LearnWordTrainer

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        privateBinding = ActivityLearnWordBinding.inflate(layoutInflater)

        rootContainer = FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        rootContainer.addView(
            binding.root,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )

        closeContainer = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            isVisible = false
        }
        rootContainer.addView(closeContainer)

        setContentView(rootContainer)

        closeComposeView = ComposeView(this).apply {
            setContent {
                CloseScreen {
                    hideCloseScreen()
                }
            }
        }

        questionIndicator = questionIndicatorFactory(binding)

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                questionIndicator.showNextQuestion(state = state)
            }
        }

        initializeButtons()
    }

    private fun showCloseScreen() {
        closeContainer.removeAllViews()
        closeContainer.addView(closeComposeView)
        closeContainer.isVisible = true
    }

    private fun hideCloseScreen() {
        closeContainer.isVisible = false
        resetState()
    }

    private fun resetState() {
        with(binding) {
            tvQueryWord.isVisible = true
            layoutVariantsAnswer.isVisible = true
            layoutResult.isVisible = false
            ivFinish.isVisible = false
            tvFinish.isVisible = false
            btnFinish.isVisible = false

            tvResultCount.text = "0"
            val params = tvScore.layoutParams
            params.width = 0
            tvScore.layoutParams = params
            btnSkip.text = getString(R.string.button_skip)

            val variants = VariantViewHelper.getVariants(binding)
            variants.forEach { variant ->
                AnswerIndicator(this@MainActivity, variant, binding).markAnswer(null)
            }

            learnWordTrainer.zeroing()

            viewModel.nextQuestion()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        rootContainer.removeAllViews()
        privateBinding = null
    }

    private fun initializeButtons() {
        binding.ibClose.setOnClickListener {
            showCloseScreen()
        }
    }
}