package com.example.myhome.data.repositories

import com.example.myhome.data.datasources.dao.Dao
import com.example.myhome.data.datasources.service.ApiService
import com.example.myhome.data.models.*
import com.example.myhome.data.models.db.*
import com.example.myhome.domain.models.*
import com.example.myhome.domain.repositories.MyHouseRepository
import io.realm.kotlin.query.RealmResults
import javax.inject.Inject

class MyHouseRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val db: Dao
) : MyHouseRepository {

    override suspend fun getCameras(forceLoad: Boolean): ApiResult<List<Camera>> {
        return try {
            val items: RealmResults<CameraRealm> = db.getCameras()

            if (items.isEmpty() || forceLoad) {
                val response = service.getCameras().data.cameras

                val listRealm = response.map {
                    it.toRealm()
                }

                db.insertCameras(listRealm)
                ApiResult.Success(response)

            } else {
                val cameraDomainList = items.map { it.toDomain() }
                ApiResult.Success(cameraDomainList)
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message.orEmpty())
        }
    }

    override suspend fun updateCamera(camera: Camera) {
        db.updateCamera(camera.toRealm())
    }

    override suspend fun getDoors(forceLoad: Boolean): ApiResult<List<Door>> {
        return try {
            val items: RealmResults<DoorRealm> = db.getDoors()

            if (items.isEmpty() || forceLoad) {
                val response = service.getDoors().data

                val listRealm = response.map {
                    it.toRealm()
                }

                db.insertDoors(listRealm)
                ApiResult.Success(response)

            } else {
                val doorsDomainList = items.map { it.toDomain() }
                ApiResult.Success(doorsDomainList)
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message.orEmpty())
        }
    }

    override suspend fun updateDoor(door: Door) {
        db.updateDoor(door.toRealm())
    }
}