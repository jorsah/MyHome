package com.example.myhome.data.datasources.dao

import com.example.myhome.data.models.db.CameraRealm
import com.example.myhome.data.models.db.DoorRealm
import io.realm.kotlin.query.RealmResults

interface Dao {
    suspend fun updateCamera(camera: CameraRealm)
    suspend fun insertCameras(cameras: List<CameraRealm>)
    suspend fun getCameras(): RealmResults<CameraRealm>
    suspend fun updateDoor(door: DoorRealm)
    suspend fun insertDoors(doors: List<DoorRealm>)
    suspend fun getDoors(): RealmResults<DoorRealm>
}