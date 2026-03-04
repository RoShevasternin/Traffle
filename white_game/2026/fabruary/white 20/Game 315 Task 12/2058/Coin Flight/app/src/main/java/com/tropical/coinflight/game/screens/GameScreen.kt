package com.tropical.coinflight.game.screens

import com.tropical.coinflight.game.LibGDXGame
import com.tropical.coinflight.game.actors.AResultGroup
import com.tropical.coinflight.game.actors.ATimerGroup
import com.tropical.coinflight.game.box2d.AbstractBody
import com.tropical.coinflight.game.box2d.AbstractJoint
import com.tropical.coinflight.game.box2d.BodyId
import com.tropical.coinflight.game.box2d.bodies.BAvia
import com.tropical.coinflight.game.box2d.bodies.BCoin
import com.tropical.coinflight.game.box2d.bodies.BEnemy
import com.tropical.coinflight.game.box2d.bodies.standart.BStatic
import com.tropical.coinflight.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.tropical.coinflight.game.utils.actor.animHide
import com.tropical.coinflight.game.utils.actor.animShow
import com.tropical.coinflight.game.utils.advanced.AdvancedMouseScreen
import com.tropical.coinflight.game.utils.advanced.AdvancedStage
import com.tropical.coinflight.game.utils.font.FontParameter
import com.tropical.coinflight.game.utils.region
import com.tropical.coinflight.game.utils.runGDX
import com.tropical.coinflight.game.utils.toB2
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.tropical.coinflight.game.utils.WIDTH_BOX2D
import com.tropical.coinflight.game.utils.WIDTH_UI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

var CANONcoinCount = 0

class GameScreen(override val game: LibGDXGame) : AdvancedMouseScreen(game) {

    private val assets = game.allAssets

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(25)
    private val font          = fontGenerator_Averta.generateFont(fontParameter)

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
        setBackBackground(game.allAssets.background.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Static()
        createB_Avia()
        createJ_Prismatic()
        createB_Items()
        createB_Bomb()

        val panel = Image(assets.panel)
        addActors(panel)
        panel.setBounds(481f, 546f, 339f, 41f)

        //addBack()
        addCoinLbl()
        addTimer()
        addHarts()

        addAndFillActor(aResult)

        aTimer.startTimer {
            aTimer.isPause = true
            isWorldPause   = true

            aResult.update(true, CANONcoinCount)
            aResult.animShow(TIME_ANIM_SCREEN_ALPHA)
            aResult.touchable = Touchable.enabled
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

//    private fun AdvancedStage.addBack() {
//        val menu = Actor()
//        addActor(menu)
//        menu.setBounds(273f, 1291f, 102f, 102f)
//
//        menu.setOnClickListener(game.soundUtil) {
//            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
//        }
//    }

    private fun AdvancedStage.addTimer() {
        addActor(aTimer)
        aTimer.setBounds(705f, 547f, 111f, 32f)
    }

    private fun AdvancedStage.addCoinLbl() {
        addActor(aCoinLbl)
        aCoinLbl.apply {
            setBounds(528f, 547f, 111f, 32f)
            setAlignment(Align.center)
        }
    }

    private fun AdvancedStage.addHarts() {
        var nx = 8f
        aHarts.onEach { hart ->
            addActor(hart)
            hart.setBounds(nx, 0.5f, 55f, 55f)
            nx += 6f + 55f
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        bStatic.create(56f, -101f, 202f, 202f)
    }

    private fun createB_Avia() {
        bAvia.create(40f, 182f, 235f, 235f)

        bAvia.beginContactBlockArray.add(AbstractBody.ContactBlock { b2 ->
            when (b2.id) {
                BodyId.Game.COIN -> {
                    b2 as BCoin
                    if (b2.atomicBoolean.getAndSet(false)) {
                        itemFlow.tryEmit(b2)
                        game.soundUtil.apply { play(bonus) }

                        CANONcoinCount += (25..75).random()
                        aCoinLbl.setText(CANONcoinCount)
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

                            aResult.update(false, CANONcoinCount)
                            aResult.animShow(TIME_ANIM_SCREEN_ALPHA)
                            aResult.touchable = Touchable.enabled
                        }

                        bAvia.body?.applyLinearImpulse(Vector2(0f, listOf(-20000, 20000).random().toFloat()), bAvia.body?.worldCenter, true)
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
                        if (it.position.x <= 0f) {
                            if (bItem.atomicBoolean.getAndSet(false)) {
                                itemFlow.tryEmit(bItem)
                            }
                        }
                    }
                })

                bItem.bodyDef.gravityScale = 0f

                val size = (50..80).random().toFloat()
                bItem.create(WIDTH_UI+500, 0f, size, size)

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

                        setTransform(Vector2(WIDTH_UI+500, 0f).toB2, 0f)
                    }
                }
            }
        }
        coroutine?.launch {
            itemFlow.collect { bItem ->
                delay((500L..1000L).random())
                runGDX {
                    bItem.body?.apply {
                        setTransform(WIDTH_BOX2D+20, (40..560).random().toFloat().toB2, 0f)

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
                        if (it.position.x <= 0f) {
                            if (bBomb.atomicBoolean.getAndSet(false)) {
                                bombFlow.tryEmit(bBomb)
                            }
                        }
                    }
                })

                bBomb.bodyDef.gravityScale = 0f
                val size = (100..200).random().toFloat()
                bBomb.create(WIDTH_UI+500, 0f, size, size)

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

                        setTransform(Vector2(WIDTH_UI+500, 0f).toB2, 0f)
                    }
                }
            }
        }
        coroutine?.launch {
            bombFlow.collect { bBomb ->
                delay((1000L..3000L).random())
                runGDX {
                    bBomb.body?.apply {
                        setTransform(WIDTH_BOX2D+20, (100..500).random().toFloat().toB2, 0f)

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

            localAxisA.set(0f, 1f)

            lowerTranslation = 100f.toB2
            upperTranslation = 500f.toB2
            enableLimit      = true
        })
    }

}