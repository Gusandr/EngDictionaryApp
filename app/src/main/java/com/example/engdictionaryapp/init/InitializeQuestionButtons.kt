package com.example.engdictionaryapp.init

import android.app.Activity
import androidx.core.view.isVisible
import com.example.engdictionaryapp.R
import com.example.engdictionaryapp.databinding.ActivityCloseBinding
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.models.Variant
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.ui.AnswerIndicator
import com.example.engdictionaryapp.ui.QuestionIndicator

/*
Передавать контекст вместо активити
Добавить binding (скорее всего)
Переименовать на InitializeQuestion, вместо App на что-то

 */

class InitializeQuestionButtons(
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

    private val variants = getVariants(binding)
    private val context = activity.applicationContext


    fun initialize() {
        getVariants(binding)
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

                bindingClose.btnContinueMain?.setOnClickListener {
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

    companion object {
        fun getVariants(binding: ActivityLearnWordBinding): List<Variant> {
            val variants = mutableListOf<Variant>()

            with(binding) {
                val variantsList = listOf(
                    Variant(
                        layoutAnswer1,
                        tvVariantNumber1,
                        tvVariantValue1
                    ),
                    Variant(
                        layoutAnswer2,
                        tvVariantNumber2,
                        tvVariantValue2
                    ),
                    Variant(
                        layoutAnswer3,
                        tvVariantNumber3,
                        tvVariantValue3
                    ),
                    Variant(
                        layoutAnswer4,
                        tvVariantNumber4,
                        tvVariantValue4
                    )
                )

                for (variant in variantsList)
                    variants.add(variant)
            }

            return variants
        }
    }
}