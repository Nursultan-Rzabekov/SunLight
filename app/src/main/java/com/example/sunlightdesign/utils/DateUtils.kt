package com.example.sunlightdesign.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object{

        const val PATTERN_DD_MM_YYYY = "dd.MM.yyyy"
        const val PATTERN_DD_MM_YYYY_HH_mm = "dd.MM.yyyy HH:mm"

        fun convertLongStringToDate(date: String): Date {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.ENGLISH)
            return try {
                sdf.parse(date)?: Date()
            } catch (e: Exception){
                Date()
            }
        }

        fun convertDateToString(date: Date, pattern: String = "dd.MM.yyyy HH:mm"): String {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            try {
                return sdf.format(date)
            }catch (e: Exception){
                throw Exception(e)
            }
        }

        fun reformatDateString(dataText: String, pattern: String): String {
            return convertDateToString(
                date = convertLongStringToDate(dataText),
                pattern = pattern)
        }
    }
}