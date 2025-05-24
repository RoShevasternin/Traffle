package com.gorillaz.puzzlegame.game.dataStore

import com.gorillaz.puzzlegame.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

class DS_User(override val coroutine: CoroutineScope): DataStoreJsonUtil<DataUser>(
    serializer   = DataUser.serializer(),
    deserializer = DataUser.serializer(),
) {

    override val dataStore = DataStoreManager.User

    override val flow = MutableStateFlow(
        DataUser(
            nickname             = "",
            currentAvatarIndex   = -1,
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