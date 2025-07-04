package com.example.engdictionaryapp.ui

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.engdictionaryapp.*
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import java.util.Locale

const val MAX_VALID_WIDTH_FOR_TV_SCORE = 219
const val MIN_VALID_WIDTH_FOR_TV_SCORE = 20
const val MIN_VALUE_TO_CHANGE_COLOR = 120

class QuestionIndicator(
    private val context: Context,
    private val binding: ActivityLearnWordBinding,
    private val learnWordTrainer: LearnWordTrainer
) {
    fun showNextQuestion() {
        val result = learnWordTrainer.getResult()
        val ratioOfResul = countingRatioOfResul()
        val onePercentForTvScore = countingOnePercentForTvScore(ratioOfResul)
        val firstQuestion = learnWordTrainer.nextQuestion()

        with(binding) {
            if (firstQuestion == null) {
                displayGameResult(result)
                return
            }

            btnSkip.isVisible = true
            tvQueryWord.isVisible = true
            tvQueryWord.text = firstQuestion.correctAnswer.original
            tvResultCount.text = result.toString()
            tvScore.layoutParams.width =
                ViewUtils.dpToPx(
                    onePercentForTvScore.toInt().coerceAtLeast(MIN_VALID_WIDTH_FOR_TV_SCORE),
                    context
                )
            tvScore.requestLayout()

            tvResultCount.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (tvScore.layoutParams.width >= ViewUtils.dpToPx(
                            MIN_VALUE_TO_CHANGE_COLOR,
                            context
                        )
                    )
                        R.color.white
                    else
                        R.color.black
                )
            )

            val variants = VariantViewHelper.getVariants(binding)

            variants.forEachIndexed { index, element ->
                with(element) {
                    tvVariantValue.text = firstQuestion.variants[index].translate

                    layoutAnswer.setOnClickListener {
                        AnswerIndicator(
                            context,
                            this,
                            binding
                        ).markAnswer(
                            learnWordTrainer.checkAnswer(
                                index
                            )
                        )
                        variants.forEach { it.layoutAnswer.setOnClickListener {} }
                    }

                }
            }
        }
    }

    private fun displayGameResult(result: Int) {
        with(binding) {
            tvQueryWord.isVisible = false
            btnSkip.isVisible = true
            layoutVariantsAnswer.isVisible = false
            btnSkip.text =
                context.resources.getString(R.string.button_complete)
            tvResultCount.setTextColor(
                ContextCompat.getColor(
                    context, R.color.white
                )
            )
            tvScore.layoutParams.width =
                ViewUtils.dpToPx(
                    MAX_VALID_WIDTH_FOR_TV_SCORE,
                    context
                )
            tvScore.requestLayout()

            val format = context.resources.getString(R.string.title_finish_score)
            val formattedString = String.format(
                Locale.getDefault(),
                format,
                result,
                learnWordTrainer.getDictionarySize()
            )
            tvFinish.text = formattedString
            tvFinish.isVisible = true
            Glide.with(context)
                .load(R.drawable.giphy) // GIF
                .apply(RequestOptions.fitCenterTransform())
                .into(ivFinish)
            ivFinish.isVisible = true
        }
    }

    private fun countingRatioOfResul(): Double {
        return (learnWordTrainer.getDictionarySize() - learnWordTrainer.getDictionarySizeNotLearned()).toDouble() / learnWordTrainer.getDictionarySize()
    }

    private fun countingOnePercentForTvScore(ratioOfResul: Double): Double {
        return MAX_VALID_WIDTH_FOR_TV_SCORE.toDouble() * ratioOfResul
    }
}