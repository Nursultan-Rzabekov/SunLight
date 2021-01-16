package com.example.sunlightdesign.ui.screens.profile.verification

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.utils.Constants
import timber.log.Timber

class UserVerificationViewModel: StrongViewModel() {

    private var _backDocument = MutableLiveData<Uri?>()
    val backDocument: LiveData<Uri?> = _backDocument

    private var _frontDocument = MutableLiveData<Uri?>()
    val frontDocument: LiveData<Uri?> = _frontDocument

    private var _contractDocument = MutableLiveData<Uri?>()
    val contractDocument: LiveData<Uri?> = _contractDocument


    fun onBackDocumentInvalidate() {
        _backDocument.postValue(null)
    }

    fun onFrontDocumentInvalidate() {
        _frontDocument.postValue(null)
    }

    fun onContractDocumentInvalidate() {
        _contractDocument.postValue(null)
    }

    fun onAttachDocument(requestCode: Int = Constants.ACTION_IMAGE_CONTENT_INTENT_CODE) {
        withActivity {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            it.startActivityForResult(intent, requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) return
        when (requestCode) {
            Constants.ACTION_IMAGE_CONTENT_INTENT_CODE -> {
                Timber.d("Image path: ${data.data}")
                when {
                    _frontDocument.value == null -> _frontDocument.postValue(data.data)
                    _backDocument.value == null -> _backDocument.postValue(data.data)
                    _contractDocument.value == null -> _contractDocument.postValue(data.data)
                }
            }
        }
    }

}