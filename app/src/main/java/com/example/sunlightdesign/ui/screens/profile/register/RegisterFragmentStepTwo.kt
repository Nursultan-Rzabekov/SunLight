

package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.screens.profile.BaseProfileFragment
import kotlinx.android.synthetic.main.fragment_register_partner_step_two.*


class RegisterFragmentStepTwo : BaseProfileFragment() {
//     val viewModel: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_partner_step_two, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners(){
        registration_partner_step_two_next_button.setOnClickListener {
            findNavController().navigate(R.id.action_stepTwoFragment_to_stepThreeFragment)
        }
    }

}
