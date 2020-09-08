package com.example.sunlightdesign.usecase

import com.example.sunlightdesign.usecase.blocks.ObserverBlock
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlin.coroutines.CoroutineContext


abstract class BaseObserveCoroutinesUseCase<T> : CoroutineScope {

    private var parentJob: Job = Job()
    override val coroutineContext: CoroutineContext = parentJob + Dispatchers.Main

    protected abstract fun observe(): ReceiveChannel<T>

    fun execute(block: ObserverBlock<T>) {
        val response = Request<T>().apply { block() }
        unsubscribe()
        parentJob = Job()
        launch(parentJob) {
            val channel = observe()
            for (items in channel) {
                response(items)
            }
        }
    }

    fun execute(actor: SendChannel<T>) {
        unsubscribe()
        parentJob = Job()
        launch(parentJob) {
            val channel = observe()
            for (items in channel) {
                actor.send(items)
            }
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class Request<T> {
        private var onChange: ((T) -> Unit)? = null

        fun onChange(block: (T) -> Unit) {
            onChange = block
        }

        operator fun invoke(result: T) {
            onChange?.invoke(result)
        }

    }

}

