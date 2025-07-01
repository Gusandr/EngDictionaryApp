package com.example.engdictionaryapp.models

import android.widget.LinearLayout
import android.widget.TextView

data class Variant(
    val layoutAnswer: LinearLayout,
    val tvVariantNumber: TextView,
    val tvVariantValue: TextView
)