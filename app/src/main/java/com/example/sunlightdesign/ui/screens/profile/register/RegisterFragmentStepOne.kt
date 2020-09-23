package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.auth.BaseAuthFragment
import com.example.sunlightdesign.ui.screens.profile.BaseProfileFragment
import com.example.sunlightdesign.utils.IIN_MASK
import com.example.sunlightdesign.utils.MaskUtils
import com.example.sunlightdesign.utils.closeKeyboard
import com.example.sunlightdesign.utils.onTextFormatted
import kotlinx.android.synthetic.main.registration_partner.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class RegisterFragmentStepOne : BaseProfileFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_partner, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()
        setupMask()

        viewModel.getCountriesList()

    }


    private fun setupMask() {
        MaskImpl(
            MaskUtils.createSlotsFromMask(IIN_MASK, true), true).also {
            it.isHideHardcodedHead = false
            MaskFormatWatcher(it).apply {
                installOn(iin_et)
                onTextFormatted {
                    if (isIinValid()) phone_et.requestFocus()
                }
            }
        }

        MaskImpl(
            MaskUtils.createSlotsFromMask(MaskUtils.PHONE_MASK, true), true).also {
            it.isHideHardcodedHead = true
            MaskFormatWatcher(it).apply {
                installOn(phone_et)
                onTextFormatted { updateSignUpBtn() }
            }
        }
    }

    private fun isIinValid() = iin_et.length() == 12

    private fun setListeners(){
        btn_next_step_one.setOnClickListener {
            findNavController().navigate(R.id.action_stepOneFragment_to_stepTwoFragment)
        }
    }

    private fun updateSignUpBtn() {
        btn_next_step_one.isEnabled = if (isPhoneValid(phone_et) && isIinValid()) {
            activity?.closeKeyboard()
            true
        } else false
    }

}

fun isPhoneValid(phone_et: EditText) = MaskUtils.unMaskValue(MaskUtils.PHONE_MASK,phone_et.text.toString()).length == 11
