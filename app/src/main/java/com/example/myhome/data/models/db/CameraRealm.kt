package com.example.myhome.data.models.db

import com.example.myhome.domain.models.Camera
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Required

class CameraRealm : RealmObject {
    @PrimaryKey
    var id: Int = 0

    @Required
    var name: String = ""
    var snapshot: String = ""
    var room: String? = null
    var favorites: Boolean = false
    var rec: Boolean = false
}



fun Camera.toRealm() = CameraRealm().also {
    it.id = id
    it.name = name
    it.snapshot = snapshot
    it.room = room
    it.favorites = favorites
    it.rec = rec
}

fun CameraRealm.toDomain() = Camera(
    id = id,
    name = name,
    snapshot = snapshot,
    room = room,
    favorites = favorites,
    rec = rec
)
