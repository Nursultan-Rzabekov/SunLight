package com.example.sunlightdesign.utils

import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_register_partner_step_one.*


fun isPhoneValid(phone_et: EditText) =
    MaskUtils.unMaskValue(MaskUtils.PHONE_MASK,phone_et.text.toString()).length == 11

fun isIinValid(iin: String) = iin.length == 12