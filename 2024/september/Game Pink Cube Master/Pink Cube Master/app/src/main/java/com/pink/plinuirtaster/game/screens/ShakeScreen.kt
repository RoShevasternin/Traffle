package com.pink.plinuirtaster.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.actors.AButton
import com.pink.plinuirtaster.game.box2d.BodyId
import com.pink.plinuirtaster.game.box2d.WorldUtil
import com.pink.plinuirtaster.game.box2d.bodies.BBorder
import com.pink.plinuirtaster.game.box2d.bodies.BCube
import com.pink.plinuirtaster.game.screens.SettingsScreen.Companion.IS_THEME_LIGHT
import com.pink.plinuirtaster.game.utils.GColor
import com.pink.plinuirtaster.game.utils.TIME_ANIM
import com.pink.plinuirtaster.game.utils.actor.animHide
import com.pink.plinuirtaster.game.utils.actor.animHideScreen
import com.pink.plinuirtaster.game.utils.actor.animShow
import com.pink.plinuirtaster.game.utils.actor.animShowScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedBox2dScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedStage
import com.pink.plinuirtaster.game.utils.font.FontParameter
import com.pink.plinuirtaster.game.utils.region
import com.pink.plinuirtaster.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt


class ShakeScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setBorder(5f, GColor.stroke)
    private val font85        = fontGenerator_RubikWetPaint.generateFont(fontParameter.setSize(85))

    private val ls85 = Label.LabelStyle(font85, GColor.pink)

    private val btnBeck  = AButton(this, AButton.Static.Type.Back)
    private val imgPanel = Image(game.all.PANEL_9)
    private val imgTurn  = Image(game.all.TURN)
    private val imgShake = Image(game.all.SHAKE)
    private val lblName  = Label(NamesScreen.UserData_List[ScoreScreen.CurrentPlayerIndex].uName, ls85)
    private val imgCubeList = List(6) { Image(game.all.cubesList.random()) }

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
        setBackBackground(if (IS_THEME_LIGHT) game.splash.BACKGROUND.region else game.all.Dark.region)

        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun render(delta: Float) {
        super.render(delta)

        if (isShaking() && shakingTime != -1f) {
            isShaking = true
            if (imgShake.color.a == 1f) imgShake.addAction(Actions.fadeOut(1f))

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
                    runGDX { game.soundUtil.apply { play(RESULT_SHAKE, 0.6f) } }

                    imgCubeList.onEach { image ->
                        runGDX { image.animShow(0.35f) }
                        delay(150)
                    }
                    delay(600)
                    runGDX {
                        imgPanel.animHide(TIME_ANIM)
                        stageUI.root.animHideScreen(TIME_ANIM) {
                            game.navigationManager.navigate(ChooseScreen::class.java.name, ShakeScreen::class.java.name)
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
                addBack()
                addImgTurn()
                addImgShake()
                addImgCubeList()
            }
        }
    }

    private fun AdvancedStage.addBack() {
        addActor(btnBeck)
        btnBeck.apply {
            setBounds(50f,1795f,140f,76f)
            setOnClickListener {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        imgPanel.color.a = 0f
        addActor(imgPanel)
        imgPanel.setBounds(50f,529f,980f,980f)
    }

    private fun AdvancedStage.addImgTurn() {
        addActor(imgTurn)
        imgTurn.setBounds(434f,1747f,210f,96f)

        addActor(lblName)
        lblName.apply {
            setAlignment(Align.center)
            setBounds(234f,1604f,612f,135f)
        }
    }

    private fun AdvancedStage.addImgShake() {
        addActor(imgShake)
        imgShake.setBounds(233f,245f,623f,216f)

        imgShake.apply {
            setOrigin(Align.center)
            val scale = 0.2f
            addAction(
                Actions.forever(Actions.sequence(
                    Actions.scaleBy(-scale, -scale, 0.5f, Interpolation.slowFast),
                    Actions.scaleBy(scale, scale, 0.5f, Interpolation.fastSlow),
                ))
            )
        }
    }

    private fun AdvancedStage.addImgCubeList() {
        var nx = 165f
        var ny = 1069f
        imgCubeList.onEachIndexed { index, image ->
            image.color.a = 0f
            addActor(image)
            image.setBounds(nx, ny,183f,204f)
            nx += 100+183
            if (index.inc() % 3 == 0) {
                nx = 165f
                ny -= 100+204
            }
        }
    }

    // Create Body -----------------------------------------------------------------------------------

    private fun createB_Border() {
        bBorder.id = BodyId.BORDER
        bBorder.collisionList.add(BodyId.CUBE)
        bBorder.create(50f,529f,980f,980f)
    }

    private fun createB_CubeList() {
        fun randomX() = (63..830).random().toFloat()
        fun randomY() = (559..1284).random().toFloat()

        bCubeList.onEach { bCube ->
            bCube.actor?.color?.a = 0f

            bCube.id = BodyId.CUBE
            bCube.collisionList.addAll(arrayOf(BodyId.BORDER, BodyId.CUBE))
            bCube.create(randomX(), randomY(), 183f,204f)

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