package dev.androidbroadcast.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

public class AppDispatchers(
    public val default: CoroutineDispatcher = Dispatchers.Default,
    public val io: CoroutineDispatcher = Dispatchers.IO,
    public val main: MainCoroutineDispatcher = Dispatchers.Main,
    public val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
)
