package com.example.engdictionaryapp.ui.util

import com.example.engdictionaryapp.MainState

interface QuestionIndicator {
    fun showNextQuestion(state: MainState)
}