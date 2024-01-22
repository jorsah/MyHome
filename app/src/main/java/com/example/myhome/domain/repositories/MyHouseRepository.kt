package com.example.myhome.domain.repositories

import com.example.myhome.data.models.ApiResult
import com.example.myhome.domain.models.Camera
import com.example.myhome.domain.models.Door

interface MyHouseRepository {
    suspend fun getCameras(forceLoad: Boolean = false): ApiResult<List<Camera>>
    suspend fun updateCamera(camera: Camera)
    suspend fun getDoors(forceLoad: Boolean = false): ApiResult<List<Door>>
    suspend fun updateDoor(door: Door)
}