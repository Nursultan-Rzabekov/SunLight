package com.example.sunlightdesign.ui.launcher.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.launcher.auth.AuthViewModel
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.example.sunlightdesign.utils.MaskUtils
import com.example.sunlightdesign.utils.isPhoneValid
import kotlinx.android.synthetic.main.sunlight_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class LoginFragment : StrongFragment<AuthViewModel>(AuthViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sunlight_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceViewState: Bundle?) {
        super.onViewCreated(view, savedInstanceViewState)

        welcome_login_as_tv.text = getString(R.string.login_welcome, "Спонсор Имя")

        setupMask()
        configViewModel()
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()
    }


    private fun setListeners(){
        btn_enter.setOnClickListener {
            if(setCheckers())
                viewModel.getUseCase(SetLogin(MaskUtils.unMaskValue(MaskUtils.PHONE_MASK, phone_et.text.toString()), password_et.text.toString()))
        }

        forget_password_tv.setOnClickListener {}
    }

    private fun configViewModel() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })
        }

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
