package com.example.domain.utils

import com.example.domain.R
import com.example.domain.model.Sex

object SexConverter {

    fun convertToResId(sex: Sex?): Int {
        return when (sex) {
            Sex.MALE -> R.string.male
            Sex.FEMALE -> R.string.female
            else -> R.string.not_mentioned
        }
    }
}