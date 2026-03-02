package com.wintergroup.cupcakejump.game.actors

import com.wintergroup.cupcakejump.game.utils.advanced.AdvancedGroup
import com.wintergroup.cupcakejump.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}