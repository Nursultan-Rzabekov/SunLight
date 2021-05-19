package com.corp.sunlightdesign.utils

import ru.tinkoff.decoro.FormattedTextChangeListener
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

fun MaskFormatWatcher.onTextFormatted(onText: (String?) -> Unit) {
    this.setCallback(object : FormattedTextChangeListener {
        override fun beforeFormatting(oldValue: String?, newValue: String?) = false
        override fun onTextFormatted(formatter: FormatWatcher?, newText: String?) {
            onText.invoke(newText)
        }
    })
}