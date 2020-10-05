package com.example.sunlightdesign.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import kotlin.reflect.KClass

open class StrongFragment<T: StrongViewModel>(clazz: KClass<T>) : Fragment() {
    protected val viewModel by sharedViewModel(clazz)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.activityActionBehavior.observe(this@StrongFragment, Observer {
            it?.invoke(activity as? StrongActivity ?: return@Observer)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("readred")
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }
}