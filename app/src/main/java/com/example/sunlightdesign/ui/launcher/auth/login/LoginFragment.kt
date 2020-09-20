

package com.example.sunlightdesign.ui.launcher.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.auth.BaseAuthFragment
import com.example.sunlightdesign.utils.CARD_MASK
import com.example.sunlightdesign.utils.IIN_MASK
import com.example.sunlightdesign.utils.MaskUtils
import com.example.sunlightdesign.utils.onTextFormatted
import kotlinx.android.synthetic.main.sunlight_login.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class LoginFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sunlight_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()
    }


    private fun setListeners(){
        btn_enter.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


    private fun setupMask() {
//        MaskImpl(MaskUtils.createSlotsFromMask(IIN_MASK, true), true).also {
//            it.isHideHardcodedHead = false
//            MaskFormatWatcher(it).apply {
//                installOn(edit_iin)
//                onTextFormatted {
//                    if (isIinValid()) edit_phone.requestFocus()
//                }
//            }
//        }

        MaskImpl(MaskUtils.createSlotsFromMask(MaskUtils.PHONE_MASK, true), true).also {
            it.isHideHardcodedHead = true
            MaskFormatWatcher(it).apply {
                installOn(phone_et)
                onTextFormatted {
                    //updateSignUpBtn()
                }
            }
        }
//
//
//        MaskImpl(MaskUtils.createSlotsFromMask(CARD_MASK, true), true).also {
//            it.isHideHardcodedHead = false
//            MaskFormatWatcher(it).apply {
//                installOn(edit_card_number)
//                onTextFormatted { updateSignUpBtn() }
//            }
//        }


    }

//    private fun updateSignUpBtn() {
//        btn_enter.isEnabled = if (isPhoneValid() && isIinValid() && (isCardValid() || isIbanValid())) {
//            activity?.closeKeyboard()
//            true
//        } else {
//            false
//        }
//    }

}
