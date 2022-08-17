package com.corp.sunlightdesign.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.utils.showListPopupWindow
import com.corp.sunlightdesign.utils.textwatchers.InternationalPhoneTextWatcher
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.android.synthetic.main.country_code_phone_view.view.*
import java.util.*

private const val DEFAULT_COUNTRY_CODE = "kz"

class CountryCodePhoneView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private val countries = mutableListOf<CountryCode>()
    private val phoneUtil by lazy { PhoneNumberUtil.createInstance(context) }
    private var currentCountry: CountryCode? = null
    private val itemsAdapter = CountryCodeBaseAdapter()
    private var currentTextWatcher: InternationalPhoneTextWatcher? = null
    private var onTextFormattedListener: OnTextFormattedListener? = null

    var isErrorEnabled: Boolean
        get() = phoneBodyTextInputLayout.isErrorEnabled
        set(value) { phoneBodyTextInputLayout.isErrorEnabled = value }

    var error: CharSequence?
        get() = phoneBodyTextInputLayout.error
        set(value) { phoneBodyTextInputLayout.error = value }

    init {
        countries.addAll(getCountryCodes())
        LayoutInflater.from(context).inflate(R.layout.country_code_phone_view, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        setDefaultMask()

        phoneBodyEditText.addTextChangedListener {
            onTextFormattedListener?.onTextFormatted(it)
        }

        phonePrefixTextView.setOnClickListener {
            showListPopupWindow(
                context = context,
                items = countries,
                anchor = countryPickerLayout,
                adapter = itemsAdapter,
                onSelection = { country ->
                    setCountry(country)
                }
            )
        }
    }

    private fun setDefaultMask() {
        val localeCountryCode = Locale.getDefault().country.toLowerCase(Locale.getDefault())
        val countryCode = DEFAULT_COUNTRY_CODE
        val countryInList = countries.firstOrNull { it.code == countryCode }
        val country = countryInList ?: countries.first { it.code == DEFAULT_COUNTRY_CODE }

        setCountry(country)
    }

    private fun setCountry(country: CountryCode) {
        currentCountry = country
        phoneBodyEditText.setText("")
        phonePrefixTextView.text = ("+${country.phoneCode}")
        flagImageView.setImageDrawable(ContextCompat.getDrawable(
            context,
            getCountryFlag(country.code)
        ))
        setMask(country)
    }

    private fun setMask(country: CountryCode) {
        if (currentTextWatcher == null) {
            currentTextWatcher = InternationalPhoneTextWatcher(
                context,
                country.code,
                country.phoneCode
            )
            phoneBodyEditText.addTextChangedListener(currentTextWatcher)
        } else {
            currentTextWatcher?.updateCountry(country.code, country.phoneCode)
        }
    }

    fun setPhoneInfo(countryCode: String, body: String) {
        val country = countries.firstOrNull { countryCode == it.code } ?: return
        setCountry(country)
        phoneBodyEditText.setText(body)
    }

    fun getPhoneInfo(): PhoneInfo? {
        if (!isValid()) return null
        if (currentCountry == null) return null
        val body = "${phonePrefixTextView.text} ${phoneBodyEditText.text}"
        val normalizedBody = PhoneNumberUtil.normalizeDigitsOnly(body)
        val mainBody = PhoneNumberUtil.normalizeDigitsOnly(phoneBodyEditText.text)
        return PhoneInfo(
            phone = normalizedBody,
            countryCode = currentCountry?.code.orEmpty(),
            body = mainBody
        )
    }

    fun isValid(): Boolean {
        val phone = "${phonePrefixTextView.text} ${phoneBodyEditText.text}"
        return try {
            phoneUtil.isValidNumber(phoneUtil.parse(phone, null))
        } catch (e: Exception) {
            false
        }
    }

    fun setOnTextFormattedListener(listener: OnTextFormattedListener) {
        this.onTextFormattedListener = listener
    }

    fun removeOnTextFormattedListener() {
        this.onTextFormattedListener = null
    }

    interface OnTextFormattedListener {
        fun onTextFormatted(text: CharSequence?)
    }
}

data class CountryCode(
    val code: String,
    val phoneCode: Int,
    val countryName: String
) {
    override fun toString(): String = countryName
}

data class PhoneInfo(
    val phone: String,
    val countryCode: String,
    val body: String
)