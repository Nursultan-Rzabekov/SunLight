

package com.example.sunlightdesign.ui.launcher.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.auth.BaseAuthFragment
import kotlinx.android.synthetic.main.registration_partner.*


class RegisterFragmentStepOne : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_partner, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners(){
        btn_next_step_one.setOnClickListener {
            findNavController().navigate(R.id.action_stepOneFragment_to_stepTwoFragment)
        }
    }

}
