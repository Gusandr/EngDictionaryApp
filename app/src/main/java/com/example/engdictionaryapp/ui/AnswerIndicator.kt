package com.example.engdictionaryapp.ui

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.engdictionaryapp.MainActivity
import com.example.engdictionaryapp.R
import com.example.engdictionaryapp.models.Variant

class AnswerIndicator(private val activity: MainActivity, private val variant: Variant) {
    fun markAnswer(isCorrect: Boolean?) {
        with(variant) {
            layoutAnswer.background = ContextCompat.getDrawable(
                activity,
                when (isCorrect) {
                    true -> R.drawable.shape_rounded_containers_correct
                    false -> R.drawable.shape_rounded_containers_wrong
                    else -> R.drawable.shape_rounded_containers
                }
            )

            tvVariantNumber.apply {
                background = ContextCompat.getDrawable(
                    activity,
                    when (isCorrect) {
                        true -> R.drawable.shape_rounded_variants_correct
                        false -> R.drawable.shape_rounded_variants_wrong
                        else -> R.drawable.shape_rounded_variants
                    }
                )
                setTextColor(
                    ContextCompat.getColor(
                        activity,
                        when (isCorrect) {
                            null -> R.color.textVariantsColor
                            else -> R.color.white
                        }
                    )
                )
            }

            tvVariantValue.setTextColor(
                ContextCompat.getColor(
                    activity,
                    when (isCorrect) {
                        true -> R.color.correctAnswerColor
                        false -> R.color.wrongAnswerColor
                        else -> R.color.textVariantsColor
                    }
                )
            )
        }
        if (isCorrect != null)
            showResultMenu(isCorrect)
    }

    private fun showResultMenu(isCorrect: Boolean) {
        with(activity) {
            val color: Int
            val message: String
            val resultIconRes: Int

            when (isCorrect) {
                true -> {
                    color = ContextCompat.getColor(this, R.color.correctAnswerColor)
                    message = resources.getString(R.string.title_correct)
                    resultIconRes = R.drawable.ic_correct
                }
                false -> {
                    color = ContextCompat.getColor(this, R.color.wrongAnswerColor)
                    message = resources.getString(R.string.title_wrong)
                    resultIconRes = R.drawable.ic_wrong
                }
            }

            with(binding) {
                btnSkip.isVisible = false
                layoutResult.isVisible = true
                btnContinue.setTextColor(color)
                layoutResult.setBackgroundColor(color)
                tvResultMessage.text = message
                ivResultIcon.setImageResource(resultIconRes)
            }
        }
    }
}