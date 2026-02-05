/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.dataStore

import com.novaburst.pixelrally.game.manager.StorageManager
import com.novaburst.pixelrally.game.utils.GLOBAL_listDataLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

class LevelBonusData(override val coroutine: CoroutineScope): JsonSerializer<List<Int>>(
    serializer = ListSerializer(Int.serializer()),
    deserializer = ListSerializer(Int.serializer()),
) {

    override val dataStore = StorageManager.LevelJackpot

    override val flow = MutableStateFlow(List(GLOBAL_listDataLocation.size) { 0 }) // 0..27

    init { initialize() }

}