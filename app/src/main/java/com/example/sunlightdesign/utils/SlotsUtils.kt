package com.example.sunlightdesign.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot

val IIN_MASK = "#### #### ####"
val DATE_MASK = "##.##.####"
val IBAN_MASK = "KZ## #### #### #### ####"
val IBAN_SIB_MASK = "######"
val CARD_MASK = "#### #### #### ####"
val EXPIRE_DATE_MASK = "## / ##"
val SMS_CODE = "_ _ _ _"
val LOGIN_PHONE_MASK = "+7 (___) ___ __ __"

private fun hardcodedSlot(value: Char) = Slot(Slot.RULE_INPUT_MOVES_INPUT, value, null);

object MaskUtils {
    val PHONE_MASK = "+# (###) ### ## ##"
    fun createSlotsFromMask(mask: String, onlyDigits: Boolean = true, digitSymbol: Char = '#'): Array<Slot> {
        return mutableListOf<Slot>().apply {
            mask.forEach {
                if (it == digitSymbol){
                    if (onlyDigits){
                        add(PredefinedSlots.digit())
                    } else {
                        add(PredefinedSlots.any())
                    }
                } else {
                    add(hardcodedSlot(it).withTags(Slot.TAG_DECORATION))
                }
            }
        }.toTypedArray()
    }
    fun unMaskValue(mask: String, value: String): String {
        if (mask.isEmpty()){
            return value
        }

        val builder = StringBuilder(value)
        var value = value
        var offset = 0

        mask.forEachIndexed { index, c ->
            val vI = value.indexOfFirst { it == c }
            if ((vI+offset) == index){
                builder.deleteCharAt(vI)
                value = builder.toString()
                offset++
            }
        }

        return builder.toString().trim()
    }


    fun maskValue(mask: String, value: String, maskSymbol: Char = '#'): String {
        if (mask.isEmpty()){
            return value
        }

        val builder = StringBuilder()
        var offset = 0

        mask.forEachIndexed { index, c ->
            if (c == maskSymbol){
                val item = value.getOrNull(offset) ?: ""
                builder.append(item)
                offset++
            } else {
                builder.append(c)
            }
        }

        return builder.toString().trim()
    }
}



