/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.util

import java.util.concurrent.atomic.AtomicBoolean

class OneTime {

    private val flag = AtomicBoolean(true)

    fun use(block: () -> Unit) {
        if (flag.getAndSet(false)) block()
    }

}