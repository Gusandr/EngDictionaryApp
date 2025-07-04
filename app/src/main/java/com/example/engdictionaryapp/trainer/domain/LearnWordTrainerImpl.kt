package com.example.engdictionaryapp.trainer.domain

import com.example.engdictionaryapp.models.Question
import com.example.engdictionaryapp.models.Word
import com.example.engdictionaryapp.trainer.data.DictionaryJSON
import com.example.engdictionaryapp.trainer.data.WordProvider

const val NUMBER_OF_ANSWERS = 4

interface LearnWordTrainer {
    fun nextQuestion(): Question?
    fun checkAnswer(answerId: Int): Boolean
    fun zeroing()
    fun getDictionarySizeNotLearned(): Int
    fun getDictionarySize(): Int
    fun getResult(): Int
}

class LearnWordTrainerImpl(private val wordProvider: WordProvider) : LearnWordTrainer {
    private val dictionary: List<Word>
        get() = wordProvider.getWords()
    private var currentQuestion: Question = Question(arrayOf(), Word("null", "null"))
    private var result = 0

    override fun nextQuestion(): Question? {
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

    override fun checkAnswer(answerId: Int): Boolean {
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

    override fun zeroing() {
        result = 0
        dictionary.map { it.learned = false }
    }

    override fun getDictionarySizeNotLearned() = dictionary.filterNot { it.learned }.size
    override fun getDictionarySize() = dictionary.size
    override fun getResult(): Int = result
}