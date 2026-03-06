package com.safaripuz.puzsurfacefari.game.actors

import com.safaripuz.puzsurfacefari.game.utils.advanced.AdvancedGroup
import com.safaripuz.puzsurfacefari.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}