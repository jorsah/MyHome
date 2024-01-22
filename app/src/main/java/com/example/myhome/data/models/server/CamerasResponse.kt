package com.example.myhome.data.models.server

import com.example.myhome.domain.models.CamerasData
import kotlinx.serialization.Serializable


@Serializable
data class CamerasResponse(
    val success: Boolean,
    val data: CamerasData
)