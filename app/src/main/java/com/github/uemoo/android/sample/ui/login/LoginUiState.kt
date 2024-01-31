package com.github.uemoo.android.sample.ui.login

import androidx.compose.runtime.Stable

@Stable
data class LoginUiState(
    val isLoading: Boolean,
) {
    companion object {
        val InitialValue = LoginUiState(
            isLoading = false,
        )
    }
}
