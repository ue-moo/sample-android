package com.github.uemoo.android.data.exception

// アプリで発生する例外
sealed class AbstractAppException(
    open val customField: String,
) : AbstractException()

data class AppException(
    override val customField: String,
) : AbstractAppException(customField)
