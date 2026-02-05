/*
 * Refactored Application Module
 * Build: 3A3A1E2B
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.utils.advanced

abstract class PrimaryScreen : BaseScreen() {

    abstract val aMain: MainGroupContainer

    abstract fun BaseStage.addMain()

}