package com.github.uemoo.android.sample

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.uemoo.android.domain.entity.CatFact
import com.github.uemoo.android.domain.usecase.DeleteCatFactUseCase
import com.github.uemoo.android.domain.usecase.GetCatFactUseCase
import com.github.uemoo.android.sample.coroutine.LaunchSafe
import com.linecorp.linesdk.auth.LineLoginApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    launchSafe: LaunchSafe,
    private val getCatFactOrExceptionUseCase: GetCatFactUseCase,
    private val deleteCatFactUseCase: DeleteCatFactUseCase,
) : ViewModel(), LaunchSafe by launchSafe {

    private val _catFact = MutableStateFlow<CatFact?>(null)
    val catFact = _catFact.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    fun fetch() {
        _isLoading.update { true }
        // launchSafe 内で漏れた例外は共通のエラーハンドリングに移行する
        // 画面固有の例外のみ try-catch などで独自のハンドリング行う
        viewModelScope.launchSafe {
            _catFact.update { getCatFactOrExceptionUseCase() }
        }.invokeOnCompletion { e ->
            // CancellationException はコルーチンのキャンセルを伝播するために使用するので流す
            val isError = e != null && e !is CancellationException
            _isError.update { isError }
            _isLoading.update { false }
        }
    }

    fun deleteCatFactCache() {
        deleteCatFactUseCase()
    }

    /**
     * LINE のログイン結果をハンドリングする
     */
    fun handleLineLoginResult(activityResult: ActivityResult) {
        if (activityResult.resultCode != Activity.RESULT_OK) return

        val result = LineLoginApi.getLoginResultFromIntent(activityResult.data)

        // ログイン失敗
        if (!result.isSuccess) {
            Log.d("XXX", "login failed... :${result.errorData}")
            return
        }
        // ログイン成功
        Log.d("XXX", "login success! :${result.lineCredential?.accessToken?.tokenString}")
    }
}
