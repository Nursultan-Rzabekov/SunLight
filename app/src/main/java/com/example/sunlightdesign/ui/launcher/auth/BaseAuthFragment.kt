package com.example.sunlightdesign.ui.launcher.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseAuthFragment: Fragment() {

    protected abstract  val viewModel: AuthViewModel
    val TAG: String = "AppDebug"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}



























