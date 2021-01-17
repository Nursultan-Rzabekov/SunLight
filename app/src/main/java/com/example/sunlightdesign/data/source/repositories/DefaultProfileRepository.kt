package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.profile.ProfileServices
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.*
import com.example.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import com.example.sunlightdesign.usecase.usercase.profileUse.post.VerificationRequest
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DefaultProfileRepository(
    private val services: ProfileServices
) : ProfileRepository {
    override suspend fun getUserInfo(): UserInfo = services.getProfileInfo().await()
    override suspend fun changePassword(changePassword: ChangePassword): BaseResponse
            = services.changePassword(changePassword).await()

    override suspend fun changeAvatar(changeAvatar: MultipartBody.Part): ChangeAvatar
            = services.changeAvatar(changeAvatar).await()

    override suspend fun getInvites(page: Int): InvitedResponse =
        services.getInvites(page).await()

    override suspend fun getVerifyHelpers(): VerificationHelperResponse =
        services.getHelpersForVerification().await()

    override suspend fun getVerificationInfo(): VerificationResponse =
        services.getVerificationInfo().await()

    override suspend fun verifyUser(request: VerificationRequest): VerificationResponse {
        val name =
            RequestBody.create(MediaType.parse("multipart/form-data"), request.name)
        val surname =
            RequestBody.create(MediaType.parse("multipart/form-data"), request.surname)
        val middle_name =
            RequestBody.create(MediaType.parse("multipart/form-data"), request.middle_name)
        val iin =
            RequestBody.create(MediaType.parse("multipart/form-data"), request.iin)
        val social_status =
            RequestBody.create(MediaType.parse("multipart/form-data"), request.social_status)
        val bank =
            RequestBody.create(MediaType.parse("multipart/form-data"), request.bank)
        val iban =
            RequestBody.create(MediaType.parse("multipart/form-data"), request.iban)
        val type =
            RequestBody.create(MediaType.parse("multipart/form-data"), request.type)

        var ip: RequestBody? = null
        if (request.ip != null) {
            ip = RequestBody.create(MediaType.parse("multipart/form-data"), request.ip)
        }

        return services.verifyUser(
            name = name,
            surname = surname,
            middle_name = middle_name,
            iin = iin,
            social_status = social_status,
            bank = bank,
            iban = iban,
            type = type,
            images = request.images,
            ip = ip
        ).await()
    }

    private fun prepareImageFilePart(
        partName: String,
        file: File
    ): MultipartBody.Part? {
        val requestFile: RequestBody = RequestBody.create(
            MediaType.parse("image/jpg"),
            file
        )
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }
}