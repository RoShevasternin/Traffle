package com.bigfish.pairtoper.game.actors

import com.bigfish.pairtoper.game.utils.advanced.AdvancedGroup
import com.bigfish.pairtoper.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}