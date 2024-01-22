package com.github.uemoo.android.data.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal typealias GetCatFactsResponse = List<CatFact>

@Serializable
internal data class CatFact(
    val status: Status,
    @SerialName("_id") val id: String,
    val user: String,
    val text: String,
    @SerialName("__v") val v: Long,
    val source: String,
    val updatedAt: String,
    val type: String,
    val createdAt: String,
    val deleted: Boolean,
    val used: Boolean,
) {
    @Serializable
    data class Status(
        val verified: Boolean,
        val sentCount: Long,
    )
}
