/*
 * Refactored Application Module
 * Build: EAC85B5C
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.progress

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.moonarcade.starlabyrinth.game.actors.shader.MaskedContainer
import com.moonarcade.starlabyrinth.game.utils.Acts
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class AProgressLvl(override val screen: BaseScreen): BaseGroup() {

    private val imgProgress = Image(gdxGame.assetsAll.progress_lvl)
    private val mask = MaskedContainer(screen, gdxGame.assetsAll.MASK_LVL_PROGRESS)

    override fun addActorsOnGroup() {
        addMask()

        imgProgress.x = -width
    }

    // Actors ---------------------------------------------------

    private fun BaseGroup.addMask() {
        addAndFillActor(mask)
        mask.addAndFillActor(imgProgress)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun animNewLevel(blockEnd: () -> Unit) {
        imgProgress.addAction(Acts.sequence(
            Acts.moveTo(0f, 0f, 1.5f, Interpolation.slowFast),
            Acts.moveTo(-width, 0f, 0.2f),
            Acts.run { blockEnd() }
        ))
    }

}