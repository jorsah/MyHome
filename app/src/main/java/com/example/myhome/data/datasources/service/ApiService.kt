package com.example.myhome.data.datasources.service

import com.example.myhome.data.models.server.CamerasResponse
import com.example.myhome.data.models.server.DoorResponse

interface ApiService {
    suspend fun getCameras(): CamerasResponse
    suspend fun getDoors(): DoorResponse
}