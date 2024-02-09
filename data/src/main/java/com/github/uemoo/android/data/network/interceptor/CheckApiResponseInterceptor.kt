package com.github.uemoo.android.data.network.interceptor

import android.util.Log
import com.github.uemoo.android.data.api.response.ForceUpdateResponse
import com.github.uemoo.android.data.network.StatusCode
import com.github.uemoo.android.data.exception.ApiException
import com.github.uemoo.android.data.exception.FatalException
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * API レスポンスを検証して、必要であれば例外を投げる
 */
internal class CheckApiResponseInterceptor @Inject constructor() : Interceptor {

    private val json = Json { ignoreUnknownKeys = true }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        Log.d("XXX", "CheckApiResponseInterceptor#intercept:response=${response}")

        // 成功であれば抜ける
        if (response.isSuccessful) return response

        // レスポンスが正常でなければ例外を生成して投げる
        throw makeApiException(
            request = request,
            response = response,
        )
    }

    private fun makeApiException(
        request: Request,
        response: Response,
    ): Throwable {
        val bodyString = requireNotNull(response.body?.string())
        return when (StatusCode.get(response.code)) {
            // 強制アップデート
            StatusCode.ForceUpdate -> {
                // レスポンスからデータを取得するためにデコードする
                val body = json.decodeFromString(ForceUpdateResponse.serializer(), bodyString)

                FatalException.ForceUpdateException(
                    requestMethod = request.method,
                    requestUrl = request.url.toString(),
                    responseCode = response.code,
                    customField = body.message,
                )
            }

            // 上記以外
            StatusCode.NonFatal -> {
                ApiException(
                    requestMethod = request.method,
                    requestUrl = request.url.toString(),
                    responseCode = response.code,
                )
            }
        }
    }
}
