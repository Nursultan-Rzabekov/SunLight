package com.example.sunlightdesign.ui.base

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.R
import com.example.sunlightdesign.utils.showMessage
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject


class VmAction(
    var singleAction: (StrongActivity) -> Unit
) {

    fun invoke(activity: StrongActivity?) {
        activity ?: return
        singleAction(activity)
        singleAction = {}
    }
}

open class StrongViewModel : ViewModel(){
    val activityActionBehavior = SingleLiveEvent<VmAction>()

    fun VmAction.invokeAction() {
        val isUiThread =
            android.os.Looper.getMainLooper().isCurrentThread
        if (isUiThread) {
            activityActionBehavior.value = this
        } else {
            activityActionBehavior.postValue(this)
        }
    }

    protected open fun handleError(throwable: Throwable? = null, errorMessage: String? = null) {
        withActivity {
            showMessage(
                context = it,
                title = it.resources.getString(R.string.text_error),
                message = errorMessage ?: throwable?.localizedMessage.toString(),
                setCancelable = false,
                btnPositive = it.resources.getString(R.string.text_ok),
                btnPositiveEvent = DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                }
            )
        }
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
    open fun onPermissionActivityResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {}

    fun Single<VmAction>.invoiceAction() = doOnSuccess { it.invokeAction() }

    fun withActivity(block: (StrongActivity) -> Unit){
        VmAction(block).invokeAction()
    }
}

