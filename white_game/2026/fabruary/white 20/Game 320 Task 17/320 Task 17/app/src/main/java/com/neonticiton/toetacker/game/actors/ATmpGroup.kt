package com.neonticiton.toetacker.game.actors

import com.neonticiton.toetacker.game.utils.advanced.AdvancedGroup
import com.neonticiton.toetacker.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}