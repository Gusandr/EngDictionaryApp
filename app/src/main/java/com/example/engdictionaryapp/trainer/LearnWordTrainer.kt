package com.example.engdictionaryapp.trainer

import com.example.engdictionaryapp.models.Question
import com.example.engdictionaryapp.models.Word
import com.example.engdictionaryapp.trainer.data.Dictionary

const val NUMBER_OF_ANSWERS = 4

object LearnWordTrainer {
    private val dictionary: List<Word>
        get() = Dictionary.words
    private var currentQuestion: Question = Question(arrayOf(), Word("null", "null"))
    var result = 0

    fun nextQuestion(): Question? {
        val validDictionary =
            dictionary.filterNot { it.learned }.shuffled()

        if (validDictionary.isEmpty() || validDictionary.size < NUMBER_OF_ANSWERS)
            return null

        val readyDictionary = Array(NUMBER_OF_ANSWERS) { Word("null", "null") }

        for ((i) in readyDictionary.withIndex()) {
            var word: Word
            do {
                word = validDictionary.random()
            } while (i < validDictionary.size && readyDictionary.contains(word))
            readyDictionary[i] = word
        }

        if (readyDictionary.isEmpty() || readyDictionary.size < NUMBER_OF_ANSWERS)
            return null

        currentQuestion = Question(
            readyDictionary,
            readyDictionary.random()
        )
        currentQuestion.correctAnswer.learned = true
        return currentQuestion
    }

    fun checkAnswer(answerId: Int): Boolean {
        if (answerId !in currentQuestion.variants.indices)
            throw IndexOutOfBoundsException(
                "answerId ($answerId) goes outside the currentQuestion.variants.indices " +
                        "(${currentQuestion.variants.indices})!"
            )

        val isCorrectAnswer = currentQuestion.variants[answerId] == currentQuestion.correctAnswer
        if (isCorrectAnswer)
            result++
        return isCorrectAnswer
    }

    fun zeroing() {
        result = 0
        dictionary.map { it.learned = false }
    }

    fun getDictionarySizeNotLearned() = dictionary.filterNot { it.learned }.size
    fun getDictionarySize() = dictionary.size
}