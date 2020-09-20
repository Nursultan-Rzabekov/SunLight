package com.example.sunlightdesign.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val tasksRepository: AuthRepository
) : ViewModel() {


}


