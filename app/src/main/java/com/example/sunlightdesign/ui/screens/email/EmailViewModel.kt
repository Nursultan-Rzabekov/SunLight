package com.example.sunlightdesign.ui.screens.email

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import javax.inject.Inject


/**
 * ViewModel for the task list screen.
 */
class EmailViewModel @Inject constructor(
    private val tasksRepository: AuthRepository
) : ViewModel() {

}


