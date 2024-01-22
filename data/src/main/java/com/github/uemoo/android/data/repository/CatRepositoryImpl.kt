package com.github.uemoo.android.data.repository

import com.github.uemoo.android.data.api.CatApi
import com.github.uemoo.android.domain.entity.CatFact
import com.github.uemoo.android.domain.repository.CatRepository
import javax.inject.Inject

internal class CatRepositoryImpl @Inject constructor(
    private val api: CatApi,
) : CatRepository {
    // API レスポンスを Domain の Entity に変換する
    // 腐敗防止層の役割を持つ
    override suspend fun getCatFacts(): List<CatFact> {
        return api.getCatFacts().map {
            CatFact(
                id = it.id,
                user = it.user,
                text = it.text,
                v = it.v,
                source = it.source,
                updatedAt = it.updatedAt,
                type = it.type,
                createdAt = it.createdAt,
                deleted = it.deleted,
                used = it.used,
            )
        }
    }
}
