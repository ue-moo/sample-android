package com.github.uemoo.android.data.api

import com.github.uemoo.android.data.api.response.GetCatFactsResponse
import retrofit2.http.GET

internal interface CatApi {
    @GET("/facts")
    suspend fun getCatFacts(): GetCatFactsResponse
}
