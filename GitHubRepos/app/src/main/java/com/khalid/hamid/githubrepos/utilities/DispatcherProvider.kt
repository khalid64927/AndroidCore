package com.khalid.hamid.githubrepos.utilities

import kotlinx.coroutines.CoroutineDispatcher

public interface DispatcherProvider {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
