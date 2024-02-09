package com.github.uemoo.android.sample.error

import com.github.uemoo.android.data.exception.AbstractException
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@ActivityRetainedScoped
internal class ErrorStateHolder @Inject constructor() {
    private val _error = MutableStateFlow<AbstractException?>(null)
    val error = _error.asStateFlow()

    fun produceError(error: AbstractException) {
        _error.update { error }
    }

    fun consumeError() {
        _error.update { null }
    }
}
