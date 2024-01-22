package com.example.myhome.ui.base.model

import com.example.myhome.domain.models.Camera
import com.example.myhome.domain.models.Door

data class ListItemUi(
    val name: String,
    val snapshot: String,
    val id: Int,
    val favorites: Boolean,
) {
    companion object {
        val Empty = ListItemUi(
            name = "",
            snapshot = "",
            id = -1,
            favorites = false
        )
    }
}

fun Camera.toUi() = ListItemUi(
    name = name,
    snapshot = snapshot,
    id = id,
    favorites = favorites
)

fun Door.toUi() = ListItemUi(
    name = name,
    snapshot = snapshot.orEmpty(),
    id = id,
    favorites = favorites
)
