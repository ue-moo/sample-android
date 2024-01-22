package com.github.uemoo.android.domain.entity

data class CatFact(
    val id: String,
    val user: String,
    val text: String,
    val v: Long,
    val source: String,
    val updatedAt: String,
    val type: String,
    val createdAt: String,
    val deleted: Boolean,
    val used: Boolean,
)
