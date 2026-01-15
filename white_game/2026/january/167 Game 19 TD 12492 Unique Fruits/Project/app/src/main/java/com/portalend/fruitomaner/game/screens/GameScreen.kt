package com.portalend.fruitomaner.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.portalend.fruitomaner.game.LibGDXGame
import com.portalend.fruitomaner.game.actors.AButton
import com.portalend.fruitomaner.game.box2d.AbstractBody
import com.portalend.fruitomaner.game.box2d.AbstractJoint
import com.portalend.fruitomaner.game.box2d.BodyId
import com.portalend.fruitomaner.game.box2d.bodies.BItem
import com.portalend.fruitomaner.game.box2d.bodies.BStatic
import com.portalend.fruitomaner.game.box2d.bodies.BWheel
import com.portalend.fruitomaner.game.utils.*
import com.portalend.fruitomaner.game.utils.actor.animHide
import com.portalend.fruitomaner.game.utils.actor.animShow
import com.portalend.fruitomaner.game.utils.advanced.AdvancedStage
import com.portalend.fruitomaner.game.utils.advanced.AdvancedUserScreen
import com.portalend.fruitomaner.game.utils.font.FontParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameScreen(override val game: LibGDXGame) : AdvancedUserScreen() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(41)
    private val font = fontGenerator_Regular.generateFont(fontParameter)

    companion object {
        var record = 0
    }

    // Actor
    private val imgTimer  = Image(game.all.timer)
    private val imgCount  = Image(game.all.count)
    private val lblRecord = Label("$record", Label.LabelStyle(font, Color.valueOf("522D16")))
    private val lblTimer  = Label("60", Label.LabelStyle(font, Color.WHITE))
    private val btnBack   = AButton(this, AButton.Static.Type.Back)

    // Body
    private val bWheel    = BWheel(this)
    private val bItemList = List(20) { BItem(this) }

    // Fluid
    private val itemFlow = MutableSharedFlow<BItem>(20)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.backgrounds.random().region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Wheel()
        createB_Item()

        createJ_Motor()

        addPanel()
    }

    // Add
    private fun AdvancedStage.addPanel() {
        addActor(imgTimer)
        imgTimer.setBounds(327f, 1412f, 214f, 103f)

        addActor(imgCount)
        imgCount.setBounds(627f, 1412f, 214f, 99f)

        addActor(btnBack)
        btnBack.apply {
            setBounds(27f, 1415f, 214f, 96f)
            setOnClickListener { stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() } }
        }

        addActor(lblRecord)
        lblRecord.apply {
            setBounds(664f, 1436f, 140f, 52f)
            setAlignment(Align.center)
        }

        addActor(lblTimer)
        lblTimer.apply {
            setBounds(372f, 1423f, 123f, 83f)
            setAlignment(Align.center)
        }

        var timer = 10
        coroutine?.launch {
            while (timer > 0 && isActive) {
                delay(1000L)
                runGDX {
                    timer--
                    lblTimer.setText(timer)
                }
            }

            // GAME OVER
            stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(ResultScreen::class.java.name) }
        }

    }

    // Create Body ------------------------------------------------------------------------

    private fun createB_Item() {
        bItemList.onEach { bItem ->
            bItem.id = BodyId.ITEM
            bItem.collisionList.addAll(arrayOf(BodyId.WHEEL))
            bItem.create(0f, -300f, 146f, 146f)

            var timer = 0f
            bItem.renderBlockArray.add(AbstractBody.RenderBlock {
                timer += it
                if (timer >= 1) {
                    timer = 0f
                    if ((bItem.body?.position?.y ?: 0f) >= HEIGHT_BOX2D+2) {
                        if (bItem.isOnStart.getAndSet(false)) {
                            itemFlow.tryEmit(bItem)
                        }
                    }
                }
            })

            itemFlow.tryEmit(bItem)
        }

        val startPos = Vector2()

        coroutine?.launch {
            itemFlow.collect { bItem ->
                bItem.body?.apply {
                    setLinearVelocity(0f, 0f)
                    isAwake = false
                    gravityScale = 0f

                    runGDX {
                        val xxx = (80..800).random().toFloat()
                        setTransform(startPos.set(xxx, -200f).toB2, 0f)
                        bItem.isOnStart.set(true)
                    }
                }
            }
        }
        coroutine?.launch {
            itemFlow.collect { bItem ->
                delay((200..400L).random())

                runGDX {
                    bItem.body?.apply {
                        gravityScale = 1f
                        isAwake = true
                        if (Random.nextBoolean()) applyTorque(5f, false) else applyTorque(-5f, false)
                        applyLinearImpulse(Vector2(0f, 1f), worldCenter, true)
                    }
                }
            }
        }
    }

    private fun createB_Wheel() {
        bWheel.apply {
            id = BodyId.WHEEL
            collisionList.addAll(arrayOf(BodyId.ITEM, BodyId.BORDER))

            create(377f, 32f, 250f, 250f)

            beginContactBlockArray.add(AbstractBody.ContactBlock { enemy ->
                when (enemy.id) {
                    BodyId.ITEM -> {
                        enemy as BItem

                        if (enemy.isOnStart.getAndSet(false)) {

                            game.soundUtil.apply {
                                game.soundUtil.apply { play(collect, 0.35f) }
                            }

                            record++

                            lblRecord.setText(record)
                            itemFlow.tryEmit(enemy)
                        }
                    }
                }
            })
        }
    }

    private fun createJ_Motor() {
        val stata = BStatic(this)
        stata.create(401f, 653f, 66f, 66f)

        val jMotor = AbstractJoint<MotorJoint, MotorJointDef>(this)
        jMotor.create(MotorJointDef().apply {
            bodyA = stata.body
            bodyB = bWheel.body

            maxForce = bWheel.body!!.mass * 33
            maxTorque = bWheel.body!!.mass * 33
        })
    }

}