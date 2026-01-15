package com.fontantwo.tennisfortwo.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.fontantwo.tennisfortwo.game.GDXGame
import com.fontantwo.tennisfortwo.game.actors.AFountain2Panel
import com.fontantwo.tennisfortwo.game.actors.button.AButton
import com.fontantwo.tennisfortwo.game.actors.panel.APanelSelect
import com.fontantwo.tennisfortwo.game.box2d.AbstractBody
import com.fontantwo.tennisfortwo.game.box2d.AbstractJoint
import com.fontantwo.tennisfortwo.game.box2d.BodyId
import com.fontantwo.tennisfortwo.game.box2d.bodies.BBall
import com.fontantwo.tennisfortwo.game.box2d.bodies.BPortal
import com.fontantwo.tennisfortwo.game.box2d.bodies.BUser
import com.fontantwo.tennisfortwo.game.box2d.bodiesGroup.BGBorders
import com.fontantwo.tennisfortwo.game.utils.*
import com.fontantwo.tennisfortwo.game.utils.actor.animHide
import com.fontantwo.tennisfortwo.game.utils.actor.animShow
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedStage
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedTennisScreen
import com.fontantwo.tennisfortwo.game.utils.font.FontParameter
import java.util.concurrent.atomic.AtomicBoolean

class GameScreen(override val game: GDXGame): AdvancedTennisScreen(game) {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font50    = fontGenerator_Langar.generateFont(parameter.setSize(63))

    private val labelStyle = Label.LabelStyle(font50, GameColor.whiter)

    private var leftRecord  = 0
    private var rightRecord = 0

    // Actor
    private val panelFountain  = AFountain2Panel(this)
    private val backBtn        = AButton(this, AButton.Static.Type.Back)
    private val stolImg        = Image(game.assetsAll.STOL)
    private val dvoitocheImg   = Image(game.assetsAll.dvokrapka)
    private val leftIconImg    = Image(game.assetsAll.rocketLeftList[APanelSelect.LEFT_INDEX])
    private val rightIconImg   = Image(game.assetsAll.rocketLeftList[APanelSelect.RIGHT_INDEX])
    private val leftRecordLbl  = Label(leftRecord.toString(), labelStyle)
    private val rightRecordLbl = Label(rightRecord.toString(), labelStyle)

    // Body
    private val bUserA = BUser(this, game.assetsAll.rocketLeftList[APanelSelect.LEFT_INDEX])
    private val bUserB = BUser(this, game.assetsAll.rocketRightList[APanelSelect.RIGHT_INDEX])
    private val bBall  = BBall(this)
    private val bPortalA = BPortal(this)
    private val bPortalB = BPortal(this)

    // Body Group
    private val bgBorders = BGBorders(this)

    // Joint
    private val jMotorA = AbstractJoint<MotorJoint, MotorJointDef>(this)
    private val jMotorB = AbstractJoint<MotorJoint, MotorJointDef>(this)
    private val jMotorBall = AbstractJoint<MotorJoint, MotorJointDef>(this)

    // Field
    private val isDestroyJoint = AtomicBoolean(false)
    private val isStartBall    = AtomicBoolean(false)

    override fun show() {
//        game.musicUtil.apply { music = game.apply {
//            isLooping = true
//            coff      = 0.25f
//        } }

        stageUI.root.animHide()
        setBackBackground(game.assetsLoader.MAIN.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun hideScreen(block: Block) {
        panelFountain.animHideParticles()
        stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {

//            game.musicUtil.apply { music = main.apply {
//                isLooping = true
//                coff      = 0.15f
//            } }

            block.invoke()
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addStol()
        addBack()
        addIcons()
        addDvokrapku()
        addRecords()

        createBG_Borders()

        createB_Portal()
        createB_User()
        createB_Ball()

        createJ_User()
        createJ_Ball()

        addAndFillActor(panelFountain)
    }

    // Actor ------------------------------------------------------------------------

    private fun AdvancedStage.addStol() {
        addActor(stolImg)
        stolImg.setBounds(0f, 14f, 1379f, 949f)
    }

    private fun AdvancedStage.addBack() {
        addActor(backBtn)
        backBtn.setBounds(1407f, 22f, 133f, 126f)
        backBtn.setOnClickListener { hideScreen { game.navigationManager.back() } }
    }

    private fun AdvancedStage.addIcons() {
        addActors(leftIconImg, rightIconImg)
        leftIconImg.setBounds(1413f, 761f, 104f, 104f)
        rightIconImg.setBounds(1413f, 222f, 104f, 104f)
    }

    private fun AdvancedStage.addDvokrapku() {
        addActor(dvoitocheImg)
        dvoitocheImg.setBounds(1450f, 498f, 29f, 89f)
    }

    private fun AdvancedStage.addRecords() {
        addActors(leftRecordLbl, rightRecordLbl)
        leftRecordLbl.apply {
            setBounds(1376f, 655f, 188f, 88f)
            setAlignment(Align.center)
        }
        rightRecordLbl.apply {
            setBounds(1376f, 344f, 188f, 88f)
            setAlignment(Align.center)
        }
    }

    // Body ------------------------------------------------------------------------
    private fun createB_User() {
        // A
        bUserA.apply {
            id = BodyId.USER_A
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.USER_B, BodyId.BALL))
        }
        bUserA.create(217f, 408f, 131f, 131f)

        // B
        bUserB.apply {
            id = BodyId.USER_B
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.USER_A, BodyId.BALL))
        }
        bUserB.create(1031f, 408f, 131f, 131f)
    }

    private fun createB_Ball() {
        bBall.apply {
            id = BodyId.BALL
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.USER_A, BodyId.USER_B, BodyId.PORTAL))
        }
        bBall.create(638f, 428f, 90f, 90f)

        bBall.beginContactBlockArray.add(AbstractBody.ContactBlock { bbb, _ ->
            if (isDestroyJoint.getAndSet(true).not()) runGDX {
                isStartBall.set(false)
                jMotorBall.destroy()
            }
            if (bbb.id == BodyId.USER_A || bbb.id == BodyId.USER_B) {
                game.soundUtil.apply { play(tennis, 0.2f) }
            }
        })
    }

    private fun createB_Portal() {
        // A
        bPortalA.apply {
            id = BodyId.PORTAL
            collisionList.add(BodyId.BALL)
        }
        bPortalA.create(108f, 317f, 26f, 315f)

        bPortalA.beginContactBlockArray.add(AbstractBody.ContactBlock { _, _ ->
            toStartBall()

            runGDX {
                rightRecord++
                rightRecordLbl.setText(rightRecord)
            }
        })

        // B
        bPortalB.apply {
            id = BodyId.PORTAL
            collisionList.add(BodyId.BALL)
        }
        bPortalB.create(1243f, 317f, 26f, 315f)

        bPortalB.beginContactBlockArray.add(AbstractBody.ContactBlock { _, _ ->
            toStartBall()

            runGDX {
                leftRecord++
                leftRecordLbl.setText(leftRecord)
            }
        })
    }

    // ---------------------------------------------------
    // create Joint
    // ---------------------------------------------------

    private fun createJ_User() {
        // A
        jMotorA.create(MotorJointDef().apply {
            bodyA = bgBorders.bDown.body
            bodyB = bUserA.body
            collideConnected = true

            maxForce  = 80f
            maxTorque = 30f
            correctionFactor = 0.8f
            linearOffset.set(Vector2(185f, 360f+67).toB2)
        })

        // B
        jMotorB.create(MotorJointDef().apply {
            bodyA = bgBorders.bDown.body
            bodyB = bUserB.body
            collideConnected = true

            maxForce  = 80f
            maxTorque = 30f
            correctionFactor = 0.8f
            linearOffset.set(Vector2(933f, 360f+67).toB2)
        })
    }

    private fun createJ_Ball() {
        jMotorBall.create(MotorJointDef().apply {
            bodyA = bgBorders.bDown.body
            bodyB = bBall.body
            collideConnected = true

            maxForce  = 20f
            maxTorque = 20f
            correctionFactor = 0f
            linearOffset.set(Vector2(683f, 361f+67).toB2)
        })
    }

    // Body Group ------------------------------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f, 0f, WIDTH_UI, HEIGHT_UI)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun toStartBall() {
        if (isStartBall.getAndSet(true).not()) {
            runGDX {
                game.soundUtil.apply { play(goal, /*volumeLevel * 0.2f*/) }

                bBall.body?.apply {
                    setLinearVelocity(0f, 0f)
                    isAwake = false
                    setTransform(Vector2(683f, 361f+67).toB2, 0f)
                }

                createJ_Ball()

                isDestroyJoint.set(false)
            }
        }
    }

}