package com.flightcoll.bridgertons.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.flightcoll.bridgertons.game.SuberGame
import com.flightcoll.bridgertons.game.actors.button.AButton
import com.flightcoll.bridgertons.game.box2d.AbstractBody
import com.flightcoll.bridgertons.game.box2d.AbstractJoint
import com.flightcoll.bridgertons.game.box2d.BodyId
import com.flightcoll.bridgertons.game.box2d.bodies.BCompas
import com.flightcoll.bridgertons.game.box2d.bodies.BPlane
import com.flightcoll.bridgertons.game.box2d.bodies.BXxx
import com.flightcoll.bridgertons.game.box2d.bodiesGroup.BGBorders
import com.flightcoll.bridgertons.game.box2d.destroyAll
import com.flightcoll.bridgertons.game.utils.*
import com.flightcoll.bridgertons.game.utils.actor.animHide
import com.flightcoll.bridgertons.game.utils.actor.animShow
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedStage
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedUserScreen
import com.flightcoll.bridgertons.game.utils.font.FontParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class IglaScreen(override val game: SuberGame): AdvancedUserScreen(game) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(50)
    private val font          = fontGenerator_Jura.generateFont(fontParameter)

    // Actor
    private val aCountLbl = Label("", LabelStyle(font, Color.WHITE))
    private val aBack     = AButton(this, AButton.Static.Type.mEnU)
    private val aCntImg   = Image(game.assets.colichestvo)

    // BodyGroup
    private val bgBorders = BGBorders(this)

    // Body
    private val bPilot = BPlane(this)
    private val bCompasList = List(10) { BCompas(this) }
    private val bXxxList    = List(4) { BXxx(this, BXxx.Static.Type.entries[it]) }

    // Joint
    private val jMotor = AbstractJoint<MotorJoint, MotorJointDef>(this)

    // Fielda
    private val counterFlow = MutableStateFlow(0)
    private val compassFlow = MutableSharedFlow<BCompas>(10)
    private val xxxFlow     = MutableSharedFlow<BXxx>(1)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.fisters.zagruzon.region)
        super.show()
        stageUI.root.animShow(vremia_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addColichestvo()
        addCountLbl()
        addBack()

        createBG_Borders()

        createB_Pilot()
        createJ_Motor()

        createB_Compas()
        createB_Xxx()
    }

    override fun dispose() {
        listOf(bgBorders).destroyAll()
        super.dispose()
    }


    // Add Acator

    private fun AdvancedStage.addCountLbl() {
        addActor(aCountLbl)
        aCountLbl.setAlignment(Align.center)
        aCountLbl.setBounds(706f, 47f, 200f, 59f)
    }

    private fun AdvancedStage.addBack() {
        addActors(aBack)

        aBack.apply {
            setBounds(394f, 28f, 156f, 92f)
            setOnClickListener {
                stageUI.root.animHide(vremia_ANIM) { game.navigationManager.back() }
            }
        }
    }

    private fun AdvancedStage.addColichestvo() {
        addActors(aCntImg)
        aCntImg.setBounds(572f, 2f, 383f, 144f)

        coroutine?.launch {
            counterFlow.collect {
                runGDX { aCountLbl.setText(it) }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Create Body Group
    // ------------------------------------------------------------------------
    private fun createBG_Borders() {
        bgBorders.create(0f,0f,WIDTH,HEIGHT)
    }

    // ------------------------------------------------------------------------
    // Create Body
    // ------------------------------------------------------------------------
    private fun createB_Pilot() {
        bPilot.apply {
            id = BodyId.PILOT
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.COMPASS, BodyId.XXX))

            bodyDef.fixedRotation = true
        }
        bPilot.create(24f, 351f, 299f, 198f)

        val bomX = listOf(-500f, -400f, -300f)
        val bomY = listOf(-500f, -400f, -300f, 500f, 400f, 300f)

        bPilot.beginContactBlockArray.add(AbstractBody.ContactBlock {
            when(it.id) {
                BodyId.BORDERS -> game.soundUtil.apply { play(barier) }
                BodyId.COMPASS -> {
                    it as BCompas
                    if (it.isOnStart.getAndSet(false)) {
                        game.soundUtil.apply { play(compass) }
                        Gdx.input.vibrate(100)
                        bPilot.body?.apply { applyLinearImpulse(bomX.random(), bomY.random(), worldCenter.x, worldCenter.y, true) }
                        counterFlow.value += 1
                        compassFlow.tryEmit(it)
                    }
                }
                BodyId.XXX -> {
                    it as BXxx
                    if (it.isOnStart.getAndSet(false)) {
                        game.soundUtil.apply { play(xxx) }
                        Gdx.input.vibrate(150)
                        bPilot.body?.apply { applyLinearImpulse(bomX.random(), bomY.random(), worldCenter.x, worldCenter.y, true) }
                        counterFlow.value += it.tipe.viluet
                        xxxFlow.tryEmit(it)
                    }
                }
            }
        })
    }

    private fun createB_Compas() {
        bCompasList.onEach { bCompass ->
            bCompass.apply {
                id = BodyId.COMPASS
                collisionList.add(BodyId.PILOT)

                bodyDef.apply {
                    fixedRotation = true
                    gravityScale  = 0f
                }
            }
            bCompass.create(WIDTH, HEIGHT, 103f, 74f)

            var timer = 0f
            bCompass.renderBlockArray.add(AbstractBody.RenderBlock {
                timer += it
                if (timer >= 1) {
                    timer = 0f
                    if((bCompass.body?.position?.x ?: 0f) <= 0f) {
                        if (bCompass.isOnStart.getAndSet(false)) {
                            compassFlow.tryEmit(bCompass)
                        }
                    }
                }
            })

            compassFlow.tryEmit(bCompass)
        }

        val impulse  = Vector2(-100f, 0f)
        val startPos = Vector2()

        coroutine?.launch {
            compassFlow.collect { bCompass ->
                bCompass.body?.apply {
                    setLinearVelocity(0f, 0f)
                    isAwake = false

                    runGDX {
                        setTransform(startPos.set(1600f, (145..826).random().toFloat()).toB2, 0f)
                        bCompass.isOnStart.set(true)
                    }
                }
            }
        }
        coroutine?.launch {
            compassFlow.collect { bCompass ->
                delay((500..1000L).random())

                bCompass.body?.apply {
                    applyLinearImpulse(impulse.apply { x = -(100..300).random().toFloat() }, worldCenter, true)
                }
            }
        }

    }

    private fun createB_Xxx() {
        bXxxList.onEach { bXxx ->
            bXxx.apply {
                id = BodyId.XXX
                collisionList.add(BodyId.PILOT)

                bodyDef.apply {
                    fixedRotation = true
                    gravityScale  = 0f
                }
            }
            bXxx.create(WIDTH, HEIGHT, 167f, 74f)

            var timer = 0f
            bXxx.renderBlockArray.add(AbstractBody.RenderBlock {
                timer += it
                if (timer >= 1) {
                    timer = 0f
                    if((bXxx.body?.position?.x ?: 0f) <= 0f) {
                        if (bXxx.isOnStart.getAndSet(false)) {
                            xxxFlow.tryEmit(bXxx)
                        }
                    }
                }
            })
        }

        xxxFlow.tryEmit(bXxxList.random())

        val impulse  = Vector2(-10f, 0f)
        val startPos = Vector2()

        coroutine?.launch {
            xxxFlow.collect { bXxx ->
                bXxx.body?.apply {
                    setLinearVelocity(0f, 0f)
                    isAwake = false

                    runGDX {
                        setTransform(startPos.set(1600f, (145..826).random().toFloat()).toB2, 0f)
                        bXxx.isOnStart.set(true)
                    }
                }
            }
        }
        coroutine?.launch {
            xxxFlow.collect {
                delay((3000..5000L).random())

                val bXxx = bXxxList.random()

                bXxx.body?.apply {
                    applyLinearImpulse(impulse.apply { x = -(10..50).random().toFloat() }, worldCenter, true)
                }
            }
        }

    }

    // Joint
    private fun createJ_Motor() {
        jMotor.create(MotorJointDef().apply {
            bodyA = bgBorders.bDown.body
            bodyB = bPilot.body
            collideConnected = true

            linearOffset.set(Vector2(24f, 361f).toB2)
            maxForce = 1000f

        })
    }

}