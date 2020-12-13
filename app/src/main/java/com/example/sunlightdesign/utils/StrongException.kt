package com.example.sunlightdesign.utils

import java.lang.Exception

class SessionEndException: Exception()

class ErrorListException(
    val errorMessage: String?,
    val errorMap: Map<String, List<String>>?
): Exception()