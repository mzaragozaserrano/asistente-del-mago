package com.asistentedelmago.core.model

data class MagicTrick(
    val id: String,
    val userId: String,
    val title: String,
    val description: String? = null,
    val materials: String? = null,
    val angles: String? = null,
    val resetTime: String? = null,
    val videoLinks: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)

