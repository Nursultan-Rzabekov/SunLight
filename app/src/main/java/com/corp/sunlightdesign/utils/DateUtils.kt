package com.corp.sunlightdesign.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object{

        const val PATTERN_DD_MM_YYYY = "dd.MM.yyyy"
        const val PATTERN_DD_MM_YYYY_HH_mm = "dd.MM.yyyy HH:mm"
        const val PATTERN_DD_MM_YYYY_HH_mm_SLASH = "yyyy-MM-dd HH:mm:ss"
        const val PATTERN_FULL_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'"

        fun convertLongStringToDate(
            date: String,
            pattern: String = PATTERN_DD_MM_YYYY_HH_mm_SLASH
        ): Date {
            val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
            return try {
                sdf.parse(date) ?: Date()
            } catch (e: Exception){
                Date()
            }
        }

        fun convertDateToString(date: Date, pattern: String = PATTERN_DD_MM_YYYY_HH_mm): String {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            try {
                return sdf.format(date)
            }catch (e: Exception){
                throw Exception(e)
            }
        }

        fun reformatDateString(
            dataText: String,
            toPattern: String,
            fromPattern: String = PATTERN_DD_MM_YYYY_HH_mm_SLASH
        ): String {
            return convertDateToString(
                date = convertLongStringToDate(dataText, fromPattern),
                pattern = toPattern)
        }
    }
}