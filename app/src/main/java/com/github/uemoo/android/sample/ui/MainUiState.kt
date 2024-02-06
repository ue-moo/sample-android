package com.github.uemoo.android.sample.ui

import com.github.uemoo.android.domain.entity.CatFact

// UiState は画面の描画に必要な情報をひとまとめにしたもの
// Single Source of Truth の考えに基づき
// UI が参照すべき情報の全てが詰まっている
data class MainUiState(
    val type: ScreenType,
    val catFact: CatFact?,
    val isLoading: Boolean,
    val isError: Boolean,
) {
    companion object {
        val InitialValue = MainUiState(
            type = ScreenType.Login,
            catFact = null,
            isLoading = false,
            isError = false,
        )
    }
}

sealed interface ScreenType {
    // 変更箇所をすべて定義しておくことで、UI で分岐が不要になる
    val title: String

    // 状態によっては表示しないものは nullable とする
    val description: String?
    val socialButtonText: String
    val nextButtonText: String
    fun next(): ScreenType

    data object Login : ScreenType {
        override val title: String = "ログイン"
        override val description: String = "利用規約とプライバシーポリシーに同意してください"
        override val socialButtonText: String = "LINEでログイン"
        override val nextButtonText: String = "新規登録"
        override fun next(): ScreenType = Register
    }

    data object Register : ScreenType {
        override val title: String = "新規登録"
        override val description: String? = null
        override val socialButtonText: String = "LINEではじめる"
        override val nextButtonText: String = "ログイン"
        override fun next(): ScreenType = Login
    }
}
