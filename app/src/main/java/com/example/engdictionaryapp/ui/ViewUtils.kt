package com.example.engdictionaryapp.ui

import android.content.Context
import android.util.TypedValue

object ViewUtils {
    fun dpToPx(value: Int, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}