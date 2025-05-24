package com.shoote.maniapink.game.actors

import com.shoote.maniapink.game.utils.advanced.AdvancedGroup
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}