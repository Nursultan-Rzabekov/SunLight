package com.corp.sunlightdesign.usecase.blocks

import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


typealias CompletionBlock<T> = BaseCoroutinesUseCase.Request<T>.() -> Unit