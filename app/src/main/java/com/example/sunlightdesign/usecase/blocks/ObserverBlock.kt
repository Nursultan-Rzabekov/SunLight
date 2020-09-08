package com.example.sunlightdesign.usecase.blocks

import com.example.sunlightdesign.usecase.BaseObserveCoroutinesUseCase


typealias ObserverBlock<T> = BaseObserveCoroutinesUseCase.Request<T>.() -> Unit