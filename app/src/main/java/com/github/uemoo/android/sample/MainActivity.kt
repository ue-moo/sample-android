package com.github.uemoo.android.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.github.uemoo.android.sample.error.ErrorStateHolder
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Button(
                        onClick = viewModel::fetch,
                    ) {
                        Text(text = "更新する")
                    }

                    val isLoading by viewModel.isLoading.collectAsState()

                    if (isLoading) {
                        Box(
                            modifier = Modifier.matchParentSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        // データを取得
        lifecycleScope.launch {
            viewModel.token.filterNotNull().collect { token ->
                Toast.makeText(this@MainActivity, "成功：$token", Toast.LENGTH_SHORT).show()
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
