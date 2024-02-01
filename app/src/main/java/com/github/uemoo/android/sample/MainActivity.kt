package com.github.uemoo.android.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.github.uemoo.android.sample.error.ErrorStateHolder
import com.github.uemoo.android.sample.ui.login.LoginScreen
import com.github.uemoo.android.sample.ui.theme.SampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    @Inject
    lateinit var errorStateHolder: ErrorStateHolder

    private val viewModel by viewModels<MainViewModel>()

    private val loginResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { viewModel.handleLineLoginResult(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val catFact by viewModel.catFact.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()
            val isError by viewModel.isError.collectAsState()

            SampleTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(all = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Crossfade(
                            targetState = isError,
                            modifier = Modifier.fillMaxWidth(),
                            label = "Sample",
                        ) { isError ->
                            if (isError) {
                                Text(
                                    text = "エラーが発生しました",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                )
                            } else {
                                catFact?.let {
                                    Text(
                                        text = it.text,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }

                        LoginScreen(
                            onLineLoginClick = loginResultLauncher::launch,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(weight = 1f),
                        )

                        Spacer(modifier = Modifier.height(height = 20.dp))

                        Button(
                            onClick = viewModel::fetch,
                        ) {
                            Text(text = "更新する")
                        }
                        Button(
                            onClick = viewModel::deleteCatFactCache,
                        ) {
                            Text(text = "キャッシュを削除する")
                        }
                    }

                    AnimatedVisibility(
                        visible = isLoading,
                        modifier = Modifier
                            .matchParentSize()
                            .background(color = Color.White),
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        // エラーをユーザーに伝える
        lifecycleScope.launch {
            errorStateHolder.error.filterNotNull().collect { error ->
                errorStateHolder.consumeError()
                Toast.makeText(this@MainActivity, "失敗：${error.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // データを更新
        viewModel.fetch()
    }
}
