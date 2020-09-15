package com.example.sunlightdesign.usecase.usercase.authUse


import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.TasksRepository
import com.example.sunlightdesign.data.source.remote.entity.LoginResponse
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase
import javax.inject.Inject


class GetLoginAuthUseCase @Inject constructor(
    private val itemsRepository: TasksRepository
) : BaseCoroutinesUseCase<List<LoginResponse>>() {

    override suspend fun executeOnBackground(): List<LoginResponse> =
        itemsRepository.getTasks(true)
}