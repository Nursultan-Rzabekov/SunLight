package com.example.sunlightdesign.ui.screens.home.structure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.StructureInfo
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.mainUse.get.GetStructureUseCase

class StructureViewModel constructor(
    private val getStructureUseCase: GetStructureUseCase
): StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _structure = MutableLiveData<StructureInfo>()
    val structure: LiveData<StructureInfo> get() = _structure

    fun getStructureInfo() {
        progress.postValue(true)
        getStructureUseCase.execute {
            onComplete {
                progress.postValue(false)
                _structure.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

}