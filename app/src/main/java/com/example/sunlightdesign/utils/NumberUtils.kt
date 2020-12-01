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