

package com.example.sunlightdesign.ui.launcher.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.auth.AuthViewModel
import com.example.sunlightdesign.ui.launcher.auth.BaseAuthFragment


class RegisterFragmentStepFive : BaseAuthFragment() {

    override val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_partner_step_five, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners(){

    }

}
