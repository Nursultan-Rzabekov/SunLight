package com.example.sunlightdesign.ui.base

import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.LauncherActivity
import com.example.sunlightdesign.ui.launcher.auth.AuthActivity
import com.example.sunlightdesign.utils.SecureSharedPreferences
import com.example.sunlightdesign.utils.SessionEndException
import com.example.sunlightdesign.utils.showMessage
import com.example.sunlightdesign.utils.startNewActivity
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

open class StrongViewModel : ViewModel(), KoinComponent {

    val sharedPreferences: SecureSharedPreferences by inject()

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
        when (throwable) {
            is SessionEndException -> {
                finishSession()
            }
        }

        withActivity { activity ->
            showMessage(
                context = activity,
                title = activity.resources.getString(R.string.text_error),
                message = errorMessage ?: throwable?.localizedMessage.toString(),
                setCancelable = false,
                btnPositive = activity.resources.getString(R.string.text_ok),
                btnPositiveEvent = DialogInterface.OnClickListener { dialog, _ ->  dialog.dismiss() }
            )
        }
    }

    private fun finishSession() {
        sharedPreferences.bearerToken = null
        withActivity { activity ->
            activity.startNewActivity(AuthActivity::class) {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            activity.overridePendingTransition(0, 0)
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

