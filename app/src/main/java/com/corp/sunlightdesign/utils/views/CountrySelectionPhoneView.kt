package com.corp.sunlightdesign.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.utils.BasePopUpAdapter
import com.corp.sunlightdesign.utils.showListPopupWindow
import com.santalu.maskara.Mask
import com.santalu.maskara.MaskChangedListener
import com.santalu.maskara.MaskStyle
import kotlinx.android.synthetic.main.country_code_phone_field.view.*
import kotlinx.android.synthetic.main.sunlight_login.*

const val DEFAULT_MASK = "(___) ___ __ __"

class CountrySelectionPhoneView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private val items = mutableMapOf<String, String>()
    private val itemsAdapter = BasePopUpAdapter<String>()
    private var currentMaskListener: MaskChangedListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.country_code_phone_field, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (items.isNotEmpty()) {
            val key = items.keys.first()
            phonePrefixTextView.text = key
            val mask = items[key] ?: DEFAULT_MASK
            installMask(mask)
            phoneBodyEditText.setText("")
        } else {
            installMask(DEFAULT_MASK)
        }

        phonePrefixTextView.setOnClickListener {
            showListPopupWindow(
                context = context,
                items = items.keys.toList(),
                anchor = phonePrefixTextView,
                adapter = itemsAdapter,
                onSelection = { codeKey ->
                    val mask = items[codeKey] ?: return@showListPopupWindow
                    installMask(mask)
                    phoneBodyEditText.setText("")
                    phonePrefixTextView.text = codeKey
                }
            )
        }
    }

    private fun installMask(maskText: String) {
        val mask = Mask(
            value = maskText,
            character = '_',
            style = MaskStyle.COMPLETABLE
        )
        if (currentMaskListener != null) {
            phoneBodyEditText.removeTextChangedListener(currentMaskListener)
        }
        currentMaskListener = MaskChangedListener(mask)
        phoneBodyEditText.addTextChangedListener(currentMaskListener)
    }

    fun installItems(map: Map<String, String>) {
        items.clear()
        items.putAll(map)
    }

    fun getText(): String =
        "${phonePrefixTextView.text}" + currentMaskListener?.unMasked

    fun getPair(): Pair<String, String> =
        phonePrefixTextView.text.toString() to currentMaskListener?.unMasked.orEmpty()

    fun setPair(pair: Pair<String, String>) {
        val mask = items[pair.first] ?: return
        phonePrefixTextView.text = pair.first
        installMask(mask)
        phoneBodyEditText.setText(pair.second)
    }

    fun isDone(): Boolean = currentMaskListener?.isDone ?: false
}