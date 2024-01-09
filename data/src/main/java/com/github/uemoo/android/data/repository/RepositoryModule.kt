package com.github.uemoo.android.data.repository

import com.github.uemoo.android.domain.repository.SampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    fun bindSampleRepository(impl: SampleRepositoryImpl): SampleRepository
}
