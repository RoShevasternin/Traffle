/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Disposable
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup

class ScrollablePanel(private val group: ComponentGroup): ScrollPane(group), Disposable {

    override fun dispose() {
        group.dispose()
    }

}