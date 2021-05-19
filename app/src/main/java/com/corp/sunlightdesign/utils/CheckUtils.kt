package com.corp.sunlightdesign.utils

fun isPhoneValid(phone_et: String) =
    MaskUtils.unMaskValue(MaskUtils.PHONE_MASK, phone_et).length >= Constants.PHONE_LENGTH

fun isIinValid(iin: String) =
    MaskUtils.unMaskValue(IIN_MASK, iin).length >= Constants.IIN_LENGTH