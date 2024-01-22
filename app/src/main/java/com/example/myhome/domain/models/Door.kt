package com.example.myhome.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Door(
    val name: String,
    val room: String?,
    val id: Int,
    val favorites: Boolean,
    val snapshot: String? = null
)