package com.portalend.fruitomaner.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.portalend.fruitomaner.game.utils.actor.setOnTouchListener
import com.portalend.fruitomaner.game.utils.advanced.AdvancedGroup
import com.portalend.fruitomaner.game.utils.advanced.AdvancedScreen

class AMap(override val screen: AdvancedScreen): AdvancedGroup() {

    private val maps = List(18) { Image(screen.game.all.maps[it]) }

    var block = {}

    init {
        setSize(689f, 4420f)
        var nx = 0f
        var ny = 4007f
        maps.onEachIndexed { index, image ->
            addActor(image)
            image.setBounds(nx,ny,289f,412f)
            image.setOnTouchListener(screen.game.soundUtil) { block() }

            nx += 111+289
            if (index.inc() % 2 == 0) {
                nx = 0f
                ny -= 89+412
            }

        }
    }

    override fun addActorsOnGroup() {}

    override fun getPrefHeight(): Float {
        return height
    }

    override fun getPrefWidth(): Float {
        return width
    }

}