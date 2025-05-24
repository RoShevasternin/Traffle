package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Disposable
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup

class AScrollPane(private val group: AdvancedGroup): ScrollPane(group), Disposable {

    override fun dispose() {
        group.dispose()
    }

}