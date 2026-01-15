package com.skyplane.puzzleflight.game.actors

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.skyplane.puzzleflight.game.actors.checkbox.ACheckBox
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedGroup
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedScreen

class AVolumeGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    private val volumes = List(6) { ACheckBox(screen, ACheckBox.Static.Type.ITEM) }

    override fun addActorsOnGroup() {
        addVolumes()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addVolumes() {
        var nx = 0f

        volumes.onEach {
            touchable = Touchable.disabled
            addActor(it)
            it.setBounds(nx, 0f, 35f, 48f)
            nx += 40f
        }
    }

    fun update(num: Int) {
        volumes.onEach { it.uncheck() }
        repeat(num) { volumes[it].check() }
    }

}