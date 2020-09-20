package com.example.sunlightdesign.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import javax.inject.Inject


/**
 * ViewModel for the task list screen.
 */
class ProfileViewModel @Inject constructor(
    private val tasksRepository: AuthRepository
) : ViewModel() {

}


