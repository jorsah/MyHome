package com.example.myhome.data.models.db

import com.example.myhome.domain.models.Door
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Required

class DoorRealm : RealmObject {
    @PrimaryKey
    var id: Int = 0

    @Required
    var name: String = ""
    var snapshot: String = ""
    var room: String? = null
    var favorites: Boolean = false
}

fun Door.toRealm() = DoorRealm().also {
    it.id = id
    it.name = name
    it.snapshot = snapshot.orEmpty()
    it.room = room
    it.favorites = favorites
}

fun DoorRealm.toDomain() = Door(
    id = id,
    name = name,
    snapshot = snapshot,
    room = room,
    favorites = favorites,
)
