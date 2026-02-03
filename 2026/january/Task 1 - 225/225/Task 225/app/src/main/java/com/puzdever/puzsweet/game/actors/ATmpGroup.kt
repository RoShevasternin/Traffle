package com.puzdever.puzsweet.game.actors

import com.puzdever.puzsweet.game.utils.advanced.AdvancedGroup
import com.puzdever.puzsweet.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}