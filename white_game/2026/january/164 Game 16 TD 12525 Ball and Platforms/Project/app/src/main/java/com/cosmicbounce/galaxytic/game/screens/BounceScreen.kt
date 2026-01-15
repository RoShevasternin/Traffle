package com.cosmicbounce.galaxytic.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cosmicbounce.galaxytic.game.LibGDXGame
import com.cosmicbounce.galaxytic.game.actors.AButton
import com.cosmicbounce.galaxytic.game.box2d.AbstractBody
import com.cosmicbounce.galaxytic.game.box2d.BodyId
import com.cosmicbounce.galaxytic.game.box2d.WorldUtil
import com.cosmicbounce.galaxytic.game.box2d.bodies.BBall
import com.cosmicbounce.galaxytic.game.box2d.bodies.BBot
import com.cosmicbounce.galaxytic.game.box2d.bodies.BItem
import com.cosmicbounce.galaxytic.game.box2d.bodies.BKorobka
import com.cosmicbounce.galaxytic.game.box2d.bodies.BPlatform
import com.cosmicbounce.galaxytic.game.utils.HEIGHT_BOX2D
import com.cosmicbounce.galaxytic.game.utils.HEIGHT_UI
import com.cosmicbounce.galaxytic.game.utils.TIME_ANIM
import com.cosmicbounce.galaxytic.game.utils.WIDTH_UI
import com.cosmicbounce.galaxytic.game.utils.actor.animHide
import com.cosmicbounce.galaxytic.game.utils.actor.animShow
import com.cosmicbounce.galaxytic.game.utils.actor.setOnClickListener
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedBox2dScreen
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedStage
import com.cosmicbounce.galaxytic.game.utils.font.FontParameter
import com.cosmicbounce.galaxytic.game.utils.region
import com.cosmicbounce.galaxytic.game.utils.runGDX
import com.cosmicbounce.galaxytic.game.utils.toB2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class BounceScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font23        = fontGenerator_Gugi.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(23))

    private val imgA = Image(game.all.a)
    private val imgB = Image(game.all.b)
    private val imgC = Image(game.all.c)

    private var r1 = 0
    private var r2 = 0
    private var r3 = 0

    private val lblA = Label("$r1", Label.LabelStyle(font23, Color.WHITE))
    private val lblB = Label("$r2", Label.LabelStyle(font23, Color.WHITE))
    private val lblC = Label("$r3", Label.LabelStyle(font23, Color.WHITE))

    private val btnBack  = AButton(this, AButton.Static.Type.Back)

    private val imgLeft  = Image(game.all.bumleft)
    private val imgRight = Image(game.all.bumright)
    private val imgCentr = Image(game.all.cntr)

    // Body
    private val bBall      = BBall(this)
    private val bKorobka   = BKorobka(this)
    private val bBot       = BBot(this)

    private val bPlatformList = List(6) { BPlatform(this) }

    // Field
    private val platformFlow = MutableSharedFlow<BPlatform>(6)


    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.backgrounds.random().region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        goAsteroids()

        addImgABC()
        addLblABC()
        addBack()
        addImgControl()

        createB_Korobka()
        createB_Bot()
        createB_Ball()

        createB_Platform()
    }

    private fun AdvancedStage.addImgABC() {
        addActors(imgA, imgB, imgC)
        imgA.setBounds(44f, 968f, 173f, 135f)
        imgB.setBounds(231f, 968f, 173f, 135f)
        imgC.setBounds(419f, 968f, 173f, 135f)
    }

    private fun AdvancedStage.addLblABC() {
        addActors(lblA, lblB, lblC)
        lblA.setBounds(67f, 1060f, 125f, 29f)
        lblB.setBounds(250f, 1060f, 125f, 29f)
        lblC.setBounds(443f, 1060f, 125f, 29f)
        lblA.setAlignment(Align.center)
        lblB.setAlignment(Align.center)
        lblC.setAlignment(Align.center)
    }

    private fun AdvancedStage.addBack() {
        addActor(btnBack)
        btnBack.apply {
            setBounds(258f, 14f, 123f, 74f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgControl() {
        addActors(imgLeft, imgRight, imgCentr)
        imgLeft.setBounds(21f, 51f, 173f, 173f)
        imgRight.setBounds(444f, 51f, 173f, 173f)
        imgCentr.setBounds(193f, 113f, 251f, 167f)

        imgLeft.setOnClickListener {
            game.soundUtil.apply { play(jump, 0.1f) }

            bBall.body?.apply {
                setLinearVelocity(0f, 0f)
                applyLinearImpulse(Vector2(-0.5f, 1f), worldCenter, true)
            }
        }
        imgRight.setOnClickListener {
            game.soundUtil.apply { play(jump, 0.1f) }
            bBall.body?.apply {
                setLinearVelocity(0f, 0f)
                applyLinearImpulse(Vector2(0.5f, 1f), worldCenter, true)
            }
        }
    }


    // Anim ------------------------------------------------------------------------

    private fun animHideScreen(block: () -> Unit) {
        stageUI.root.animHide(TIME_ANIM) { block() }
    }

    // Create Body ------------------------------------------------------------------------

    private fun createB_Korobka() {
        bKorobka.apply {
            id = BodyId.STENA
            collisionList.add(BodyId.BALL)
        }
        bKorobka.create(-54f, -46f, 745f, 1224f)
    }

    private fun createB_Bot() {
        bBot.apply {
            id = BodyId.STENA
            collisionList.add(BodyId.BALL)
        }
        bBot.create(3f, 22f, 625f, 193f)
    }

    private fun createB_Ball() {
        bBall.apply {
            id = BodyId.BALL
            collisionList.addAll(arrayOf(BodyId.STENA, BodyId.ITEM, BodyId.PLANFORMA))
        }
        bBall.create(258f, 234f, 121f, 121f)

        bBall.beginContactBlockArray.add(AbstractBody.ContactBlock { enemy ->
            when (enemy.id) {

                BodyId.ITEM -> {

                    enemy as BItem

                    if (enemy.isOnStart.getAndSet(false)) {
                        game.soundUtil.apply { play(win_in_game) }

                        enemy.platfaramar?.hideItem()

                        game.soundUtil.apply {
                            game.soundUtil.apply { play(click, 0.35f) }
                        }

                        r1 += (5..50).random()
                        r2 += (5..50).random()
                        r3 += (5..50).random()

                        lblA.setText(r1)
                        lblB.setText(r2)
                        lblC.setText(r3)
                    }
                }
            }
        })
    }

    private fun createB_Platform() {
        bPlatformList.onEach { platform ->
            platform.id = BodyId.PLANFORMA
            platform.collisionList.add(BodyId.BALL)
            platform.create(0f, HEIGHT_UI + 80f, 178f, 61f)

            platform.createItem()


            var timer = 0f
            platform.renderBlockArray.add(AbstractBody.RenderBlock {
                timer += it
                if (timer >= 1) {
                    timer = 0f
                    if ((platform.body?.position?.y ?: 0f) < (-40f).toB2 || (platform.body?.position?.y ?: 0f) > HEIGHT_BOX2D + 200f.toB2) {
                        if (platform.isOnStart.getAndSet(false)) {
                            platform.hideItem()
                            platformFlow.tryEmit(platform)
                        }
                    }
                }
            })

            platformFlow.tryEmit(platform)
        }

        val startPos = Vector2()

        coroutine?.launch {
            platformFlow.collect { platform ->
                platform.body?.apply {
                    setLinearVelocity(0f, 0f)
                    isAwake      = false
                    gravityScale = 0f

                    runGDX {
                        val nx = (0..450).random().toFloat()
                        setTransform(startPos.set(nx, HEIGHT_UI + 80f).toB2, 0f)
                        platform.isOnStart.set(true)
                    }
                }
            }
        }
        coroutine?.launch {
            platformFlow.collect { platform ->
                delay((2000..4000L).random())

                runGDX {
                    if (Random.nextBoolean()) platform.showItem() else platform.hideItem()

                    platform.body?.apply {
                        setLinearVelocity(0f, -1f)
                        gravityScale = 0f
                        isAwake = true
                    }
                }
            }
        }
    }

    // Asteroids ------------------------------------------------------------------------

    private fun goAsteroids() {
        val aLeft  = Image(game.all.left)
        val aRight = Image(game.all.right)
        stageUI.addActors(aLeft, aRight)
        aLeft.setBounds(300f, HEIGHT_UI -300, 287f, 283f)
        aRight.setBounds(WIDTH_UI, HEIGHT_UI, 287f, 283f)


        val startYInterval = (538..849)
        val endYInterval   = (0..487)
        val timeInterval   = (70..300)

        fun startLeft() {
            aLeft.setPosition(-300f, startYInterval.random().toFloat())

            aLeft.apply {
                clearActions()
                addAction(
                    Actions.sequence(
                    Actions.moveTo(WIDTH_UI, endYInterval.random().toFloat(), timeInterval.random() / 100f),
                    Actions.run { startLeft() }
                ))
            }
        }

        fun startRight() {
            aRight.setPosition(WIDTH_UI, startYInterval.random().toFloat())

            aRight.apply {
                clearActions()
                addAction(
                    Actions.sequence(
                    Actions.moveTo(-300f, endYInterval.random().toFloat(), timeInterval.random() / 100f),
                    Actions.run { startRight() }
                ))
            }
        }

        startLeft()
        startRight()

    }


}