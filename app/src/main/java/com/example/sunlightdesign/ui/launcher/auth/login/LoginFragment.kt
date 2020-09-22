package com.example.sunlightdesign.ui.launcher.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.auth.AuthState
import com.example.sunlightdesign.ui.launcher.auth.BaseAuthFragment
import com.example.sunlightdesign.ui.launcher.auth.register.isPhoneValid
import com.example.sunlightdesign.ui.screens.MainActivity
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.example.sunlightdesign.utils.MaskUtils
import com.example.sunlightdesign.utils.displayErrorDialog
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcome_login_as_tv.text = getString(R.string.login_welcome, "Спонсор Имя")

        setupMask()
        setObservers()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()
    }


    private fun setListeners(){
        btn_enter.setOnClickListener {
            if(setCheckers())
            viewModel.getUseCase(SetLogin(MaskUtils.unMaskValue(MaskUtils.PHONE_MASK, phone_et.text.toString()), password_et.text.toString()))
        }

        forget_password_tv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setObservers() {
        viewModel.authState.observe(viewLifecycleOwner, Observer{
            progress_bar.isVisible = it is AuthState.Loading
            when(it) {
                is AuthState.Success -> {
                    startActivity(MainActivity.intent(requireContext()))
                }
                is AuthState.Error -> {
                    requireActivity().displayErrorDialog(it.message)
                }
            }
        })
    }

    private fun setCheckers() : Boolean {
        if(!isPhoneValid(phone_et)){
            phone_et.error = getString(R.string.wrong_phone_number)
            return false
        }
        return true
    }

    private fun setupMask() {
        MaskImpl(
            MaskUtils.createSlotsFromMask(
                MaskUtils.PHONE_MASK,
                true),
            true).also {
            it.isHideHardcodedHead = true
            MaskFormatWatcher(it).apply {
                installOn(phone_et)
            }
        }

        phone_et.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                phone_et.hint = getString(R.string.phone_mask_hint)
            } else {
                phone_et.hint = ""
            }
        }
    }

}
