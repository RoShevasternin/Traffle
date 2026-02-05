/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskExecutor(coroutineScope: CoroutineScope, count: Int, doAfterComplete: () -> Unit = {}) {

    private val flow = MutableStateFlow(0)

    var block: () -> Unit = doAfterComplete

    init {
        coroutineScope.launch {
            flow.collect { if (it == count) block() }
        }
    }

    // Function implementation
    fun complete() { flow.value += 1 }

    fun reset() {
        flow.value = 0
    }

}