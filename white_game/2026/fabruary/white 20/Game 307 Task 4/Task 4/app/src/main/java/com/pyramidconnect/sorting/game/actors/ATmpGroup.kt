package com.pyramidconnect.sorting.game.actors

import com.pyramidconnect.sorting.game.utils.advanced.AdvancedGroup
import com.pyramidconnect.sorting.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}