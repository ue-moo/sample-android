package com.github.uemoo.android.data.repository

import android.util.Log
import com.github.uemoo.android.data.api.CatApi
import com.github.uemoo.android.data.db.dao.CatFactDao
import com.github.uemoo.android.domain.entity.CatFact
import com.github.uemoo.android.domain.repository.CatRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

internal class CatRepositoryImpl @Inject constructor(
    private val api: CatApi,
    private val dao: CatFactDao,
) : CatRepository {
    // API レスポンスを Domain の Entity に変換する
    // 腐敗防止層の役割を持つ
    // ローカル DB にキャッシュがあればキャッシュを、なければ API からデータを取得する
    // API 取得時に例外が発生する場合がある
    override suspend fun getCatFacts(forceRefresh: Boolean): List<CatFact> {
        val cache = dao.query()
        return if (cache != null && !forceRefresh) {
            Log.d("XXX", "キャッシュから取得しました")
            cache
        } else {
            val response = if (Random.nextInt() % 3 == 0) {
                delay(1000)
                throw IllegalStateException("エラーが発生しました")
            } else {
                Log.d("XXX", "API から取得しました")
                api.getCatFacts()
            }
            // ローカル DB にキャッシュする
            dao.insert(response)
            response
        }.map {
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

    override fun clearCatFactsCache() {
        dao.delete()
    }
}
