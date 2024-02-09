package com.github.uemoo.android.data.api.response

import kotlinx.serialization.Serializable

/**
 * 強制アップデート
 */
@Serializable
internal data class ForceUpdateResponse(
    val message: String,
)
