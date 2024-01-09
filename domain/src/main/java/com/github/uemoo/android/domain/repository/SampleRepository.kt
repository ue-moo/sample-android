package com.github.uemoo.android.domain.repository

import com.github.uemoo.android.domain.entity.Token

interface SampleRepository {
    suspend fun getTokenOrException(): Token
}
