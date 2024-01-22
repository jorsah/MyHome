package com.example.myhome.data.datasources.service

import com.example.myhome.data.models.server.CamerasResponse
import com.example.myhome.data.models.server.DoorResponse
import com.example.myhome.di.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {
    override suspend fun getCameras(): CamerasResponse
            = httpClient.get("$BASE_URL/cameras/").body()

    override suspend fun getDoors(): DoorResponse
            = httpClient.get("$BASE_URL/doors/").body()
}
