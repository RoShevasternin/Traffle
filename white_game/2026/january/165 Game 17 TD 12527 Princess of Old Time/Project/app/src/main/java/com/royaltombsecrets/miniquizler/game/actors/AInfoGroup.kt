package com.royaltombsecrets.miniquizler.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedGroup
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedScreen

class AInfoGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefWidth(): Float {
        return 4800f
    }

    override fun addActorsOnGroup() {
        setSize(4800f, 377f)

        var nx = 75f

        List(9) { Image(screen.game.all.frames[it]) }.onEach {
            addActor(it)
            it.setBounds(nx, 0f, 450f, 377f)
            nx += 75 + 450
        }
    }

}