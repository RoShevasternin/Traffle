package com.puzzlertron.dohistorical.game.actors

import com.puzzlertron.dohistorical.game.utils.advanced.AdvancedGroup
import com.puzzlertron.dohistorical.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}