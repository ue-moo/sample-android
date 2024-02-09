package com.github.uemoo.android.data.network

internal enum class StatusCode(val value: Int?) {
    // 強制アップデート
    ForceUpdate(461),

    // 上記以外
    NonFatal(null);

    companion object {
        fun get(value: Int): StatusCode = StatusCode.entries.firstOrNull { it.value == value } ?: NonFatal
    }
}
