package com.fruiterra.maniachello.game.actors

import com.fruiterra.maniachello.game.utils.advanced.AdvancedGroup
import com.fruiterra.maniachello.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}