package com.github.uemoo.android.data.db.dao

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.github.uemoo.android.data.api.response.GetCatFactsResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * [GetCatFactsResponse] のローカルにキャッシュ戦略
 * 基本的なデータアクセス手段を提供する
 */
internal class CatFactDao @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : Dao<GetCatFactsResponse> {
    override fun insert(value: GetCatFactsResponse) {
        val string = Json.encodeToString<GetCatFactsResponse>(value)
        Log.d("XXX", "CatFactDao#insert:string=$string")
        sharedPreferences.edit {
            putString(KEY, string)
        }
    }

    override fun delete() {
        Log.d("XXX", "CatFactDao#delete")
        sharedPreferences.edit {
            remove(KEY)
        }
    }

    override fun query(): GetCatFactsResponse? {
        val string = sharedPreferences
            .getString(KEY, "")
            .takeIf { !it.isNullOrEmpty() } // null か空文字であれば抜ける
            ?: return null
        Log.d("XXX", "CatFactDao#query:string=$string")
        return Json.decodeFromString<GetCatFactsResponse>(string)
    }

    companion object {
        private const val KEY = "cat"
    }
}
