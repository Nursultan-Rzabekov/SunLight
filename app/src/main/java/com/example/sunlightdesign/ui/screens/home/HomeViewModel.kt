package com.example.sunlightdesign.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.TasksRepository
import timber.log.Timber
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {


}


