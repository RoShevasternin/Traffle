package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.main.AMainTrickDescription
import com.colderonetrains.battlesskates.game.screens.TrickDescriptionScreen
import com.colderonetrains.battlesskates.game.utils.Acts
import com.colderonetrains.battlesskates.game.utils.Block
import com.colderonetrains.battlesskates.game.utils.actor.disable
import com.colderonetrains.battlesskates.game.utils.actor.setOnClickListener
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame

class ARandom_RollTrick(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val imgRoll = Image(gdxGame.assetsAll.ROLL_TRICK)
    private val imgCub  = Image(gdxGame.assetsAll.CUBEK)

    var blockClick = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgRoll)
        addImgIcon()

        setOnClickListener(gdxGame.soundUtil) {
            disable()
            startRandom()
        }
    }

    private fun addImgIcon() {
        addActor(imgCub)
        imgCub.setBounds(426f, 36f, 91f, 91f)
    }

    // Logic ----------------------------------------------------

    private fun startRandom() {
        imgCub.setOrigin(Align.center)
        imgCub.clearActions()
        imgCub.addAction(Actions.sequence(
            Acts.rotateBy(-360f * 5f, 1f, Interpolation.fastSlow),
            Acts.run { blockClick() }
        ))
    }

}