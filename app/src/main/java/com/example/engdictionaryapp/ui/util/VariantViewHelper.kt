package com.example.engdictionaryapp.ui.util

import com.example.engdictionaryapp.databinding.ActivityLearnWordBinding
import com.example.engdictionaryapp.models.Variant

object VariantViewHelper {
    fun getVariants(binding: ActivityLearnWordBinding): List<Variant> {
        val variants = mutableListOf<Variant>()

        with(binding) {
            val variantsList = listOf(
                Variant(
                    layoutAnswer1,
                    tvVariantNumber1,
                    tvVariantValue1
                ),
                Variant(
                    layoutAnswer2,
                    tvVariantNumber2,
                    tvVariantValue2
                ),
                Variant(
                    layoutAnswer3,
                    tvVariantNumber3,
                    tvVariantValue3
                ),
                Variant(
                    layoutAnswer4,
                    tvVariantNumber4,
                    tvVariantValue4
                )
            )

            for (variant in variantsList)
                variants.add(variant)
        }

        return variants
    }
}