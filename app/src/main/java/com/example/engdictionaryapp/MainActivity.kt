package com.example.engdictionaryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.engdictionaryapp.databinding.ActivityCloseBinding
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.ui.AnswerIndicator
import com.example.engdictionaryapp.ui.util.QuestionIndicator
import com.example.engdictionaryapp.ui.util.VariantViewHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var privateBinding: ActivityLearnWordBinding? = null
    val binding
        get() = privateBinding
            ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null!")

    private lateinit var questionIndicator: QuestionIndicator

    @Inject
    @JvmSuppressWildcards
    lateinit var questionIndicatorFactory: (ActivityLearnWordBinding) -> QuestionIndicator

    @Inject
    lateinit var learnWordTrainer: LearnWordTrainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        privateBinding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionIndicator = questionIndicatorFactory(binding)
        questionIndicator.showNextQuestion()

        initializeButtons()
    }

    override fun onDestroy() {
        super.onDestroy()
        privateBinding = null
    }

    private fun initializeButtons() {
        val variants = VariantViewHelper.getVariants(binding)
        val context = this@MainActivity.applicationContext

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
                questionIndicator.showNextQuestion()
            }

            btnSkip.setOnClickListener {
                questionIndicator.showNextQuestion()
            }

            ibClose.setOnClickListener {
                val bindingClose =
                    ActivityCloseBinding.inflate(
                        this@MainActivity.layoutInflater
                    )
                this@MainActivity.setContentView(bindingClose.root)

                bindingClose.btnContinueMain.setOnClickListener {
                    tvQueryWord.isVisible = true
                    layoutVariantsAnswer.isVisible = true
                    layoutResult.isVisible = false
                    ivFinish.isVisible = false
                    tvFinish.isVisible = false
                    btnFinish.isVisible = false

                    tvResultCount.text = "0"
                    tvScore.layoutParams.width = 0
                    tvScore.requestLayout()
                    btnSkip.text = context.resources.getString(R.string.button_skip)
                    variants.forEach { AnswerIndicator(context, it, binding).markAnswer(null) }
                    learnWordTrainer.zeroing()
                    questionIndicator.showNextQuestion()

                    this@MainActivity.setContentView(binding.root)
                }
            }
        }
    }
}