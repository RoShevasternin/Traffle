package com.bounceroval.mazedackq.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.bounceroval.mazedackq.game.LibGDXGame
import com.bounceroval.mazedackq.game.actors.AButton
import com.bounceroval.mazedackq.game.box2d.AbstractBody
import com.bounceroval.mazedackq.game.box2d.AbstractJoint
import com.bounceroval.mazedackq.game.box2d.BodyId
import com.bounceroval.mazedackq.game.utils.*
import com.bounceroval.mazedackq.game.utils.actor.animHide
import com.bounceroval.mazedackq.game.utils.actor.animShow
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedStage
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedUserScreen
import com.bounceroval.mazedackq.game.utils.font.FontParameter
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.bounceroval.mazedackq.game.box2d.bodies.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameScreen(override val game: LibGDXGame) : AdvancedUserScreen() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(35)
    private val font          = fontGenerator_Inter.generateFont(fontParameter)

    // Actor
    private val panelImg   = Image(game.all.panel)
    private val planeImg   = Image(game.all.plans[PrePlayScreen.AVIA_INDEX])
    private val aBack      = AButton(this, AButton.Static.Type.Back)
    private val aRecordLbl = Label("0", Label.LabelStyle(font, Color.WHITE))

    // Body
    private val bItemList = List(20) { BItem(this) }
    private val bPlane    = BPlane(this)
    private val bBorderLeft  = BBorder(this)
    private val bBorderRight = BBorder(this)
    private val bBordersList = List(15) { BBorderBall(this) }


    // Fluid
    private val itemFlow = MutableSharedFlow<BItem>(20)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.backgrounds.random().region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Border()
        createB_Borders()
        createB_Plane()
        createJ_Motor()

        createB_Item()

        addPanel()
    }

    // Add
    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(31f, 1614f, 295f, 248f)

        addActor(planeImg)
        planeImg.setBounds(83f, 1695f, 192f, 146f)

        addActor(aBack)
        aBack.apply {
            setBounds(75f, 1473f, 209f, 96f)
            setOnClickListener { stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() } }
        }

        addActor(aRecordLbl)
        aRecordLbl.apply {
            setBounds(131f, 1637f, 97f, 43f)
            setAlignment(Align.center)
        }

    }

    // Create Body ------------------------------------------------------------------------

    private fun createB_Border() {
        bBorderLeft.apply {
            id = BodyId.BORDER
            collisionList.addAll(arrayOf(BodyId.ITEM, BodyId.PLAN))
            create(-50f, 0f, 50f, 1890f)
        }
        bBorderRight.apply {
            id = BodyId.BORDER
            collisionList.addAll(arrayOf(BodyId.ITEM, BodyId.PLAN))
            create(1063f, 0f, 50f, 1890f)
        }
    }

    private fun createB_Item() {
        bItemList.onEach { bItem ->
            bItem.id = BodyId.ITEM
            bItem.collisionList.addAll(arrayOf(BodyId.PLAN, BodyId.BORDER))
            bItem.create(0f, HEIGHT + 50, 90f, 90f)

            var timer = 0f
            bItem.renderBlockArray.add(AbstractBody.RenderBlock {
                timer += it
                if (timer >= 1) {
                    timer = 0f
                    if ((bItem.body?.position?.y ?: 0f) <= 0f) {
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
                        val xxx = (50..950).random().toFloat()
                        setTransform(startPos.set(xxx, HEIGHT + 50).toB2, 0f)
                        bItem.isOnStart.set(true)
                    }
                }
            }
        }
        coroutine?.launch {
            itemFlow.collect { bItem ->
                delay((115..460L).random())

                runGDX {
                    bItem.body?.apply {
                        gravityScale = 1f
                        isAwake = true
                        if (Random.nextBoolean()) applyTorque(5f, false) else applyTorque(-5f, false)
                        applyLinearImpulse(Vector2(0f, -1f), worldCenter, true)
                    }
                }
            }
        }

    }

    private fun createB_Plane() {
        bPlane.apply {
            id = BodyId.PLAN
            collisionList.addAll(arrayOf(BodyId.ITEM, BodyId.BORDER))

            create(388f, 96f, 286f, 217f)

            var counter  = 0

            beginContactBlockArray.add(AbstractBody.ContactBlock { enemy ->
                when (enemy.id) {
                    BodyId.ITEM -> {
                        enemy as BItem

                        if (enemy.isOnStart.getAndSet(false)) {
                            if (SettingScreen.isVIBRO) Gdx.input.vibrate(100)

                            game.soundUtil.apply {
                                game.soundUtil.apply { play(collect, 0.5f) }
                            }

                            counter += (10..40).random()

                            aRecordLbl.setText(counter)
                            itemFlow.tryEmit(enemy)
                        }
                    }
                }
            })
        }
    }

    private fun createJ_Motor() {
        val bst = BStatic(this)
        bst.create(-50f, 123f, 100f, 100f)

        AbstractJoint<PrismaticJoint, PrismaticJointDef>(this).create(PrismaticJointDef().apply {
            bodyA = bst.body
            bodyB = bPlane.body

            enableLimit = true
            lowerTranslation = 115f.toB2
            upperTranslation = 925f.toB2
        })
    }

    private fun createB_Borders() {
        val pos1 = listOf(
            Vector2(-25f, 473f),
            Vector2(234f, 597f),
            Vector2(481f, 498f),
            Vector2(759f, 597f),
            Vector2(108f, 870f),
            Vector2(344f, 846f),
            Vector2(650f, 870f),
            Vector2(995f, 895f),
            Vector2(809f, 1120f),
            Vector2(381f, 1135f),
            Vector2(108f, 1282f),
            Vector2(344f, 1538f),
            Vector2(550f, 1375f),
            Vector2(777f, 1475f),
            Vector2(995f, 1695f),
        )

        val pos2 = listOf(
            Vector2(995f, 1695f),
            Vector2(777f, 1688f),
            Vector2(344f, 1538f),
            Vector2(621f, 1462f),
            Vector2(998f, 1320f),
            Vector2(244f, 1282f),
            Vector2(465f, 1282f),
            Vector2(-50f, 1070f),
            Vector2(275f, 994f),
            Vector2(611f, 994f),
            Vector2(990f, 1020f),
            Vector2(990f, 753f),
            Vector2(481f, 721f),
            Vector2(127f, 771f),
            Vector2(294f, 548f),
        )

        val pos3 = listOf(
            Vector2(808f, 1639f),
            Vector2(808f, 1466f),
            Vector2(494f, 1522f),
            Vector2(613f, 1266f),
            Vector2(394f, 1266f),
            Vector2(175f, 1266f),
            Vector2(808f, 1091f),
            Vector2(417f, 945f),
            Vector2(-50f, 945f),
            Vector2(175f, 647f),
            Vector2(417f, 647f),
            Vector2(659f, 647f),
            Vector2(808f, 791f),
            Vector2(999f, 425f),
            Vector2(-50f, 425f),
        )

        var pos: Vector2

        val posa = listOf(pos1, pos2, pos3).random()

        bBordersList.onEachIndexed { index, bBorderBall ->
            bBorderBall.apply {
                id = BodyId.BORDER
                collisionList.addAll(arrayOf(BodyId.ITEM))
                pos = posa[index]
                create(pos.x, pos.y, 100f, 100f)
            }
        }
    }


}