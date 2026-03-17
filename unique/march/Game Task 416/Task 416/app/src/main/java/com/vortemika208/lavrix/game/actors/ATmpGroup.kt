package com.vortemika208.lavrix.game.actors

import com.vortemika208.lavrix.game.utils.advanced.AdvancedGroup
import com.vortemika208.lavrix.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}