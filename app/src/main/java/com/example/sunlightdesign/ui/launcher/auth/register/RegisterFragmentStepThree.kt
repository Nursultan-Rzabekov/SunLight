

package com.example.sunlightdesign.ui.launcher.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.auth.AuthViewModel
import com.example.sunlightdesign.ui.launcher.auth.BaseAuthFragment
import kotlinx.android.synthetic.main.registration_partner_step_three.*


class RegisterFragmentStepThree : BaseAuthFragment() {

    override val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_partner_step_three, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners(){
        btn_next_step_three.setOnClickListener {
            findNavController().navigate(R.id.action_stepThreeFragment_to_stepFourFragment)
        }
    }

}
