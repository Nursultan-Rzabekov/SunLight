package com.example.sunlightdesign.ui.screens.email

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.emailUse.DeleteAnnouncementUseCase
import com.example.sunlightdesign.usecase.usercase.emailUse.get.GetAnnouncementsUseCase
import com.example.sunlightdesign.usecase.usercase.emailUse.get.ShowAnnouncementsDetailsUseCase


/**
 * ViewModel for the task list screen.
 */
class EmailViewModel  constructor(
    private val getAnnouncementsUseCase: GetAnnouncementsUseCase,
    private val showAnnouncementsDetailsUseCase: ShowAnnouncementsDetailsUseCase,
    private val deleteAnnouncementUseCase: DeleteAnnouncementUseCase
) : StrongViewModel() {

}


