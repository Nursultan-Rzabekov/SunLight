package com.example.sunlightdesign.ui.screens.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.TasksRepository


/**
 * ViewModel for the task list screen.
 */
class TasksViewModel(
    private val tasksRepository: TasksRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}


