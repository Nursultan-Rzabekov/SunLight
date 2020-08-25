package com.example.sunlightdesign.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.TasksRepository
import javax.inject.Inject


/**
 * ViewModel for the task list screen.
 */
class ProfileViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {

}


