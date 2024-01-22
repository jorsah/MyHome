package com.example.myhome.data.models.server

import com.example.myhome.domain.models.Door
import kotlinx.serialization.Serializable

@Serializable
data class DoorResponse(
    val success: Boolean,
    val data: List<Door>
)
