package com.dasholy.olympusdash.game.screens

import com.dasholy.olympusdash.game.LibGDXGame
import com.dasholy.olympusdash.game.actors.AResultGroup
import com.dasholy.olympusdash.game.actors.ATimerGroup
import com.dasholy.olympusdash.game.actors.button.AButton
import com.dasholy.olympusdash.game.box2d.AbstractBody
import com.dasholy.olympusdash.game.box2d.AbstractJoint
import com.dasholy.olympusdash.game.box2d.BodyId
import com.dasholy.olympusdash.game.box2d.bodies.BAvia
import com.dasholy.olympusdash.game.box2d.bodies.BCoin
import com.dasholy.olympusdash.game.box2d.bodies.BEnemy
import com.dasholy.olympusdash.game.box2d.bodies.standart.BStatic
import com.dasholy.olympusdash.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.dasholy.olympusdash.game.utils.actor.animHide
import com.dasholy.olympusdash.game.utils.actor.animShow
import com.dasholy.olympusdash.game.utils.actor.setOnClickListener
import com.dasholy.olympusdash.game.utils.advanced.AdvancedMouseScreen
import com.dasholy.olympusdash.game.utils.advanced.AdvancedStage
import com.dasholy.olympusdash.game.utils.font.FontParameter
import com.dasholy.olympusdash.game.utils.region
import com.dasholy.olympusdash.game.utils.runGDX
import com.dasholy.olympusdash.game.utils.toB2
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

var GLOBAL_coinCount = 0

class GameScreen(override val game: LibGDXGame) : AdvancedMouseScreen(game) {

    private val assets = game.gameAssets

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(40)
    private val font          = fontGenerator_Akshar.generateFont(fontParameter)

    // Actor
    private val aTimer   = ATimerGroup(this)
    private val aCoinLbl = Label("0", Label.LabelStyle(font, Color.WHITE))
    private val aResult  = AResultGroup(this).apply { color.a = 0f }
    private val aHarts   = List(3) { Image(assets.heart) }

    // Body
    private val bStatic = BStatic(this)
    private val bAvia   = BAvia(this)

    // Joint
    private val jPrismatic = AbstractJoint<PrismaticJoint, PrismaticJointDef>(this)

    // Field
    private var hartCount = 0

    private val itemFlow = MutableSharedFlow<BCoin>(replay = 15)
    private val bombFlow = MutableSharedFlow<BEnemy>(replay = 5)


    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.gameAssets.mainB.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Static()
        createB_Avia()
        createJ_Prismatic()
        createB_Items()
        createB_Bomb()

        val coinp = Image(assets.pan_coin)
        val timep = Image(assets.pan_time)
        addActors(coinp, timep)
        coinp.setBounds(27f, 1315f - 60f, 220f, 55f)
        timep.setBounds(401f, 1315f - 60f, 220f, 55f)

        addBack()
        addCoinLbl()
        addTimer()
        addHarts()

        addAndFillActor(aResult)

        aTimer.startTimer {
            aTimer.isPause = true
            isWorldPause   = true

            aResult.update(false)
            aResult.animShow(TIME_ANIM_SCREEN_ALPHA)
            aResult.touchable = Touchable.enabled
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        val menu = AButton(this@GameScreen, AButton.Static.Type.MENU)
        addActor(menu)
        menu.setBounds(273f, 1291f-60, 102f, 102f)

        menu.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addTimer() {
        addActor(aTimer)
        aTimer.setBounds(462f, 1315f-55, 151f, 44f)
    }

    private fun AdvancedStage.addCoinLbl() {
        addActor(aCoinLbl)
        aCoinLbl.apply {
            setBounds(91f, 1315f-55, 151f, 44f)
            setAlignment(Align.center)
        }
    }

    private fun AdvancedStage.addHarts() {
        var nx = 183f
        aHarts.onEach { hart ->
            addActor(hart)
            hart.setBounds(nx, 1f, 74f, 74f)
            nx += 74f + 28f
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        bStatic.create(-500f, 0f, 454f, 454f)
    }

    private fun createB_Avia() {
        bAvia.create(199f, 0f, 250f, 250f)

        bAvia.beginContactBlockArray.add(AbstractBody.ContactBlock { b2 ->
            when (b2.id) {
                BodyId.Game.COIN -> {
                    b2 as BCoin
                    if (b2.atomicBoolean.getAndSet(false)) {
                        itemFlow.tryEmit(b2)
                        game.soundUtil.apply { play(bonus) }

                        GLOBAL_coinCount += (10..20).random()
                        aCoinLbl.setText(GLOBAL_coinCount)
                    }
                }
                BodyId.Game.ENEMY -> {
                    b2 as BEnemy
                    if (b2.atomicBoolean.getAndSet(false)) {
                        bombFlow.tryEmit(b2)
                        game.soundUtil.apply { play(boom) }

                        aHarts[hartCount].remove()
                        hartCount++
                        if (hartCount == 3) {
                            aTimer.isPause = true
                            isWorldPause   = true

                            aResult.update(false)
                            aResult.animShow(TIME_ANIM_SCREEN_ALPHA)
                            aResult.touchable = Touchable.enabled
                        }

                        bAvia.body?.applyLinearImpulse(Vector2(listOf(-20000, 20000).random().toFloat(), 0f), bAvia.body?.worldCenter, true)
                    }
                }
            }
        })
    }

    private fun createB_Items() {
        repeat(15) {
            BCoin(this).also { bItem ->
                bItem.renderBlockArray.add(AbstractBody.RenderBlock {
                    bItem.body?.let {
                        if (it.position.y <= 0f) {
                            if (bItem.atomicBoolean.getAndSet(false)) {
                                itemFlow.tryEmit(bItem)
                            }
                        }
                    }
                })

                bItem.bodyDef.gravityScale = 0f

                val size = (50..100).random().toFloat()
                bItem.create(-300f, 0f, size, size)

                itemFlow.tryEmit(bItem)
            }
        }

        coroutine?.launch {
            itemFlow.collect { bItem ->
                runGDX {
                    bItem.body?.apply {
                        bItem.setNoneId()

                        setLinearVelocity(0f, 0f)
                        gravityScale = 0f
                        isAwake = false

                        setTransform(Vector2(-300f, 0f).toB2, 0f)
                    }
                }
            }
        }
        coroutine?.launch {
            itemFlow.collect { bItem ->
                delay((500L..1000L).random())
                runGDX {
                    bItem.body?.apply {
                        setTransform((50..600).random().toFloat().toB2, 1405f.toB2, 0f)

                        gravityScale = 1f
                        isAwake      = true
                    }
                }
                delay(100)
                runGDX {
                    bItem.id = BodyId.Game.COIN
                    bItem.atomicBoolean.set(true)
                }
            }
        }
    }

    private fun createB_Bomb() {
        repeat(1) {
            BEnemy(this).also { bBomb ->
                bBomb.renderBlockArray.add(AbstractBody.RenderBlock {
                    bBomb.body?.let {
                        if (it.position.y <= 0f) {
                            if (bBomb.atomicBoolean.getAndSet(false)) {
                                bombFlow.tryEmit(bBomb)
                            }
                        }
                    }
                })

                bBomb.bodyDef.gravityScale = 0f
                val size = (100..250).random().toFloat()
                bBomb.create(-400f, 0f, size, size)

                bombFlow.tryEmit(bBomb)
            }
        }

        coroutine?.launch {
            bombFlow.collect { bBomb ->
                runGDX {
                    bBomb.body?.apply {
                        setLinearVelocity(0f, 0f)
                        gravityScale = 0f
                        isAwake = false

                        setTransform(Vector2(-400f, 0f).toB2, 0f)
                    }
                }
            }
        }
        coroutine?.launch {
            bombFlow.collect { bBomb ->
                delay((1000L..3000L).random())
                runGDX {
                    bBomb.body?.apply {
                        setTransform((100..500).random().toFloat().toB2, 1405f.toB2, 0f)

                        gravityScale = 1f
                        isAwake      = true
                    }
                }
                delay(100)
                bBomb.atomicBoolean.set(true)
            }
        }
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Prismatic() {
        jPrismatic.create(PrismaticJointDef().apply {
            bodyA = bStatic.body
            bodyB = bAvia.body

            lowerTranslation = 300f.toB2
            upperTranslation = (230+648f).toB2
            enableLimit      = true
        })
    }

}