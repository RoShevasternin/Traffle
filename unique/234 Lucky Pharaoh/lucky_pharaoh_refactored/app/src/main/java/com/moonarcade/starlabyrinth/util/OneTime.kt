/*
 * Refactored Application Module
 * Build: 9D22D879
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.util

import java.util.concurrent.atomic.AtomicBoolean

class OneTime {

    private val flag = AtomicBoolean(true)

    fun use(block: () -> Unit) {
        if (flag.getAndSet(false)) block()
    }

}