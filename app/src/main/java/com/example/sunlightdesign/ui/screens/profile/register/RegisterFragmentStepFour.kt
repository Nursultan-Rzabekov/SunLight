

package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_register_partner_step_four.*


class RegisterFragmentStepFour : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_partner_step_four, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners(){
        btn_next_step_four.setOnClickListener {
            findNavController().navigate(R.id.action_stepFourFragment_to_stepFiveFragment)
        }
    }

}
