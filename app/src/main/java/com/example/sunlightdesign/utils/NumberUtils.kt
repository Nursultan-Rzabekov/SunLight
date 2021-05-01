package com.example.sunlightdesign.utils

import java.text.DecimalFormat

class NumberUtils {

    companion object {
        private val decimalFormat = DecimalFormat("#,###.##")

        fun prettifyDouble(number: Double) : String {
            return decimalFormat.format(number).replace(',', ' ')
        }
    }
}

fun Double?.orZero(): Double = this ?: 0.0

fun Int?.orMinusOne(): Int = this ?: -1