package com.example.engdictionaryapp.trainer.data

import com.example.engdictionaryapp.models.Word

interface WordsProvider {
    fun getWords(): List<Word>
}