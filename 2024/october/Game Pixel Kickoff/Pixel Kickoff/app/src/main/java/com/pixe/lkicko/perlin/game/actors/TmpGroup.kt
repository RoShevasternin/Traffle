package com.pixe.lkicko.perlin.game.actors

import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedGroup
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen

class TmpGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {

    }

}