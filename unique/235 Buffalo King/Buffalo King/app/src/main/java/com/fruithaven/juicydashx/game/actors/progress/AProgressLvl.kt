package com.fruithaven.juicydashx.game.actors.progress

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fruithaven.juicydashx.game.actors.shader.AMaskGroup
import com.fruithaven.juicydashx.game.utils.Acts
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedGroup
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedScreen
import com.fruithaven.juicydashx.game.utils.gdxGame

class AProgressLvl(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgProgress = Image(gdxGame.assetsAll.progress_lvl)
    private val mask        = AMaskGroup(screen, gdxGame.assetsAll.MASK_LVL_PROGRESS)

    override fun addActorsOnGroup() {
        addMask()

        imgProgress.x = -width
    }

    // Actors ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
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