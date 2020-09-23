package com.example.sunlightdesign.utils

/**
 * Extension functions and Binding Adapters.
 */

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.example.sunlightdesign.Event
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.AreYouSureCallback
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import kotlin.reflect.KClass

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(context.getString(it), timeLength)
        }
    })
}

fun Fragment.setupRefreshLayout(
    refreshLayout: ScrollChildSwipeRefreshLayout,
    scrollUpChild: View? = null
) {
    refreshLayout.setColorSchemeColors(
        ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
        ContextCompat.getColor(requireActivity(), R.color.colorAccent),
        ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark)
    )
    // Set the scrolling view in the custom SwipeRefreshLayout.
    scrollUpChild?.let {
        refreshLayout.scrollUpChild = it
    }
}


fun Activity.displayToast(@StringRes message:Int){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.displayToast(message:String){
    Toast.makeText(this,message, Toast.LENGTH_LONG).show()
}

fun Activity.displaySuccessDialog(message: String?){
    MaterialDialog(this)
        .show{
            title(R.string.text_success)
            message(text = message)
            positiveButton(R.string.text_ok)
        }
}

fun Activity.displayErrorDialog(errorMessage: String?){
    MaterialDialog(this)
        .show{
            title(R.string.text_error)
            message(text = errorMessage)
            positiveButton(R.string.text_ok)
        }
}

fun Activity.displayInfoDialog(message: String?){
    MaterialDialog(this)
        .show{
            title(R.string.text_info)
            message(text = message)
            positiveButton(R.string.text_ok)
        }
}

fun Activity.areYouSureDialog(message: String, callback: AreYouSureCallback){
    MaterialDialog(this)
        .show{
            title(R.string.are_you_sure)
            message(text = message)
            negativeButton(R.string.text_cancel){
                callback.cancel()
            }
            positiveButton(R.string.text_yes){
                callback.proceed()
            }
        }
}

fun Activity.closeKeyboard(){
    val view = this.currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}


fun showMessage(context: Context, title: String? = null, message: String,
                btnNegative: String? = null, btnPositive: String = "OK",
                btnNegativeEvent: DialogInterface.OnClickListener? = null,
                btnPositiveEvent: DialogInterface.OnClickListener? = null,
                setCancelable: Boolean = true)
{
    var dialog: AlertDialog? = null
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title?:"")
    builder.setMessage(message)
    builder.setCancelable(setCancelable)
    if (btnNegative != null) {
        if (btnNegativeEvent != null)
            builder.setNegativeButton(btnNegative, btnNegativeEvent)
        else
            builder.setNegativeButton(btnNegative) { _, _ ->
                dialog?.dismiss()
            }
    }
    builder.setPositiveButton(btnPositive, btnPositiveEvent)
    try
    {
        dialog = builder.create()
        dialog.show()
    }
    catch (ex: Throwable)
    {
        Timber.e("showMessage method; line 335: ${ex.localizedMessage?:"unknown"}")
    }
}

fun <T : Activity> Activity.startNewActivity(activityClass: KClass<T>, block: Intent.() -> Unit = {}){
    val intent = Intent(this, activityClass.java)
    intent.block()
    this.startActivity(intent)
}


