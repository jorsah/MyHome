package com.example.myhome.data.datasources.dao

import com.example.myhome.data.models.db.CameraRealm
import com.example.myhome.data.models.db.DoorRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class DaoImpl(private val realm: Realm) : Dao {

    override suspend fun updateCamera(camera: CameraRealm) {
        realm.write {
            query<CameraRealm>().find().firstOrNull {
                it.id == camera.id
            }?.let { item ->
                findLatest(item)?.let {
                    it.name = camera.name
                }
            }
        }
    }

    override suspend fun insertCameras(cameras: List<CameraRealm>) {
        realm.writeBlocking {
            delete<CameraRealm>()
            cameras.forEach {
                copyToRealm(it)
            }
        }
    }

    override suspend fun getCameras(): RealmResults<CameraRealm> = realm.query<CameraRealm>().find()

    override suspend fun updateDoor(door: DoorRealm) {
        realm.write {
            query<DoorRealm>().find().firstOrNull {
                it.id == door.id
            }?.let { item ->
                findLatest(item)?.let {
                    it.name = door.name
                }
            }
        }
    }

    override suspend fun insertDoors(doors: List<DoorRealm>) {
        realm.writeBlocking {
            delete<DoorRealm>()
            doors.forEach {
                copyToRealm(it)
            }
        }

    }

    override suspend fun getDoors(): RealmResults<DoorRealm> =
        realm.query<DoorRealm>().find()

}
