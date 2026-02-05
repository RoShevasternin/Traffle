/*
 * Refactored Application Module
 * Build: 3FD1029D
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Disposable
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup

class ScrollableContainer(private val group: BaseGroup): ScrollPane(group), Disposable {

    override fun dispose() {
        group.dispose()
    }

}