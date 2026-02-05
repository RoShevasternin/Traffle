/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.dataStore

import com.novaburst.pixelrally.game.manager.StorageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

class UserProfile(override val coroutine: CoroutineScope): JsonSerializer<DataUser>(
    serializer   = DataUser.serializer(),
    deserializer = DataUser.serializer(),
) {

    override val dataStore = StorageManager.User

    override val flow = MutableStateFlow(
        DataUser(
            nickname             = "",
            currentAvatarIndex = -1,
            listBuyedAvatarIndex = mutableListOf(0)
        )
    )

    init { initialize() }

}

@Serializable
data class DataUser(
    var nickname            : String,
    var currentAvatarIndex  : Int,
    var listBuyedAvatarIndex: MutableList<Int>
)