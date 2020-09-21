package com.example.sunlightdesign.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository



/**
 * ViewModel for the task list screen.
 */
class ProfileViewModel  constructor(
    private val tasksRepository: AuthRepository
) : ViewModel() {

}


