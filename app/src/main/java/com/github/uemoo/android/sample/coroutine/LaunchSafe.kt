package com.github.uemoo.android.sample.coroutine

import com.github.uemoo.android.data.exception.AbstractAppException
import com.github.uemoo.android.data.exception.AbstractException
import com.github.uemoo.android.data.exception.AppException
import com.github.uemoo.android.sample.error.ErrorStateHolder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("unused")
@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface LaunchSafeModule {
    @Binds
    fun bindLaunchSafe(impl: LaunchSafeImpl): LaunchSafe
}

internal interface LaunchSafe {
    fun CoroutineScope.launchSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job
}

internal class LaunchSafeImpl @Inject constructor(
    private val errorStateHolder: ErrorStateHolder,
) : LaunchSafe {

    private val policy: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val error = when (throwable) {
            is AbstractException -> throwable
            else -> {
                // TODO: 想定外の例外の処理
                AppException(customField = throwable.message ?: "no message")
            }
        }
        errorStateHolder.produceError(error = error)
    }

    override fun CoroutineScope.launchSafe(
        context: CoroutineContext,
        start: CoroutineStart,
        block: suspend CoroutineScope.() -> Unit,
    ): Job = launch(
        context = context + policy,
        start = start,
        block = block,
    )
}
