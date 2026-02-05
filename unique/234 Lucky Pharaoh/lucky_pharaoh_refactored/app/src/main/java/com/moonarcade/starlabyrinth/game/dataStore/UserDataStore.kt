/*
 * Refactored Application Module
 * Build: B3957256
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.dataStore

import com.moonarcade.starlabyrinth.game.manager.PersistenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

class UserDataStore(override val coroutine: CoroutineScope): JsonStorageUtil<DataUser>(
    serializer = DataUser.serializer(),
    deserializer = DataUser.serializer(),
) {

    override val informationStore = PersistenceManager.User

    override val flow = MutableStateFlow(
        DataUser(
            nickname = "",
            presentAvatarIndex = -1,
            collectionBuyedAvatarIndex = mutableListOf(0)
        )
    )

    init { initialize() }

}

@Serializable
data class DataUser(
    var nickname            : String,
    var presentAvatarIndex  : Int,
    var collectionBuyedAvatarIndex: MutableList<Int>
)