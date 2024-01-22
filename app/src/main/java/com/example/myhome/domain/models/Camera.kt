package com.example.myhome.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Camera(
    val name: String,
    val snapshot: String,
    val room: String?,
    val id: Int,
    val favorites: Boolean,
    val rec: Boolean
)