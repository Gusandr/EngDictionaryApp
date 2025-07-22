package com.example.engdictionaryapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.engdictionaryapp.trainer.domain.LearnWordTrainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val learnWordTrainer: LearnWordTrainer) :
    ViewModel() {
    private val _state = MutableStateFlow(
        MainState(null, 0, 0.0)
    )
    val state = _state

    var showCloseScreen by mutableStateOf(false)

    init {
        nextQuestion()
    }

    fun nextQuestion() {
        val result = learnWordTrainer.getResult()
        val ratioOfResult = countingRatioOfResult()
        val onePercentForTvScore = countingOnePercentForTvScore(ratioOfResult)
        val firstQuestion = learnWordTrainer.nextQuestion()

        _state.value = MainState(firstQuestion, result, onePercentForTvScore)
    }

    private fun countingRatioOfResult(): Double {
        return (learnWordTrainer.getDictionarySize() - learnWordTrainer.getDictionarySizeNotLearned()).toDouble() / learnWordTrainer.getDictionarySize()
    }

    private fun countingOnePercentForTvScore(ratioOfResul: Double): Double {
        return MAX_VALID_WIDTH_FOR_TV_SCORE.toDouble() * ratioOfResul
    }

    companion object {
        private const val MAX_VALID_WIDTH_FOR_TV_SCORE = 219
        private const val MIN_VALID_WIDTH_FOR_TV_SCORE = 20
        private const val MIN_VALUE_TO_CHANGE_COLOR = 120
    }
}