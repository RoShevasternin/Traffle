package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.actors.AButton
import com.pinlq.esst.bloo.game.box2d.BodyId
import com.pinlq.esst.bloo.game.box2d.WorldUtil
import com.pinlq.esst.bloo.game.box2d.bodies.BBorder
import com.pinlq.esst.bloo.game.box2d.bodies.BCube
import com.pinlq.esst.bloo.game.utils.TIME_ANIM
import com.pinlq.esst.bloo.game.utils.actor.animHide
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShow
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedBox2dScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import com.pinlq.esst.bloo.game.utils.region
import com.pinlq.esst.bloo.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt


class ShakeScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    companion object {
        var CURRENT_USER_INDEX = 0
    }

    private val imgPanel     = Image(game.all.shaker)
    private val imgTitle     = Image(game.all.shake_the_phone)
    private val imgPersonage = Image(game.splash.listPersonage[SelectPlayersScreen.listPlayerRegionIndex[CURRENT_USER_INDEX]])
    private val imgCubeList  = List(6) { Image(game.all.listCube.random()) }
    private val btnBack      = AButton(this, AButton.Static.Type.Back)

    // Body
    private val bBorder   = BBorder(this)
    private val bCubeList = List(6) { BCube(this) }

    // Field
    private var accelX = 0f
    private var accelY = 0f
    private var forceTmp      : Vector2 = Vector2(-accelX, accelY)
    private val shakeThreshold: Float   = 30.0f

    private var shakingTime = 0f
    private var isShaking   = false

    override fun show() {
          setBackBackground(game.splash.listBackground[MenuScreen.BACKGROUND_INDEX].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun render(delta: Float) {
        super.render(delta)

        if (isShaking() && shakingTime != -1f) {
            isShaking = true

            accelX = Gdx.input.accelerometerX
            accelY = Gdx.input.accelerometerY

            forceTmp.set(-accelX, -accelY).scl(30f)
            bCubeList.onEach {
                it.body?.applyForceToCenter(forceTmp, true)
            }
        }

        if (isShaking) {
            shakingTime += delta
            if (shakingTime >= 4) {
                isShaking   = false
                shakingTime = -1f

                bCubeList.onEach { bCube ->
                    bCube.id = BodyId.NONE
                    bCube.body?.setLinearVelocity(0f, 0f)
                    bCube.actor?.animHide(0.25f)
                }

                coroutine?.launch {
                    delay(400)
                    runGDX { game.soundUtil.apply { play(WIN, 0.7f) } }

                    imgCubeList.onEach { image ->
                        runGDX { image.animShow(0.35f) }
                        delay(150)
                    }
                    delay(600)
                    runGDX {
                        imgPanel.animHide(TIME_ANIM)
                        stageUI.root.animHideScreen(TIME_ANIM) {
                            game.navigationManager.navigate(CharacterScreen::class.java.name, ShakeScreen::class.java.name)
                        }
                    }
                }
            }
        }
    }

    override fun AdvancedStage.addActorsOnStageBox2d() {
        addImgPanel()

        createB_Border()

        imgPanel.animShow(TIME_ANIM) {
            createB_CubeList()
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgTitle()
                addImgPersonage()
                addImgCubeList()

                addActor(btnBack)
                btnBack.setBounds(298f,15f,484f,239f)
                btnBack.setOnClickListener {
                    stageUI.root.animHideScreen(TIME_ANIM) {
                        game.navigationManager.back()
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        imgPanel.color.a = 0f
        addActor(imgPanel)
        imgPanel.setBounds(70f,461f,940f,940f)
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(154f,224f,773f,188f)

        imgTitle.apply {
            setOrigin(Align.center)
            val scale = 0.2f
            addAction(
                Actions.forever(Actions.sequence(
                    Actions.scaleBy(-scale, -scale, 0.5f, Interpolation.smooth),
                    Actions.scaleBy(scale, scale, 0.5f, Interpolation.smooth),
                ))
            )
        }
    }

    private fun AdvancedStage.addImgPersonage() {
        addActor(imgPersonage)
        imgPersonage.setBounds(340f,1431f,400f,450f)

        imgPersonage.apply {
            setOrigin(Align.center)
            val scale = 0.2f
            addAction(
                Actions.forever(Actions.sequence(
                    Actions.scaleBy(-scale, -scale, 0.5f, Interpolation.smooth),
                    Actions.scaleBy(scale, scale, 0.5f, Interpolation.smooth),
                ))
            )
        }
    }

    private fun AdvancedStage.addImgCubeList() {
        var nx = 176f
        var ny = 966f
        imgCubeList.onEachIndexed { index, image ->
            image.color.a = 0f
            addActor(image)
            image.setBounds(nx, ny,207f,232f)
            nx += 52+207
            if (index.inc() % 3 == 0) {
                nx = 176f
                ny -= 73+232
            }
        }
    }

    // Create Body -----------------------------------------------------------------------------------

    private fun createB_Border() {
        bBorder.id = BodyId.BORDER
        bBorder.collisionList.add(BodyId.CUBE)
        bBorder.create(70f,461f,940f,940f)
    }

    private fun createB_CubeList() {
        fun randomX() = (123..760).random().toFloat()
        fun randomY() = (534..1122).random().toFloat()

        bCubeList.onEach { bCube ->
            bCube.actor?.color?.a = 0f

            bCube.id = BodyId.CUBE
            bCube.collisionList.addAll(arrayOf(BodyId.BORDER, BodyId.CUBE))
            bCube.create(randomX(), randomY(), 207f,232f)

            bCube.actor?.animShow(0.25f) { bCube.startShake() }
        }
    }

    // Logic -----------------------------------------------------------------------------------

    private fun isShaking(): Boolean {
        val accelX = Gdx.input.accelerometerX
        val accelY = Gdx.input.accelerometerY
        val accelZ = Gdx.input.accelerometerZ

        val totalAcceleration = sqrt((accelX * accelX + accelY * accelY + accelZ * accelZ).toDouble()).toFloat()
        return totalAcceleration > shakeThreshold
    }

}