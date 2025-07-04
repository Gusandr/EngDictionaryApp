package com.example.engdictionaryapp.trainer.data

import com.example.engdictionaryapp.models.Word

interface WordProvider {
    fun getWords(): List<Word>
}