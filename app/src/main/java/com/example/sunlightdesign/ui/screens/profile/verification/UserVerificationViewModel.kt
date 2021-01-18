package com.example.sunlightdesign.ui.screens.profile.verification

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.VerificationHelperResponse
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.VerificationResponse
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.profileUse.get.GetVerificationInfoUseCase
import com.example.sunlightdesign.usecase.usercase.profileUse.get.GetVerifyHelperUseCase
import com.example.sunlightdesign.usecase.usercase.profileUse.post.VerificationRequest
import com.example.sunlightdesign.usecase.usercase.profileUse.post.VerifyUserUseCase
import com.example.sunlightdesign.utils.Constants
import com.example.sunlightdesign.utils.ErrorListException
import timber.log.Timber

class UserVerificationViewModel(
    private val verifyUserUseCase: VerifyUserUseCase,
    private val getVerifyHelperUseCase: GetVerifyHelperUseCase,
    private val getVerificationInfoUseCase: GetVerificationInfoUseCase
): StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    var selectedSocialStatuses = mutableListOf<String>()

    private var _backDocument = MutableLiveData<Uri?>()
    val backDocument: LiveData<Uri?> = _backDocument

    private var _frontDocument = MutableLiveData<Uri?>()
    val frontDocument: LiveData<Uri?> = _frontDocument

    private var _contractDocument = MutableLiveData<Uri?>()
    val contractDocument: LiveData<Uri?> = _contractDocument

    private var _helper = MutableLiveData<VerificationHelperResponse>()
    val helper: LiveData<VerificationHelperResponse> = _helper

    private var _verificationState = MutableLiveData<VerificationResponse>()
    val verificationState: LiveData<VerificationResponse> = _verificationState

    private var _verificationInfo = MutableLiveData<VerificationResponse>()
    val verificationInfo: LiveData<VerificationResponse> = _verificationInfo

    fun getHelper() {
        progress.postValue(true)
        getVerifyHelperUseCase.execute {
            onComplete {
                progress.postValue(false)
                _helper.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

    fun verifyUser(verification: VerificationRequest) {
        progress.postValue(true)
        verifyUserUseCase.setModel(verification)
        verifyUserUseCase.execute {
            onComplete {
                progress.postValue(false)
                _verificationState.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                when (it) {
                    is ErrorListException -> {
                        handleError(errorMessage = it.errorMessage)
                    }
                    else ->
                        handleError(throwable = it)
                }
            }
        }
    }

    fun getInitialVerificationInfo() {
        progress.postValue(true)
        getVerificationInfoUseCase.execute {
            onComplete {
                progress.postValue(false)
                _verificationInfo.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
        }
    }

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