package com.example.myhome.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CamerasData(
    val room: List<String>,
    val cameras: List<Camera>
)
