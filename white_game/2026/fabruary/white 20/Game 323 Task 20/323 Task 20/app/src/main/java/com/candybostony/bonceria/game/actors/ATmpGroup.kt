package com.candybostony.bonceria.game.actors

import com.candybostony.bonceria.game.utils.advanced.AdvancedGroup
import com.candybostony.bonceria.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}