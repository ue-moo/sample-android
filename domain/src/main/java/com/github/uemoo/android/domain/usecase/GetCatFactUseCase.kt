package com.github.uemoo.android.domain.usecase

import com.github.uemoo.android.domain.entity.CatFact
import com.github.uemoo.android.domain.repository.CatRepository
import javax.inject.Inject

/**
 * データをランダムにひとつ取得する
 */
class GetCatFactUseCase @Inject constructor(
    private val catRepository: CatRepository,
) {
    suspend operator fun invoke(): CatFact {
        return catRepository.getCatFacts().random()
    }
}
