package com.example.engdictionaryapp.init

import androidx.core.view.isVisible
import com.example.engdictionaryapp.MainActivity
import com.example.engdictionaryapp.R
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.models.Variant
import com.example.engdictionaryapp.trainer.data.Dictionary
import com.example.engdictionaryapp.ui.AnswerIndicator
import com.example.engdictionaryapp.ui.QuestionIndicator

class InitializeApp(private val activity: MainActivity) {
    fun initialize() {
        with(activity) {
            privateBinding = ActivityLearnWordBinding.inflate(layoutInflater)
            Dictionary.fillWords(resources.openRawResource(R.raw.words))
            initializeVariants()
            initializeButtons()
            QuestionIndicator(activity).showNextQuestion()
        }
    }

    private fun initializeButtons() {
        with(activity) {
            with(activity.binding) {
                btnContinue.setOnClickListener {
                    layoutResult.isVisible = false
                    variants.forEach { AnswerIndicator(activity, it).markAnswer(null) }
                    QuestionIndicator(activity).showNextQuestion()
                }

                btnSkip.setOnClickListener {
                    QuestionIndicator(activity).showNextQuestion()
                }

                ibClose.setOnClickListener {
                    val bindingClose =
                        com.example.engdictionaryapp.databinding.ActivityCloseBinding.inflate(
                            layoutInflater
                        )
                    setContentView(bindingClose.root)

                    bindingClose.btnContinueMain?.setOnClickListener {
                        com.example.engdictionaryapp.trainer.LearnWordTrainer.zeroing()
                        tvQueryWord.isVisible = true
                        layoutVariantsAnswer.isVisible = true
                        btnSkip.text = resources.getString(R.string.button_skip)
                        layoutResult.isVisible = false
                        ivFinish.isVisible = false
                        tvFinish.isVisible = false
                        variants.forEach { AnswerIndicator(activity, it).markAnswer(null) }
                        QuestionIndicator(activity).showNextQuestion()

                        setContentView(binding.root)
                    }
                }
            }
        }
    }

    private fun initializeVariants() {
        with(activity) {
            for (variant in listOf(
                Variant(
                    binding.layoutAnswer1,
                    binding.tvVariantNumber1,
                    binding.tvVariantValue1
                ),
                Variant(
                    binding.layoutAnswer2,
                    binding.tvVariantNumber2,
                    binding.tvVariantValue2
                ),
                Variant(
                    binding.layoutAnswer3,
                    binding.tvVariantNumber3,
                    binding.tvVariantValue3
                ),
                Variant(
                    binding.layoutAnswer4,
                    binding.tvVariantNumber4,
                    binding.tvVariantValue4
                )
            ))
                variants.add(variant)
        }
    }
}