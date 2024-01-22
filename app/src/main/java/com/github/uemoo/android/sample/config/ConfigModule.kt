package com.github.uemoo.android.sample.config

import com.github.uemoo.android.domain.config.AppConfig
import com.github.uemoo.android.sample.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal object ConfigModule {
    @Provides
    fun provideAppConfig(): AppConfig = AppConfig(
        baseUrl = BuildConfig.BASE_URL,
    )
}
