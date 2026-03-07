package com.oceanstar.ballduinstar.game.actors

import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedGroup
import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}