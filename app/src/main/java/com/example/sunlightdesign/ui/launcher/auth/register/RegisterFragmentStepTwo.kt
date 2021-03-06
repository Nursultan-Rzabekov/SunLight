

package com.example.sunlightdesign.ui.launcher.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.auth.BaseAuthFragment
import kotlinx.android.synthetic.main.registration_partner_step_two.*


class RegisterFragmentStepTwo : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_partner_step_two, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners(){
        registration_partner_step_two_next_button.setOnClickListener {
            findNavController().navigate(R.id.action_stepTwoFragment_to_stepThreeFragment)
        }
    }

}
