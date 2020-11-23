package com.example.sunlightdesign.ui.base

import android.app.LauncherActivity
import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.R
import com.example.sunlightdesign.utils.showMessage
import com.example.sunlightdesign.utils.startNewActivity
import com.readystatesoftware.chuck.internal.ui.MainActivity
import io.reactivex.Single


class VmAction(
    var singleAction: (StrongActivity) -> Unit
) {

    fun invoke(activity: StrongActivity?) {
        activity ?: return
        singleAction(activity)
        singleAction = {}
    }
}

open class StrongViewModel : ViewModel() {
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
        withActivity { activity ->
            showMessage(
                context = activity,
                title = activity.resources.getString(R.string.text_error),
                message = errorMessage ?: throwable?.localizedMessage.toString(),
                setCancelable = false,
                btnPositive = activity.resources.getString(R.string.text_ok),
                btnPositiveEvent = DialogInterface.OnClickListener { dialog, _ ->
                    errorMessage?.let { message ->
                        if(message.contains("Logout")){
                            if(activity.javaClass.isInstance(MainActivity::class.java)){
                                (activity as MainActivity).startNewActivity(LauncherActivity::class)
                            }
                            else{
                               println()
                            }
                        }
                    }
                    dialog.dismiss()
                }
            )
        }
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    open fun onPermissionActivityResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
    }

    fun Single<VmAction>.invoiceAction() = doOnSuccess { it.invokeAction() }

    fun withActivity(block: (StrongActivity) -> Unit) {
        VmAction(block).invokeAction()
    }
}

