package com.corp.sunlightdesign.usecase.blocks

import com.corp.sunlightdesign.usecase.BaseObserveCoroutinesUseCase


typealias ObserverBlock<T> = BaseObserveCoroutinesUseCase.Request<T>.() -> Unit