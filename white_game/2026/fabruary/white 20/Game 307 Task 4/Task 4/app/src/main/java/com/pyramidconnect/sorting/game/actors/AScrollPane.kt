package com.pyramidconnect.sorting.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Disposable
import com.pyramidconnect.sorting.game.utils.advanced.AdvancedGroup
import com.pyramidconnect.sorting.util.log

class AScrollPane(private val group: AdvancedGroup): ScrollPane(group), Disposable {

    override fun dispose() {
        group.dispose()
    }

}