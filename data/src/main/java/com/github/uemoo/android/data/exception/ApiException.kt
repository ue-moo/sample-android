package com.github.uemoo.android.data.exception

// 一過性の例外
sealed class AbstractApiException(
    open val requestMethod: String,
    open val requestUrl: String,
    open val responseCode: Int,
) : AbstractException()

// 一般的な API で発生する例外
data class ApiException(
    override val requestMethod: String,
    override val requestUrl: String,
    override val responseCode: Int,
) : AbstractApiException(requestMethod, requestUrl, responseCode)

// サービスが利用できなくなるような致命的な例外
sealed class FatalException(
    override val requestMethod: String,
    override val requestUrl: String,
    override val responseCode: Int,
) : AbstractApiException(requestMethod, requestUrl, responseCode) {
    // 強制アップデート
    data class ForceUpdateException(
        override val requestMethod: String,
        override val requestUrl: String,
        override val responseCode: Int,
        val customField: String,
    ) : FatalException(requestMethod, requestUrl, responseCode)
}
