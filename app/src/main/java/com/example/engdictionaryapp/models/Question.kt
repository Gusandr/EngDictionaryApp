package com.example.engdictionaryapp.models

data class Question(val variants: Array<Word>, val correctAnswer: Word) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (!variants.contentEquals(other.variants)) return false
        if (correctAnswer != other.correctAnswer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = variants.contentHashCode()
        result = 31 * result + correctAnswer.hashCode()
        return result
    }
}