package com.github.uemoo.android.domain.repository

import com.github.uemoo.android.domain.entity.CatFact

interface CatRepository {
    suspend fun getCatFacts(): List<CatFact>
    fun clearCatFactsCache()
}
