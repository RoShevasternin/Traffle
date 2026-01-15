package com.cosmicbounce.galaxytic.game.actors

import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedGroup
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedScreen

class AInfoGroup(override val screen: AdvancedScreen, val infoList: List<AInfo>): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return 4674f
    }

    override fun addActorsOnGroup() {
        setSize(580f, 4674f)

        var ny = 4056f

        infoList.onEach {
            addActor(it)
            it.setBounds(0f, ny, 580f, 618f)
            ny -= 58 + 618
        }
    }

}