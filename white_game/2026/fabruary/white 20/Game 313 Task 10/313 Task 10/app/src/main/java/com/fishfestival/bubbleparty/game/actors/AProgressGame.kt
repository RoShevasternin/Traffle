package com.fishfestival.bubbleparty.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fishfestival.bubbleparty.game.actors.mask.AOldMask
import com.fishfestival.bubbleparty.game.utils.WIDTH_UI
import com.fishfestival.bubbleparty.game.utils.actor.addAndFillActor
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedGroup
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.advanced.PARANAMA
import com.fishfestival.bubbleparty.game.utils.advanced.PIDAR
import com.fishfestival.bubbleparty.game.utils.gdxGame
import com.fishfestival.bubbleparty.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgressGame(override val screen: PARANAMA): PIDAR() {

    private val LENGTH = 798f

    private val imgProgressBack  = Image(gdxGame.assetsAll.BROGRESS_BACK)
    private val imgProgress      = Image(gdxGame.assetsAll.PROGRESS)
    private val mask             = AOldMask(screen, gdxGame.assetsAll.MASK, WIDTH_UI.toInt())
    private val imgStars         = Image(gdxGame.assetsAll.STARS)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)

    override fun addActorsOnGroup() {
        addAndFillActor(imgProgressBack)

        addActor(mask)
        mask.setBounds(7f,20f,798f,37f)
        mask.addAndFillActor(imgProgress)

        addActor(imgStars)
        imgStars.setBounds(157f,0f,666f,66f)

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    imgProgress.x = (percent * onePercentX) - LENGTH
                }
            }
        }
    }

}