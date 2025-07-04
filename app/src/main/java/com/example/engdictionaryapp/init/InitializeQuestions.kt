package com.example.engdictionaryapp.init

import android.app.Activity
import androidx.core.view.isVisible
import com.example.engdictionaryapp.R
import com.example.engdictionaryapp.databinding.ActivityCloseBinding
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.ui.AnswerIndicator
import com.example.engdictionaryapp.ui.QuestionIndicator
import com.example.engdictionaryapp.ui.VariantViewHelper

class InitializeQuestions(
    private val activity: Activity,
    private val binding: ActivityLearnWordBinding,
    private val learnWordTrainer: LearnWordTrainer
) {

    private val questionIndicator by lazy(LazyThreadSafetyMode.NONE) {
        QuestionIndicator(
            activity.applicationContext,
            binding,
            learnWordTrainer
        )
    }

    private val variants = VariantViewHelper.getVariants(binding)
    private val context = activity.applicationContext


    fun initialize() {
        initializeButtons()
        questionIndicator.showNextQuestion()
    }

    private fun initializeButtons() {
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
                        activity.layoutInflater
                    )
                activity.setContentView(bindingClose.root)

                bindingClose.btnContinueMain.setOnClickListener {
                    learnWordTrainer.zeroing()
                    tvQueryWord.isVisible = true
                    layoutVariantsAnswer.isVisible = true
                    btnSkip.text = context.resources.getString(R.string.button_skip)
                    layoutResult.isVisible = false
                    ivFinish.isVisible = false
                    tvFinish.isVisible = false
                    variants.forEach { AnswerIndicator(context, it, binding).markAnswer(null) }
                    questionIndicator.showNextQuestion()

                    activity.setContentView(binding.root)
                }
            }
        }
    }
}