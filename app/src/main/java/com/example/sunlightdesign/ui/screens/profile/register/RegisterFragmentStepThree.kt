

package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.screens.profile.BaseProfileFragment
import kotlinx.android.synthetic.main.registration_partner_step_three.*


class RegisterFragmentStepThree : BaseProfileFragment() {

//     val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_partner_step_three, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners(){
        next_step_three_btn.setOnClickListener {
            findNavController().navigate(R.id.action_stepThreeFragment_to_stepFourFragment)
        }
    }

}
