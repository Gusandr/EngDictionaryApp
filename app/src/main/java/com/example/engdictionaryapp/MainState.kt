package com.example.engdictionaryapp

import com.example.engdictionaryapp.models.Question
import com.example.engdictionaryapp.models.Variant

data class MainState(val firstQuestion: Question?, val result: Int, val onePercentForTvScore: Double) {

}