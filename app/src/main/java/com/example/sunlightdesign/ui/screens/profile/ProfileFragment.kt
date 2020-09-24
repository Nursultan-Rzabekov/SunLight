

package com.example.sunlightdesign.ui.screens.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import kotlinx.android.synthetic.main.account_registration_referral.*


class ProfileFragment : BaseProfileFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        register_partner_btn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragmentFragment_to_registerFragment)
        }

    }

}
