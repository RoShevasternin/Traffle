package com.farm.tilerotate.game.actors

import com.farm.tilerotate.game.utils.advanced.AdvancedGroup
import com.farm.tilerotate.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}