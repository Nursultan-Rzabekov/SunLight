package com.example.sunlightdesign.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.TasksRepository
import javax.inject.Inject


/**
 * ViewModel for the task list screen.
 */
class HomeViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {

}


