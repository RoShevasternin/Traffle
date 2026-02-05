/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.dataStore

import com.novaburst.pixelrally.game.manager.StorageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class GoldBalance(override val coroutine: CoroutineScope): DataManager<Int>() {

    override val dataStore = StorageManager.Gold

    override val flow = MutableStateFlow(10_000)

    init { initialize() }

}