package com.example.engdictionaryapp.ui

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import com.example.engdictionaryapp.*
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.models.Question
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import com.example.engdictionaryapp.ui.util.QuestionIndicator
import com.example.engdictionaryapp.ui.util.VariantViewHelper
import com.example.engdictionaryapp.ui.util.ViewUtils
import java.util.Locale
import javax.inject.Inject

class QuestionIndicatorImpl @Inject constructor(
    private val context: Context,
    private val binding: ActivityLearnWordBinding,
    private val learnWordTrainer: LearnWordTrainer
) : QuestionIndicator {

    companion object {
        private const val MAX_VALID_WIDTH_FOR_TV_SCORE = 219
        private const val MIN_VALID_WIDTH_FOR_TV_SCORE = 20
        private const val MIN_VALUE_TO_CHANGE_COLOR = 120
    }

    override fun showNextQuestion() {
        val result = learnWordTrainer.getResult()
        val ratioOfResult = countingRatioOfResult()
        val onePercentForTvScore = countingOnePercentForTvScore(ratioOfResult)
        val firstQuestion = learnWordTrainer.nextQuestion()

        with(binding) {
            if (firstQuestion == null) {
                displayGameResult(result)
                return
            }

            updateUI(firstQuestion, result, onePercentForTvScore)
            setupVariants(firstQuestion)
        }
    }

    private fun ActivityLearnWordBinding.updateUI(
        question: Question,
        result: Int,
        onePercentForTvScore: Double
    ) {
        btnSkip.isVisible = true
        tvQueryWord.isVisible = true
        tvQueryWord.text = question.correctAnswer.original
        tvResultCount.text = result.toString()

        val widthPx = ViewUtils.dpToPx(
            onePercentForTvScore.toInt().coerceAtLeast(MIN_VALID_WIDTH_FOR_TV_SCORE),
            context
        )
        tvScore.doOnLayout {
            tvScore.layoutParams.width = widthPx
            tvScore.requestLayout()
        }

        updateTextColor(widthPx)
    }

    private fun ActivityLearnWordBinding.updateTextColor(widthPx: Int) {
        tvResultCount.setTextColor(
            ContextCompat.getColor(
                context,
                if (widthPx >= ViewUtils.dpToPx(MIN_VALUE_TO_CHANGE_COLOR, context))
                    R.color.white
                else
                    R.color.black
            )
        )
    }

    private fun ActivityLearnWordBinding.setupVariants(question: Question) {
        VariantViewHelper.getVariants(this).forEachIndexed { index, element ->
            with(element) {
                tvVariantValue.text = question.variants[index].translate

                layoutAnswer.setOnClickListener {
                    AnswerIndicator(context, this, this@QuestionIndicatorImpl.binding)
                        .markAnswer(learnWordTrainer.checkAnswer(index))
                    clearClickListeners()
                }
            }
        }
    }

    private fun clearClickListeners() {
        VariantViewHelper.getVariants(binding).forEach {
            it.layoutAnswer.setOnClickListener(null)
        }
    }

    private fun displayGameResult(result: Int) {
        with(binding) {
            tvQueryWord.isVisible = false
            btnFinish.isVisible = true
            layoutVariantsAnswer.isVisible = false
            btnFinish.text =
                context.resources.getString(R.string.button_complete)
            btnSkip.isVisible = false

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

    private fun countingRatioOfResult(): Double {
        return (learnWordTrainer.getDictionarySize() - learnWordTrainer.getDictionarySizeNotLearned()).toDouble() / learnWordTrainer.getDictionarySize()
    }

    private fun countingOnePercentForTvScore(ratioOfResul: Double): Double {
        return MAX_VALID_WIDTH_FOR_TV_SCORE.toDouble() * ratioOfResul
    }
}