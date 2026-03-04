package com.circuser.pairante.game.actors

import com.circuser.pairante.game.utils.advanced.AdvancedGroup
import com.circuser.pairante.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}