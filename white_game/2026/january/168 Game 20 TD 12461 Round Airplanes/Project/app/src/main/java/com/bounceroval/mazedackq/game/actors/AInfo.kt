package com.bounceroval.mazedackq.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedGroup
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedScreen

class AInfo(override val screen: AdvancedScreen): AdvancedGroup() {

    private val image1 = Image(screen.game.all.info1)
    private val image2 = Image(screen.game.all.info2)
    private val image3 = Image(screen.game.all.info3)

    override fun addActorsOnGroup() {}

    override fun getPrefHeight(): Float {
        return height
    }

    init {
        setSize(959f, 4574f)
        addActors(image1,image2,image3)
        image1.setBounds(88f, 3116f, 783f, 1458f)
        image2.setBounds(88f, 1558f, 783f, 1458f)
        image3.setBounds(88f, 0f, 783f, 1458f)
    }

}