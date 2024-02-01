package com.github.uemoo.android.data.db.dao

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun provideCatDao(
        sharedPreferences: SharedPreferences
    ): CatFactDao = CatFactDao(sharedPreferences)
}
