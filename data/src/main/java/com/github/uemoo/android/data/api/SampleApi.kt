package com.github.uemoo.android.data.api

import kotlinx.coroutines.delay

internal class SampleApi {
    suspend fun getToken(): String {
        delay(1000)
        return "sampleToken"
    }
}
