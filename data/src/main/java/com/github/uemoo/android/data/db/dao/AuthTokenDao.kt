package com.github.uemoo.android.data.db.dao

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

internal class AuthTokenDao @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : Dao<String> {
    override fun insert(value: String) {
        sharedPreferences.edit {
            putString(KEY, value)
        }
    }

    override fun delete() {
        sharedPreferences.edit {
            remove(KEY)
        }
    }

    override fun query(): String? {
        return sharedPreferences
            .getString(KEY, "sampleAuthToken")
            .takeIf { !it.isNullOrEmpty() }
    }

    companion object {
        private const val KEY = "authToken"
    }
}
