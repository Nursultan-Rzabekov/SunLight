package com.example.sunlightdesign.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.akv.akvandroidapprelease.R
import com.akv.akvandroidapprelease.ui.BaseActivity
import com.akv.akvandroidapprelease.ui.auth.state.AuthStateEvent
import com.akv.akvandroidapprelease.ui.main.MainActivity
import com.akv.akvandroidapprelease.util.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import com.akv.akvandroidapprelease.viewmodels.ViewModelProviderFactory
import com.example.sunlightdesign.BaseApplication
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.screens.MainActivity
import com.example.sunlightdesign.ui.screens.email.EmailViewModel
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : StrongActivity(), NavController.OnDestinationChangedListener
{

    override val layoutId: Int
        get() = R.layout.activity_main

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (this.application as BaseApplication).appComponent.addAuthActivityComponent().create().inject(this)
        findNavController(R.id.auth_nav_host_fragment).addOnDestinationChangedListener(this)

        subscribeObservers()

    }

    override fun onResume() {
        super.onResume()
        checkPreviousAuthUser()
    }

    private fun subscribeObservers(){

        viewModel.dataState.observe(this, Observer { dataState ->
            onDataStateChange(dataState)
            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        it.authToken?.let {
                            Log.d(TAG, "AuthActivity, DataState: ${it}")
                            viewModel.setAuthToken(it)
                        }
                    }
                }
                data.response?.let{event ->
                    event.peekContent().let{ response ->
                        response.message?.let{ message ->
                            if(message == RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE){
                                onFinishCheckPreviousAuthUser()
                            }
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(this, Observer{
            Log.d(TAG, "AuthActivity, subscribeObservers: AuthViewState: ${it}")
            it.authToken?.let{
                sessionManager.login(it)
            }
        })

        sessionManager.cachedToken.observe(this, Observer{ dataState ->
            Log.d(TAG, "AuthActivity, subscribeObservers: AuthDataState: ${dataState}")
            dataState.let{ authToken ->
                if(authToken?.token != null){
                    navMainActivity()
                }
            }
        })
    }


    fun navMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkPreviousAuthUser(){
        viewModel.setStateEvent(AuthStateEvent.CheckPreviousAuthEvent)
    }

    private fun onFinishCheckPreviousAuthUser(){
        fragment_container.visibility = View.VISIBLE
    }


}

















