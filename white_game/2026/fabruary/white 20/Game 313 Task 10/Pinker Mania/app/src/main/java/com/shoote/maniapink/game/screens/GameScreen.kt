package com.shoote.maniapink.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.shoote.maniapink.game.LibGDXGame
import com.shoote.maniapink.game.actors.APause
import com.shoote.maniapink.game.actors.AProgress
import com.shoote.maniapink.game.box2d.AbstractBody
import com.shoote.maniapink.game.box2d.BodyId.BALL
import com.shoote.maniapink.game.box2d.BodyId.BOMB
import com.shoote.maniapink.game.box2d.BodyId.BOMB_SENSOR
import com.shoote.maniapink.game.box2d.BodyId.BORDERS
import com.shoote.maniapink.game.box2d.BodyId.FINISH
import com.shoote.maniapink.game.box2d.WorldUtil
import com.shoote.maniapink.game.box2d.bodies.BBall
import com.shoote.maniapink.game.box2d.bodies.BHorizontal
import com.shoote.maniapink.game.box2d.bodies.standart.BStatic
import com.shoote.maniapink.game.box2d.bodiesGroup.BGBallsManager
import com.shoote.maniapink.game.box2d.bodiesGroup.BGBomb
import com.shoote.maniapink.game.box2d.bodiesGroup.BGBorders
import com.shoote.maniapink.game.box2d.bodiesGroup.BGGun
import com.shoote.maniapink.game.utils.*
import com.shoote.maniapink.game.utils.actor.animHide
import com.shoote.maniapink.game.utils.actor.animShow
import com.shoote.maniapink.game.utils.actor.setOnClickListener
import com.shoote.maniapink.game.utils.actor.setPosition
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen
import com.shoote.maniapink.game.utils.advanced.AdvancedStage
import com.shoote.maniapink.game.utils.font.FontParameter
import com.shoote.maniapink.util.log
import kotlinx.coroutines.launch

class GameScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter()
    private val font47        = fontGenerator_Itim.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(47))

    private val ls47 = LabelStyle(font47, GColor.brown_4C)

    private var nextBallType = BBall.Type.entries.random()

    // Actors
    private val imgPanel    = Image(game.all.panel)
    private val imgNextBall = Image(game.all.listBall[nextBallType.ordinal])
    private val imgTopPanel = Image(game.all.pause_star)
    private val lblStars    = Label(game.dataStore.star.toString(), ls47)
    private val progress    = AProgress(this)
    private val aPause      = APause(this)

    private var imgBomb: Image? = null

    // Body
    private val bStatic           = BStatic(this)
    private val bHorizontalFinish = BHorizontal(this)

    // BodyGroup
    private val bgBorders      = BGBorders(this)
    private val bgGun          = BGGun(this, bStatic)
    private val bgBallsManager = BGBallsManager(this, bgBorders.bTop)

    // Field
    private var counterPreparationGun = 0

    override fun show() {
        setBackBackground(game.all.listBackground[Global.indexBackground])

        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageBox2d() {
        createB_Static()
        createB_HorizontalFinish()

        createBG_Borders()
        createBG_BallsManager()
        addImgBottom()
        createBG_Gun()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addTopPanel()
        addLblStar()
        addProgress()
        addImgNextBall()
        addImgBomb()

        addAPause()
    }

    private val renderer = ShapeRenderer().apply {
        color = Color.WHITE
    }

    override fun render(delta: Float) {
        super.render(delta)
        if (game.dataStore.isLaser) drawTrajectory(renderer, bgGun.getStartPosition().cpy().scaledToUI, bgGun.getImpulse().scl(10f))
    }

    // Add Actors ------------------------------------------------------------------------------------------------------

    private fun AdvancedStage.addImgBottom() {
        addActor(imgPanel)
        imgPanel.setSize(1080f, 350f)
    }

    private fun AdvancedStage.addImgNextBall() {
        addActor(imgNextBall)
        imgNextBall.setBounds(898f, 182f, 98f, 98f)
    }

    private fun AdvancedStage.addTopPanel() {
        addActor(imgTopPanel)
        imgTopPanel.setBounds(50f, 1757f, 980f, 115f)

        val aPause = Actor()
        addActor(aPause)
        aPause.apply {
            setBounds(50f, 1757f, 113f, 113f)
            setOnClickListener(game.soundUtil) {
                stageBox2d.root.disable()
                stageUI.root.children.onEach { it.disable() }
                this@GameScreen.aPause.enable()
                this@GameScreen.aPause.animShow(TIME_ANIM)
                isPauseWorld = true
            }
        }
    }

    private fun AdvancedStage.addLblStar() {
        addActor(lblStars)
        lblStars.setBounds(905f,1783f,104f,56f)
        lblStars.setAlignment(Align.center)
    }

    private fun AdvancedStage.addProgress() {
        addActor(progress)
        progress.setBounds(135f,1656f,811f,77f)

        val imgStars = Image(game.all.stars)
        addActor(imgStars)
        imgStars.setBounds(373f, 1656f, 560f, 77f)

        coroutine?.launch {
            progress.progressPercentFlow.collect {
                if (it >= 100) {
                    complete()
                }
            }
        }
    }

    private fun AdvancedStage.addAPause() {
        addActor(aPause)
        aPause.apply {
            this.color.a = 0f
            this.disable()
        }
        aPause.setBounds(120f,518f,873f,863f)

        aPause.apply {
            blockPlay = {
                stageBox2d.root.enable()
                stageUI.root.children.onEach { it.enable() }
                aPause.disable()
                aPause.animHide(TIME_ANIM)
                isPauseWorld = false
            }
            blockRestart = {
                stageBox2d.root.animHide(TIME_ANIM)
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
            blockHome = {
                stageBox2d.root.animHide(TIME_ANIM)
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.clearBackStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addImgBomb() {
        if (game.dataStore.isBomb.not()) return

        imgBomb = Image(game.all.bomb)

        addActor(imgBomb)
        imgBomb!!.apply {
            setBounds(107f, 157f, 135f, 175f)
            setOrigin(Align.center)
            rotation = -18f

            setOnClickListener(game.soundUtil) {
                this.disable()
                this.animHide(TIME_ANIM)

                bgGun.preparationBomb(generateBGBomb())
            }
        }

    }

    // Create BodyGroup ------------------------------------------------------------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f, 0f, WIDTH_UI, HEIGHT_UI)
    }

    private fun createBG_BallsManager() {
        bgBallsManager.create(47f, 1472f, 1034f, 307f)

        bgBallsManager.blockDestroyBall = { updateProgress() }
    }

    private fun createBG_Gun() {
        bgGun.blockPreparation = {
            bgGun.preparationBall(generateBBall())

            nextBallType = BBall.Type.entries.random()
            imgNextBall.drawable = TextureRegionDrawable(game.all.listBall[nextBallType.ordinal])

            counterPreparationGun++
            if (counterPreparationGun >= 2) {
                counterPreparationGun = 0
                bgBallsManager.updateBalls()
            }
        }

        bgGun.create(435f, 124f, 267f, 349f)
    }

    // Create Body ------------------------------------------------------------------------------------------------------

    private fun createB_Static() {
        bStatic.create(0f, 0f, 100f, 100f)
    }

    private fun createB_HorizontalFinish() {
        bHorizontalFinish.apply {
            id = FINISH
            collisionList.add(BALL)

            fixtureDef.isSensor = true

            beginContactBlockArray.add(AbstractBody.ContactBlock { enemy, _ ->
                if (enemy is BBall) {
                    enemy.currentTimeFinish = System.currentTimeMillis()
                }
            })
            endContactBlockArray.add(AbstractBody.ContactBlock { enemy, _ ->
                if (enemy is BBall) {
                    if (currentTimeMinus(enemy.currentTimeFinish) >= 500) {
                        log("Finish")
                        lose()
                    }
                }
            })
        }
        bHorizontalFinish.create(0f, 196f, 1080f, 50f)
    }

    // Logic ------------------------------------------------------------------------------------------------------

    private fun updateProgress() {
        progress.progressPercentFlow.value += 1.5f
    }

    private fun generateBBall(): BBall {
        val bBall = BBall(this, nextBallType)
        bBall.apply {
            id = BALL
            collisionList.addAll(arrayOf(BORDERS, BALL, FINISH, BOMB, BOMB_SENSOR))

            isTarget = true

            bodyDef.gravityScale = 0f
        }
        bBall.create(-500f, 500f, 141f, 141f)

        bBall.blockDestroy = {
            updateProgress()
        }

        return bBall
    }

    private fun generateBGBomb(): BGBomb {
        val bgBomb = BGBomb(this)
        bgBomb.apply {
            bBomb.bodyDef.gravityScale = 0f
        }
        bgBomb.create(-500f, 500f, 141f, 141f)

        return bgBomb
    }

    private fun lose() {
        stageBox2d.root.animHide(TIME_ANIM)
        stageUI.root.animHide(TIME_ANIM) {
            game.navigationManager.navigate(LoseScreen::class.java.name)
        }
    }

    private fun complete() {
        stageBox2d.root.animHide(TIME_ANIM)
        stageUI.root.animHide(TIME_ANIM) {
            game.navigationManager.navigate(CompleteScreen::class.java.name)
        }
    }

    private fun getTrajectoryPoint(startingPosition: Vector2, startingVelocity: Vector2, n: Float): Vector2 {
        // velocity and gravity are given per second but we want time step values here
        val t = 1 / 60.0f // seconds per time step (at 60fps)
        val stepVelocity = startingVelocity.cpy().scl(t) // m/s
        val stepGravity = worldUtil.world.gravity.cpy().scl(t * t) // m/s/s

        // calculating the position
        return startingPosition.cpy().add(stepVelocity.scl(n)).add(stepGravity.scl(0.5f * (n * n + n)))
    }

    private fun drawTrajectory(
        shapeRenderer: ShapeRenderer,
        startingPosition: Vector2,
        startingVelocity: Vector2
    ) {
        // Установка кольору траєкторії (жовтий)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.projectionMatrix = stageUI.camera.combined

        // Малюємо траєкторію протягом 3.5 секунд (180 кроків при 60 FPS)
        for (i in 0 until 210 step 5) {

            val trajectoryPosition = getTrajectoryPoint(startingPosition, startingVelocity, i.toFloat())

            // Додаємо лінію до точки траєкторії
            shapeRenderer.circle(trajectoryPosition.x, trajectoryPosition.y, 3f)
        }

        shapeRenderer.end()
    }

}