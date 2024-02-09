package com.github.uemoo.android.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.uemoo.android.data.exception.AbstractApiException
import com.github.uemoo.android.data.exception.AbstractAppException
import com.github.uemoo.android.data.exception.ApiException
import com.github.uemoo.android.data.exception.AppException
import com.github.uemoo.android.data.exception.FatalException
import com.github.uemoo.android.sample.databinding.ActivityWelcomeBinding
import com.github.uemoo.android.sample.error.ErrorStateHolder
import com.github.uemoo.android.sample.ui.ScreenType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    @Inject
    lateinit var errorStateHolder: ErrorStateHolder

    private lateinit var binding: ActivityWelcomeBinding
    private val viewModel by viewModels<MainViewModel>()

    private val loginResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { viewModel.handleLineLoginResult(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.refreshButton.setOnClickListener {
            viewModel.fetch()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    // すべての情報が UiState に詰まっているため、collect の処理が大きくなるのが難点
                    // Compose であれば差分があった箇所のみ部分的に更新できるので解消する
                    val type = uiState.type
                    binding.titleTextview.text = type.title
                    binding.descriptionTextview.text = type.description
                    binding.socialButton.text = type.socialButtonText
                    binding.socialButton.setOnClickListener {
                        val text = when (type) {
                            ScreenType.Login -> "LINEでログインします"
                            ScreenType.Register -> "LINEで新規登録します"
                        }
                        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
                    }
                    binding.nextButton.text = type.nextButtonText
                    binding.nextButton.setOnClickListener {
                        viewModel.nextScreenType()
                    }
                }
            }
        }

        /*setContent {
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
        }*/

        // エラーをユーザーに伝える
        lifecycleScope.launch {
            errorStateHolder.error.filterNotNull().collect { error ->
                errorStateHolder.consumeError()
                // エラーによって UI を出し分ける
                val text = when (error) {
                    is ApiException -> error.requestUrl
                    is FatalException.ForceUpdateException -> error.customField
                    is AppException -> error.customField
                }
                Toast.makeText(this@MainActivity, "ERROR：${error.javaClass.simpleName}:text=$text", Toast.LENGTH_LONG).show()
            }
        }

        // データを更新
        viewModel.fetch()
    }
}
