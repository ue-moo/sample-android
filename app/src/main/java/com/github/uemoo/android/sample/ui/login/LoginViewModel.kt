package com.github.uemoo.android.sample.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
internal class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState.InitialValue)
    val uiState = _uiState.asStateFlow()

    fun createLineLoginIntent(): Intent {
        val params = LineAuthenticationParams.Builder()
            .scopes(listOf(Scope.PROFILE))
            .build()
        return LineLoginApi.getLoginIntent(context, "TODO", params)
    }
}
