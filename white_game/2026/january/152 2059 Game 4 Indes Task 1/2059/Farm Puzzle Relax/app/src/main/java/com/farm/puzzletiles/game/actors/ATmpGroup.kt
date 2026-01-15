package com.farm.puzzletiles.game.actors

import com.farm.puzzletiles.game.utils.advanced.AdvancedGroup
import com.farm.puzzletiles.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}