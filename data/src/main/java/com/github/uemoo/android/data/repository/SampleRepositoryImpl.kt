package com.github.uemoo.android.data.repository

import android.util.Log
import com.github.uemoo.android.data.api.SampleApi
import com.github.uemoo.android.domain.entity.Token
import com.github.uemoo.android.domain.repository.SampleRepository
import org.opencv.android.OpenCVLoader
import javax.inject.Inject
import kotlin.random.Random

internal class SampleRepositoryImpl @Inject constructor(
    private val api: SampleApi,
) : SampleRepository {
    override suspend fun getTokenOrException(): Token {
        // opencv の参照を確認
        Log.d("XXX", OpenCVLoader.OPENCV_VERSION)

        val rand = Random.nextInt()
        if (rand % 3 == 0) {
            throw IllegalStateException("ランダムエラーが発生しました")
        } else {
            return Token(api.getToken())
        }
    }
}
