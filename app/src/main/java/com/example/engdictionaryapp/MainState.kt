package com.example.engdictionaryapp

import com.example.engdictionaryapp.models.Question

data class MainState(val firstQuestion: Question?, val result: Int, val onePercentForTvScore: Double)