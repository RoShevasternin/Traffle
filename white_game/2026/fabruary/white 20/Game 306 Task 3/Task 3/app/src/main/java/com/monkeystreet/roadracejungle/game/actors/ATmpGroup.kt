package com.monkeystreet.roadracejungle.game.actors

import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedGroup
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}