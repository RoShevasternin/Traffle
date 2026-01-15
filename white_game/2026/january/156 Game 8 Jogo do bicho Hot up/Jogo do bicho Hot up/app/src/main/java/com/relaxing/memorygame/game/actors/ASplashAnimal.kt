package com.relaxing.memorygame.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.relaxing.memorygame.game.actors.masks.Mask
import com.relaxing.memorygame.game.utils.Block
import com.relaxing.memorygame.game.utils.actor.animHide
import com.relaxing.memorygame.game.utils.actor.animShow
import com.relaxing.memorygame.game.utils.advanced.AdvancedGroup
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen
import com.relaxing.memorygame.game.utils.runGDX
import kotlinx.coroutines.flow.collect
import java.util.Random

class ASplashAnimal(override val screen: AdvancedScreen, val isStartAnimal: Boolean = false): AdvancedGroup() {

    private val rightImg = Image(screen.game.splashAssets.BABA_RIGHT)
    private val leftImg  = Image(screen.game.splashAssets.BABA_LEFT)
    private val bullImg       = Image(screen.game.splashAssets.BULL)
    private val peacockImg    = Image(screen.game.splashAssets.PEACOCK)
    private val crocodileImg  = Image(screen.game.splashAssets.CROCODILE)
    private val grassImg      = Image(screen.game.splashAssets.GRASS)

    private val beetween = TmpGroup(screen)

    private val tmpBabaPos = Vector2(0f, 0f)

    override fun addActorsOnGroup() {
        addBab()
        addAnimal()
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private val actMove get() = Actions.moveTo(tmpBabaPos.x, tmpBabaPos.y, (100..200).random() / 100f, Interpolation.sineIn)

    private fun AdvancedGroup.addBab() {
        addActors(rightImg, leftImg)
        rightImg.apply {
            setBounds(76f, 1578f, 190f, 190f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.sequence(
                Actions.parallel(
                    Actions.scaleBy(-0.2f, -0.2f, 0.5f),
                    Actions.rotateBy(10f, 0.5f, Interpolation.fade),
                ),
                Actions.parallel(
                    Actions.scaleBy(0.2f, 0.2f, 0.5f),
                    Actions.rotateBy(-10f, 0.5f, Interpolation.fade),
                ),
                Actions.run {
                    getBabaPosition()
                    addAction(actMove)
                }
            )))
        }
        leftImg.apply {
            setBounds(868f, 1252f, 130f, 130f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.sequence(
                Actions.parallel(
                    Actions.scaleBy(0.2f, 0.2f, 0.5f),
                    Actions.rotateBy(10f, 0.5f, Interpolation.fade),
                ),
                Actions.parallel(
                    Actions.scaleBy(-0.2f, -0.2f, 0.5f),
                    Actions.rotateBy(-10f, 0.5f, Interpolation.fade),
                ),
                Actions.run {
                    getBabaPosition()
                    addAction(actMove)
                }
            )))
        }
    }

    private fun AdvancedGroup.addAnimal() {
        addActors(bullImg, beetween, peacockImg, crocodileImg, grassImg)

        beetween.setSize(width, height)

        if (isStartAnimal) {
            bullImg.setBounds(-56f, 193f, 745f, 747f)
            peacockImg.setBounds(493f, -55f, 724f, 702f)
            crocodileImg.setBounds(-12f, -160f, 579f, 503f)
            grassImg.setBounds(-68f, -118f, 1272f, 419f)
        } else {
            bullImg.setBounds(-745f, 193f, 745f, 747f)
            peacockImg.setBounds(1080f, 100f, 724f, 702f)
            crocodileImg.setBounds(-580f, -200f, 579f, 503f)
            grassImg.setBounds(-68f, -419f, 1272f, 419f)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getBabaPosition() = tmpBabaPos.also { v ->
        v.x = (190..890).random().toFloat() + dop()
        v.y = (1111..1730).random().toFloat() + dop()
    }

    private fun dop() = (20..150).random().toFloat() * if (Random().nextBoolean()) 1 else -1

    private fun startBab() {
        rightImg.apply {
            clearActions()
            addAction(Actions.parallel(
                Actions.scaleTo(1f, 1f, 0.2f),
                Actions.rotateTo(0f, 0.0f),
                Actions.moveTo(76f, 1578f, 0.2f)
            ))
        }
        leftImg.apply {
            clearActions()
            addAction(Actions.parallel(
                Actions.scaleTo(1f, 1f, 0.2f),
                Actions.rotateTo(0f, 0.0f),
                Actions.moveTo(868f, 1252f, 0.2f)
            ))
        }
    }

    fun startAnimal() {
        bullImg.clearActions()
        peacockImg.clearActions()
        crocodileImg.clearActions()
        grassImg.clearActions()

        bullImg.addAction(Actions.moveTo(-56f, 193f, 0.2f, Interpolation.sineOut))
        peacockImg.addAction(Actions.moveTo(493f, -55f, 0.2f, Interpolation.sineOut))
        crocodileImg.addAction(Actions.moveTo(-12f, -160f, 0.2f, Interpolation.sineOut))
        grassImg.addAction(Actions.moveTo(-68f, -118f, 0.2f, Interpolation.sineOut))
    }

    fun animShowAnimal() {
        bullImg.addAction(Actions.moveTo(-56f, 193f, 0.65f, Interpolation.sineOut))
        peacockImg.addAction(Actions.moveTo(493f, -55f, 0.7f, Interpolation.sineOut))
        crocodileImg.addAction(Actions.moveTo(-12f, -160f, 0.8f, Interpolation.sineOut))
        grassImg.addAction(Actions.moveTo(-68f, -118f, 0.55f, Interpolation.sineOut))
    }

    fun animAnimateAnimal() {
        bullImg.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(-30f, -10f, 0.7f, Interpolation.sine),
            Actions.moveBy(30f, 10f, 0.7f, Interpolation.sine),
        )))
        peacockImg.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(15f, -15f, 0.8f, Interpolation.sine),
            Actions.moveBy(-15f, 15f, 0.8f, Interpolation.sine),
        )))
        crocodileImg.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(-25f, -20f, 0.9f, Interpolation.sine),
            Actions.moveBy(25f, 20f, 0.9f, Interpolation.sine),
        )))
    }

    fun animToStart(block: Block) {
        startBab()
        startAnimal()

        addAction(Actions.sequence(
            Actions.delay(0.3f),
            Actions.run { block.invoke() }
        ))
    }

    fun animHideBullAndBab() {
        startBab()
        rightImg.animHide(0.3f)
        leftImg.animHide(0.3f)
        bullImg.clearActions()
        bullImg.addAction(Actions.moveTo(-56f, 193f, 0.2f, Interpolation.sineOut))
        bullImg.animHide(0.3f)
    }

    fun animShowBullAndBab() {
        rightImg.animShow(0.3f)
        leftImg.animShow(0.3f)
        bullImg.animShow(0.3f)
    }

    fun addBeetween(block: AdvancedGroup.() -> Unit) {
        beetween.block()
    }

}