package com.example.sunlightdesign.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object{

        fun convertLongStringToDate(date: String): Date {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.ENGLISH)
            var d: Date
            try {
                d = sdf.parse(date)?: Date()
            } catch (e: Exception){
                d = Date()
            }
            return d
        }

        fun convertDateToString(date: Date): String{
            val sdf = SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault())
            try {
                val d = sdf.format(date)
                return d
            }catch (e: Exception){
                throw Exception(e)
            }
        }
    }
}