package com.example.sunlightdesign.usecase.usercase.authUse


import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.remote.auth.entity.LoginResponse
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase
import javax.inject.Inject


class GetLoginAuthUseCase @Inject constructor(
    private val itemsRepository: AuthRepository
) : BaseCoroutinesUseCase<LoginResponse>() {

    override suspend fun executeOnBackground(): LoginResponse =
        itemsRepository.getTasks()
}