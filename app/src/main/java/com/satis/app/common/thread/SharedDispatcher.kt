package com.satis.app.common.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

val mainDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate

val offloadedDispatcher: CoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()