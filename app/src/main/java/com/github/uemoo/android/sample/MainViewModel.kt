package com.github.uemoo.android.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.uemoo.android.domain.entity.Token
import com.github.uemoo.android.domain.repository.SampleRepository
import com.github.uemoo.android.sample.coroutine.LaunchSafe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    launchSafe: LaunchSafe,
    private val repository: SampleRepository,
) : ViewModel(), LaunchSafe by launchSafe {

    private val _token = MutableStateFlow<Token?>(null)
    val token = _token.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    fun fetch() {
        _isLoading.update { true }
        // launchSafe 内で漏れた例外は共通のエラーハンドリングに移行する
        // 画面固有の例外のみ try-catch などで独自のハンドリング行う
        viewModelScope.launchSafe {
            _token.update { repository.getTokenOrException() }
        }.invokeOnCompletion { e ->
            val isError = e != null && e !is CancellationException
            _isError.update { isError }
            _isLoading.update { false }
        }
    }
}
