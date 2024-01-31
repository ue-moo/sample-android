package com.github.uemoo.android.sample.ui.login

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun LoginScreen(
    onLineLoginClick: (Intent) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginScreen(
        uiState = uiState,
        onLineLoginClick = {
            onLineLoginClick(viewModel.createLineLoginIntent())
        },
    )
}

@Composable
private fun LoginScreen(
    uiState: LoginUiState,
    onLineLoginClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            onClick = onLineLoginClick,
        ) {
            Text(text = "Log in with LINE")
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onLineLoginClick = {},
    )
}
