package com.example.sunlightdesign.ui.screens.list

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository



/**
 * ViewModel for the task list screen.
 */
class ListViewModel  constructor(
    private val tasksRepository: AuthRepository
) : ViewModel() {

}


