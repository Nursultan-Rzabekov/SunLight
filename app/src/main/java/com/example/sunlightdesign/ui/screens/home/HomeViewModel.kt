package com.example.sunlightdesign.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.TasksRepository
import com.example.sunlightdesign.usecase.usercase.homeUse.GetItemsUseCase
import timber.log.Timber
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val tasksRepository: TasksRepository
) : ViewModel() {


    fun getUseCase(){
        getItemsUseCase.execute {
            onComplete {
                Timber.e("onComplete: %s", it.size)
            }
            onNetworkError { Timber.e(it.toString()) }
            onError { Timber.e(it) }
        }
    }

}


