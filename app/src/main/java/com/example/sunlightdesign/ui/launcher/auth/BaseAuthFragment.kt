package com.example.sunlightdesign.ui.launcher.auth


import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseAuthFragment: Fragment() {

    protected val viewModel: AuthViewModel by sharedViewModel()

}



























