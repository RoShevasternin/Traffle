package com.viade.bepuzzle.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.viade.bepuzzle.game.actors.ACircles
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.TIME_ANIM_SCREEN
import com.viade.bepuzzle.game.utils.actor.*
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.runGDX
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.*

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aCircles = ACircles(screen)

    private val imgLoading    = Image(screen.game.assetsLoader.loading)
    private val imgHokey      = Image(screen.game.assetsLoader.hokey)
    private val imgTennis     = Image(screen.game.assetsLoader.tennis)
    private val imgFootball   = Image(screen.game.assetsLoader.football)
    private val imgBasketball = Image(screen.game.assetsLoader.basketball)
    private val imgSport      = Image(screen.game.assetsLoader.sports)

    val items = listOf(imgLoading, imgHokey, imgTennis, imgFootball, imgBasketball, imgSport)

    val listStartPosItem = listOf(
        PosSize(0f, 615f, 1080f, 609f),
        PosSize(1080f, 800f, 669f, 1077f),
        PosSize(-455f, 1100f, 455f, 506f),
        PosSize(-522f, -600f, 522f, 594f),
        PosSize(1080f, -800f, 684f, 769f),
        PosSize(161f, 1101f, 778f, 333f),
    )

    var blockFinishAnim = {}

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(aCircles)
                addImgs()
            }

            animShowMain()
            blockFinishAnim()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgs() {
        addActors(items)
        var vPosSize: PosSize

        items.onEachIndexed { index, img ->
            img.color.a = 0f
            vPosSize = listStartPosItem[index]
            img.setBounds(vPosSize)
        }

        imgLoading.setOrigin(Align.center)

    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        withTimeoutOrNull(3000) {
            imgLoading.animShowSuspend(0.35f)
            animForeverImgLoading()

            withContext(Dispatchers.Default) {
                launch { imgHokey.animShowSuspend(0.2f) }
                launch { imgHokey.animMoveToSuspend(489f, 758f, 0.3f, Interpolation.sineOut) }
                launch { imgTennis.animShowSuspend(0.2f) }
                launch { imgTennis.animMoveToSuspend(53f, 1055f, 0.3f, Interpolation.sineOut) }
            }
            animForeverImgHokey()
            animForeverImgTennis()

            withContext(Dispatchers.Default) {
                launch { imgBasketball.animShowSuspend(0.2f) }
                launch { imgBasketball.animMoveToSuspend(385f, -116f, 0.3f, Interpolation.sineOut) }
                launch { imgFootball.animShowSuspend(0.2f) }
                launch { imgFootball.animMoveToSuspend(40f, 86f, 0.3f, Interpolation.sineOut) }
            }
            imgSport.animShowSuspend(0.35f)

            animForeverImgBasketball()
            animForeverImgFootball()
        }
    }

    // Anim ------------------------------------------------

    suspend fun animHideMain(block: Block = Block {  }) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    private fun animForeverImgLoading() {
        runGDX {
            imgLoading.addAction(Actions.forever(Actions.sequence(
                Actions.scaleTo(1.02f, 1.02f, 0.25f),
                Actions.scaleTo(1.0f, 1.0f, 0.25f),
            )))
        }
    }
    private fun animForeverImgHokey() {
        runGDX {
            imgHokey.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(20f, 20f, 0.5f),
                Actions.moveBy(-20f, -20f, 0.5f),
            )))
        }
    }
    private fun animForeverImgTennis() {
        runGDX {
            imgTennis.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(-20f, 20f, 0.5f),
                Actions.moveBy(20f, -20f, 0.5f),
            )))
        }
    }
    private fun animForeverImgBasketball() {
        runGDX {
            imgBasketball.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(20f, -20f, 0.5f),
                Actions.moveBy(-20f, 20f, 0.5f),
            )))
        }
    }
    private fun animForeverImgFootball() {
        runGDX {
            imgFootball.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(-20f, -20f, 0.5f),
                Actions.moveBy(20f, 20f, 0.5f),
            )))
        }
    }

}