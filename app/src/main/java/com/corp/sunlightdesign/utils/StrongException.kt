package com.corp.sunlightdesign.utils

import java.lang.Exception

class SessionEndException: Exception()

class ErrorListException(
    val errorMessage: String?,
    val errorMap: Map<String, List<String>>?,
    val error: String?
): Exception()