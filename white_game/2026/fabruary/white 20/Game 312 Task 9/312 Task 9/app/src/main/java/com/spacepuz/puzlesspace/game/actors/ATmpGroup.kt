package com.spacepuz.puzlesspace.game.actors

import com.spacepuz.puzlesspace.game.utils.advanced.AdvancedGroup
import com.spacepuz.puzlesspace.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}