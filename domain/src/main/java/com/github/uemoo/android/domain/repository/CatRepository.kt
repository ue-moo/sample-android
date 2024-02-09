package com.github.uemoo.android.domain.repository

import com.github.uemoo.android.domain.entity.CatFact

interface CatRepository {
    suspend fun getCatFacts(forceRefresh: Boolean): List<CatFact>
    fun clearCatFactsCache()
}
