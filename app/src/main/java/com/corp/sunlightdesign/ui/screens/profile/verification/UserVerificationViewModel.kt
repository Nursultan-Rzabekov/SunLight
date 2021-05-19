package com.corp.sunlightdesign.ui.screens.profile.verification

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.VerificationHelperResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.VerificationResponse
import com.corp.sunlightdesign.ui.base.StrongViewModel
import com.corp.sunlightdesign.usecase.usercase.profileUse.get.GetVerificationInfoUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.get.GetVerifyHelperUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.VerificationRequest
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.VerifyUserUseCase
import com.corp.sunlightdesign.utils.Constants
import com.corp.sunlightdesign.utils.ErrorListException
import timber.log.Timber

class UserVerificationViewModel(
    private val verifyUserUseCase: VerifyUserUseCase,
    private val getVerifyHelperUseCase: GetVerifyHelperUseCase,
    private val getVerificationInfoUseCase: GetVerificationInfoUseCase
): StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    var selectedSocialStatuses = mutableListOf<String>()

    private var _attachDocument = MutableLiveData<Uri?>()
    val attachDocument: LiveData<Uri?> = _attachDocument

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
                _attachDocument.postValue(data.data)
            }
        }
    }

}