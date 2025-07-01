package com.example.engdictionaryapp.ui

import androidx.core.content.ContextCompat
import com.example.engdictionaryapp.*
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.engdictionaryapp.MainActivity
import com.example.engdictionaryapp.trainer.LearnWordTrainer
import java.util.Locale

class QuestionIndicator(private val activity: MainActivity) {
    fun showNextQuestion() {
        with(activity) {
            val result = LearnWordTrainer.result
            val maxValidWidthForTvScore = 219
            val minValidWidthForTvScore = 20
            val minValueToChangeColor = 120
            val ratioOfResul =
                (LearnWordTrainer.getDictionarySize() - LearnWordTrainer.getDictionarySizeNotLearned()).toDouble() / LearnWordTrainer.getDictionarySize()
            val onePercentForTvScore = maxValidWidthForTvScore.toDouble() * ratioOfResul
            val firstQuestion = LearnWordTrainer.nextQuestion()

            with(binding) {
                if (firstQuestion == null) {
                    tvQueryWord.isVisible = false
                    btnSkip.isVisible = true
                    layoutVariantsAnswer.isVisible = false
                    btnSkip.text =
                        resources.getString(R.string.button_complete)
                    tvResultCount.setTextColor(
                        ContextCompat.getColor(
                            activity, R.color.white
                        )
                    )
                    tvScore.layoutParams.width =
                        ViewUtils.dpToPx(
                            maxValidWidthForTvScore,
                            activity
                        )
                    tvScore.requestLayout()

                    val format = resources.getString(R.string.title_finish_score)
                    val formattedString = String.format(Locale.getDefault(), format, result, LearnWordTrainer.getDictionarySize())
                    tvFinish.text = formattedString
                    tvFinish.isVisible = true
                    Glide.with(activity)
                        .load(R.drawable.giphy) // Replace with your GIF resource ID
                        .apply(RequestOptions.fitCenterTransform())
                        .into(ivFinish)
                    ivFinish.isVisible = true
                    return
                }

                btnSkip.isVisible = true
                tvQueryWord.isVisible = true
                tvQueryWord.text = firstQuestion.correctAnswer.original
                tvResultCount.text = result.toString()
                tvScore.layoutParams.width =
                    ViewUtils.dpToPx(
                        onePercentForTvScore.toInt().coerceAtLeast(minValidWidthForTvScore),
                        activity
                    )
                tvScore.requestLayout()

                tvResultCount.setTextColor(
                    ContextCompat.getColor(
                        activity,
                        if (tvScore.layoutParams.width >= ViewUtils.dpToPx(
                                minValueToChangeColor,
                                activity
                            )
                        )
                            R.color.white
                        else
                            R.color.black
                    )
                )


                variants.forEachIndexed { index, element ->
                    with(element) {
                        tvVariantValue.text = firstQuestion.variants[index].translate

                        layoutAnswer.setOnClickListener {
                            AnswerIndicator(
                                activity,
                                this
                            ).markAnswer(
                                LearnWordTrainer.checkAnswer(
                                    index
                                )
                            )
                            variants.forEach { it.layoutAnswer.setOnClickListener {} }
                        }

                    }
                }
            }
        }
    }
}