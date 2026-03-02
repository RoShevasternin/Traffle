package com.tictactoe.classic.game.actors

import com.tictactoe.classic.game.utils.advanced.AdvancedGroup
import com.tictactoe.classic.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}