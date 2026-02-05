/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.dataStore

import com.novaburst.pixelrally.game.manager.StorageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class LevelProgress(override val coroutine: CoroutineScope): DataManager<Int>() {

    override val dataStore = StorageManager.Level

    override val flow = MutableStateFlow(1)

    init { initialize() }

}