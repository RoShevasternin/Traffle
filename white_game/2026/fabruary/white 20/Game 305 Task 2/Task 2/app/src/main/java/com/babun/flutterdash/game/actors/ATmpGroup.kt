package com.babun.flutterdash.game.actors

import com.babun.flutterdash.game.utils.advanced.AdvancedGroup
import com.babun.flutterdash.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}