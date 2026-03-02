package com.skynebowow.runnerblue.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.skynebowow.runnerblue.game.GDXGame
import com.skynebowow.runnerblue.game.actors.button.AButton
import com.skynebowow.runnerblue.game.box2d.AbstractBody
import com.skynebowow.runnerblue.game.box2d.AbstractJoint
import com.skynebowow.runnerblue.game.box2d.BodyId
import com.skynebowow.runnerblue.game.box2d.bodies.BAvia
import com.skynebowow.runnerblue.game.box2d.bodies.BCoin
import com.skynebowow.runnerblue.game.box2d.bodies.BEnemy
import com.skynebowow.runnerblue.game.box2d.bodies.standart.BStatic
import com.skynebowow.runnerblue.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.skynebowow.runnerblue.game.utils.actor.animHide
import com.skynebowow.runnerblue.game.utils.actor.animShow
import com.skynebowow.runnerblue.game.utils.actor.setBounds
import com.skynebowow.runnerblue.game.utils.actor.setOnClickListener
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedMouseScreen
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedStage
import com.skynebowow.runnerblue.game.utils.font.FontParameter
import com.skynebowow.runnerblue.game.utils.gdxGame
import com.skynebowow.runnerblue.game.utils.region
import com.skynebowow.runnerblue.game.utils.runGDX
import com.skynebowow.runnerblue.game.utils.toB2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.modules.serializersModuleOf

class GameScreen(override val game: GDXGame) : AdvancedMouseScreen(game) {

    private val assets = game.gameAssets

    private var counterStar = 0

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "/").setSize(50)
    private val font          = fontGenerator_Knewave.generateFont(fontParameter)

    // Actor
    private val aStarLbl = Label("0/8", Label.LabelStyle(font, Color.valueOf("0192AD")))

    // Body
    private val bStatic = BStatic(this)
    private val bAvia   = BAvia(this)

    // Joint
    private val jPrismatic = AbstractJoint<PrismaticJoint, PrismaticJointDef>(this)

    // Field
    private val itemFlow = MutableSharedFlow<BCoin>(replay = 15)
    private val bombFlow = MutableSharedFlow<BEnemy>(replay = 5)


    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.gameAssets.BK_GAME.region)
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
        addStarLbl()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        val menu = AButton(this@GameScreen, AButton.Static.Type.BACK)
        addActor(menu)
        menu.setBounds(62f, 1757f, 110f, 110f)

        menu.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addStarLbl() {
        val img = Image(gdxGame.gameAssets.PALEN)
        addActor(img)
        img.setBounds(401f, 1760f, 278f, 110f)

        addActor(aStarLbl)
        aStarLbl.apply {
            setBounds(538f, 1776f, 92f, 78f)
            setAlignment(Align.center)
        }

        addActor(aStarLbl)
        aStarLbl.apply {
            setBounds(538f, 1776f, 92f, 78f)
            setAlignment(Align.center)
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        bStatic.create(-2f, 0f, 1f, 1f)
    }

    private fun createB_Avia() {
        bAvia.create(239f, 105f, 603f, 603f)

        bAvia.beginContactBlockArray.add(AbstractBody.ContactBlock { b2 ->
            when (b2.id) {
                BodyId.Game.COIN -> {
                    b2 as BCoin
                    if (b2.atomicBoolean.getAndSet(false)) {
                        itemFlow.tryEmit(b2)
                        game.soundUtil.apply { play(bonus) }

                        counterStar += 1
                        aStarLbl.setText("$counterStar/8")

                        if (counterStar >= 8) stageUI.root.animHide {
                            gdxGame.navigationManager.navigate(WinScreen::class.java.name)
                        }
                    }
                }
                BodyId.Game.ENEMY -> {
                    b2 as BEnemy
                    if (b2.atomicBoolean.getAndSet(false)) {
                        bombFlow.tryEmit(b2)
                        game.soundUtil.apply { play(boom) }
                        bAvia.body?.applyLinearImpulse(Vector2(listOf(-20000, 20000).random().toFloat(), 0f), bAvia.body?.worldCenter, true)

                        stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { gdxGame.navigationManager.navigate(LoseScreen::class.java.name) }
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

                val size = (80..120).random().toFloat()
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
                        setTransform((50..900).random().toFloat().toB2, 1920f.toB2, 0f)

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
                //val size = (100..250).random().toFloat()
                bBomb.create(-400f, 0f, 238f, 211f)

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
                        setTransform((100..800).random().toFloat().toB2, 1920f.toB2, 0f)

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

            this.localAnchorB.add(0f, -1.8f)

            lowerTranslation = 100f.toB2
            upperTranslation = (1000f).toB2
            enableLimit      = true
        })
    }

}