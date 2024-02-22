package com.github.uemoo.android.data.network.interceptor

import com.github.uemoo.android.data.db.dao.AuthTokenDao
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AddAuthHeaderInterceptor @Inject constructor(
    private val dao: AuthTokenDao,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .header("Authorization", "Bearer ${dao.query()}")
            .build()

        return chain.proceed(newRequest)
    }
}
