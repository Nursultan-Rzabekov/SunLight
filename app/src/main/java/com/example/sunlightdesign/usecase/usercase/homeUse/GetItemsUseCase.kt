package com.example.sunlightdesign.usecase.usercase.homeUse


import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.TasksRepository
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase
import javax.inject.Inject


class GetItemsUseCase @Inject constructor(
    private val itemsRepository: TasksRepository
) : BaseCoroutinesUseCase<List<Task>>() {

    override suspend fun executeOnBackground(): List<Task> =
        itemsRepository.getTasks(true)
}