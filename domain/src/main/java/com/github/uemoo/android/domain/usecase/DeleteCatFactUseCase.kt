package com.github.uemoo.android.domain.usecase

import com.github.uemoo.android.domain.repository.CatRepository
import javax.inject.Inject

/**
 * キャッシュを削除する
 */
class DeleteCatFactUseCase @Inject constructor(
    private val catRepository: CatRepository,
) {
    operator fun invoke() {
        return catRepository.clearCatFactsCache()
    }
}
