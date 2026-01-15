package com.bounceroval.mazedackq.game.screens

import com.badlogic.gdx.math.Interpolation
import com.bounceroval.mazedackq.game.LibGDXGame
import com.bounceroval.mazedackq.game.utils.actor.setOnClickListener
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedScreen
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedStage
import com.bounceroval.mazedackq.game.utils.region
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bounceroval.mazedackq.game.actors.AButton
import com.bounceroval.mazedackq.game.utils.WIDTH_UI
import com.bounceroval.mazedackq.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PrePlayScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var AVIA_INDEX = 0
    }

    private val fList    = List(3) { Image(game.all.avias[it]) }
    private val random   = Image(game.all.random)
    private val info     = Image(game.all.info)
    private val back     = AButton(this, AButton.Static.Type.Back)

    private val avia_Y_List = listOf(1379f, 936f, 493f)


    override fun show() {
        setBackBackground(game.splash.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBack()
                addRandom()
                addInfo()
                addAvias()

                animShowScreen()
            }
            repeat(fList.count()) {
                launch { fList[it].animAvia(avia_Y_List[it]) }
                delay(125)
            }
        }
    }

    private fun AdvancedStage.addBack() {
        addActor(back)
        back.apply {
            setBounds(313f, -201f, 436f, 201f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addRandom() {
        addActor(random)
        random.apply {
            setBounds(-353f, 862f, 353f, 411f)
            setOnClickListener(game.soundUtil) {
                AVIA_INDEX = (0..2).random()
                animHideScreen {
                    game.navigationManager.navigate(GameScreen::class.java.name, PrePlayScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addInfo() {
        addActor(info)
        info.apply {
            setBounds(-400f, 0f, 392f, 379f)
            setOnClickListener(game.soundUtil) {
                animHideScreen {
                    game.navigationManager.navigate(InfoScreen::class.java.name, PrePlayScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addAvias() {
        fList.onEachIndexed { index, image ->
            addActor(image)
            image.setBounds(WIDTH_UI, avia_Y_List[index], 436f, 369f)
            image.setOnClickListener(game.soundUtil) {
                AVIA_INDEX = index
                animHideScreen {
                    game.navigationManager.navigate(GameScreen::class.java.name, PrePlayScreen::class.java.name)
                }
            }
        }
    }

    private fun animShowScreen() {
        val time = 0.5f
        back.addAction(Actions.moveTo(313f, 82f, time, Interpolation.swingOut))
        random.addAction(Actions.moveTo(68f, 1176f, time, Interpolation.swingOut))
        info.addAction(Actions.moveTo(48f, 682f, time, Interpolation.swingOut))
    }

    private suspend fun Image.animAvia(yA: Float) = suspendCoroutine { cont ->
        addAction(Actions.sequence(
            Actions.moveTo(531f, yA, 0.5f, Interpolation.swingOut),
            Actions.run { cont.resume(Unit) }
        ))
    }

    private fun animHideScreen(block: () -> Unit) {
        val time = 0.5f

        fList.onEachIndexed { index, image -> image.addAction(Actions.moveTo(WIDTH_UI, avia_Y_List[index], time, Interpolation.swingIn)) }
        random.addAction(Actions.moveTo(-353f, 862f, time, Interpolation.swingIn))
        info.addAction(Actions.moveTo(-400f, 0f, time, Interpolation.swingIn))

        back.addAction(Actions.sequence(
            Actions.moveTo(313f, -201f, time, Interpolation.swingIn),
            Actions.run { block() }
        ))
    }

}