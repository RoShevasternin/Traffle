package com.parrotrun.skydrink.game.screens

import com.parrotrun.skydrink.game.LibGDXGame
import com.parrotrun.skydrink.game.actors.AResultGroup
import com.parrotrun.skydrink.game.actors.ATimerGroup
import com.parrotrun.skydrink.game.box2d.AbstractBody
import com.parrotrun.skydrink.game.box2d.AbstractJoint
import com.parrotrun.skydrink.game.box2d.BodyId
import com.parrotrun.skydrink.game.box2d.bodies.BAvia
import com.parrotrun.skydrink.game.box2d.bodies.BCoin
import com.parrotrun.skydrink.game.box2d.bodies.BEnemy
import com.parrotrun.skydrink.game.box2d.bodies.standart.BStatic
import com.parrotrun.skydrink.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.parrotrun.skydrink.game.utils.actor.animHide
import com.parrotrun.skydrink.game.utils.actor.animShow
import com.parrotrun.skydrink.game.utils.advanced.AdvancedMouseScreen
import com.parrotrun.skydrink.game.utils.advanced.AdvancedStage
import com.parrotrun.skydrink.game.utils.font.FontParameter
import com.parrotrun.skydrink.game.utils.region
import com.parrotrun.skydrink.game.utils.runGDX
import com.parrotrun.skydrink.game.utils.toB2
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.parrotrun.skydrink.game.actors.button.AButton
import com.parrotrun.skydrink.game.utils.WIDTH_BOX2D
import com.parrotrun.skydrink.game.utils.WIDTH_UI
import com.parrotrun.skydrink.game.utils.actor.setBounds
import com.parrotrun.skydrink.game.utils.gdxGame
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

var CANONcoinCount = 0

class GameScreen(override val game: LibGDXGame) : AdvancedMouseScreen(game) {

    private val assets = game.assetsAll

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(39)
    private val font          = fontGenerator_Averta.generateFont(fontParameter)

    // Actor
    private val aTimer   = ATimerGroup(this)
    private val aCoinLbl = Label("0", Label.LabelStyle(font, Color.WHITE))
    private val aHarts   = List(3) { Image(assets.HEART) }
    private val aBack    = AButton(this, AButton.Type.BACK)

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
        setBackBackground(game.assetsAll.B_GAME.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Static()
        createB_Avia()
        createJ_Prismatic()
        createB_Items()
        createB_Bomb()

        addBack()

        val imgPanel = Image(gdxGame.assetsAll.PANEL)
        addActor(imgPanel)
        imgPanel.setBounds(691f, 978f, 539f, 65f)

        addCoinLbl()
        addTimer()
        addHarts()

        aTimer.startTimer {
            aTimer.isPause = true
            isWorldPause   = true

            gdxGame.navigationManager.navigate(LoseScreen::class.java.name)
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        addActor(aBack)
        aBack.setBounds(77f, 953f, 97f, 97f)

        aBack.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addTimer() {
        addActor(aTimer)
        aTimer.setBounds(1080f, 985f, 96f, 48f)
    }

    private fun AdvancedStage.addCoinLbl() {
        addActor(aCoinLbl)
        aCoinLbl.apply {
            setBounds(802f, 987f, 88f, 48f)
            setAlignment(Align.center)
        }
    }

    private fun AdvancedStage.addHarts() {
        var nx = 77f
        aHarts.onEach { hart ->
            addActor(hart)
            hart.setBounds(nx, 32f, 87f, 87f)
            nx += 10f + 87f
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        bStatic.create(254f, 100f, 100f, 100f)
    }

    private fun createB_Avia() {
        bAvia.create(18f, 357f, 472f, 472f)

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

                            gdxGame.navigationManager.navigate(WinScreen::class.java.name)
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

                val size = (70..170).random().toFloat()
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
                val size = (150..300).random().toFloat()
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
            upperTranslation = 700f.toB2
            enableLimit      = true
        })
    }

}