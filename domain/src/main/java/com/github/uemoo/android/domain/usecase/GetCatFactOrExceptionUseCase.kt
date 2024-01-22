package com.github.uemoo.android.domain.usecase

import com.github.uemoo.android.domain.entity.CatFact
import com.github.uemoo.android.domain.repository.CatRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

/**
 * データをランダムにひとつ取得するか、例外を投げる
 */
class GetCatFactOrExceptionUseCase @Inject constructor(
    private val catRepository: CatRepository,
) {
    suspend operator fun invoke(): CatFact {
        val rand = Random.nextInt()
        if (rand % 3 == 0) {
            delay(1000)
            throw IllegalStateException("エラーが発生しました")
        } else {
            return catRepository.getCatFacts().random()
        }
    }
}
