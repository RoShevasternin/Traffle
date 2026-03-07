package com.fushflyacensee.adventcoral.game.actors

import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedGroup
import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}