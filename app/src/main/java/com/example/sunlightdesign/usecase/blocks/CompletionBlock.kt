package com.example.sunlightdesign.usecase.blocks

import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase


typealias CompletionBlock<T> = BaseCoroutinesUseCase.Request<T>.() -> Unit