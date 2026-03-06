package com.bunnypanny.eggcatch.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.bunnypanny.eggcatch.game.GDXGame
import com.bunnypanny.eggcatch.game.actors.button.AButton
import com.bunnypanny.eggcatch.game.box2d.AbstractBody
import com.bunnypanny.eggcatch.game.box2d.AbstractJoint
import com.bunnypanny.eggcatch.game.box2d.BodyId
import com.bunnypanny.eggcatch.game.box2d.bodies.BBag
import com.bunnypanny.eggcatch.game.box2d.bodies.BEgg
import com.bunnypanny.eggcatch.game.box2d.bodies.BEnemy
import com.bunnypanny.eggcatch.game.box2d.bodies.standart.BStatic
import com.bunnypanny.eggcatch.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.bunnypanny.eggcatch.game.utils.actor.animHide
import com.bunnypanny.eggcatch.game.utils.actor.animShow
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedMouseScreen
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedStage
import com.bunnypanny.eggcatch.game.utils.font.FontParameter
import com.bunnypanny.eggcatch.game.utils.gdxGame
import com.bunnypanny.eggcatch.game.utils.region
import com.bunnypanny.eggcatch.game.utils.runGDX
import com.bunnypanny.eggcatch.game.utils.toB2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class GameScreen(override val game: GDXGame) : AdvancedMouseScreen(game) {

    private var counterStar = 0

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "/").setSize(56)
    private val font          = fontGenerator_Knewave.generateFont(fontParameter)

    // Actor
    private val randomCOUNT = (10..50).random()
    private val aCountLbl = Label("0/$randomCOUNT", Label.LabelStyle(font, Color.valueOf("FFE9CF")))

    // Body
    private val bStatic = BStatic(this)
    private val bAvia   = BBag(this)

    // Joint
    private val jPrismatic = AbstractJoint<PrismaticJoint, PrismaticJointDef>(this)

    // Field
    private val itemFlow = MutableSharedFlow<BEgg>(replay = 15)
    //private val bombFlow = MutableSharedFlow<BEnemy>(replay = 5)


    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.assetsAll.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Static()
        createB_Bag()
        createJ_Prismatic()
        createB_Items()
        //createB_Bomb()

        addBack()
        addCounter()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        val aBackBtn = AButton(this@GameScreen, AButton.Static.Type.BACK)
        addActor(aBackBtn)
        aBackBtn.setBounds(75f, 1680f, 220f, 220f)

        aBackBtn.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addCounter() {
        val img = Image(gdxGame.assetsAll.PANEL)
        addActor(img)
        img.setBounds(338f, 1680f, 405f, 172f)

        addActor(aCountLbl)
        aCountLbl.apply {
            setBounds(469f, 1742f, 143f, 66f)
            setAlignment(Align.center)
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        bStatic.create(-2f, 100f, 1f, 1f)
    }

    private fun createB_Bag() {
        bAvia.create(239f, 105f, 603f, 603f)

        bAvia.beginContactBlockArray.add(AbstractBody.ContactBlock { b2 ->
            when (b2.id) {
                BodyId.Game.COIN -> {
                    b2 as BEgg
                    if (b2.atomicBoolean.getAndSet(false)) {
                        itemFlow.tryEmit(b2)
                        game.soundUtil.apply { play(bonus) }

                        counterStar += 1
                        aCountLbl.setText("$counterStar/$randomCOUNT")

                        if (counterStar >= randomCOUNT) stageUI.root.animHide {
                            gdxGame.navigationManager.navigate(WinScreen::class.java.name)
                        }
                    }
                }
                BodyId.Game.ENEMY -> {
                    b2 as BEnemy
                    if (b2.atomicBoolean.getAndSet(false)) {
                        //bombFlow.tryEmit(b2)
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
            BEgg(this).also { bItem ->
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

                val size = (150..250).random().toFloat()
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

    /*private fun createB_Bomb() {
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
    }*/

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