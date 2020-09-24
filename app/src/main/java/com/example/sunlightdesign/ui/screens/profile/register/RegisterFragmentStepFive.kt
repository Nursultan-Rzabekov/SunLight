

package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.auth.BaseAuthFragment
import com.example.sunlightdesign.ui.screens.profile.BaseProfileFragment


class RegisterFragmentStepFive : BaseProfileFragment() {

//     val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_partner_step_five, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners(){

    }

}
