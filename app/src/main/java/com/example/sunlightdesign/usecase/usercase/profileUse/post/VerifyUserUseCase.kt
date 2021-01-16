package com.example.sunlightdesign.usecase.usercase.profileUse.post

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.VerificationResponse
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase
import okhttp3.MultipartBody

class VerifyUserUseCase(
    private val repository: ProfileRepository
): BaseCoroutinesUseCase<VerificationResponse?>() {

    private var model: VerificationRequest? = null

    fun setModel(model: VerificationRequest) {
        this.model = model
    }

    override suspend fun executeOnBackground(): VerificationResponse? =
        model?.let {
            repository.verifyUser(it)
        }

}

data class VerificationRequest(
    val name: String,
    val surname: String,
    val middle_name: String,
    val iin: String,
    val social_status: String,
    val bank: String,
    val iban: String,
    val type: String,
    val ip: String?,
    val images: List<MultipartBody.Part>
) {
    companion object {
        const val TYPE_LEGAL = "1"
        const val TYPE_NOT_LEGAL = "0"
    }
}