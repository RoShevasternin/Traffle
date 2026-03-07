package com.oceanstar.ballduinstar.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.oceanstar.ballduinstar.game.actors.ATmpGroup
import com.oceanstar.ballduinstar.game.actors.button.AButton
import com.oceanstar.ballduinstar.game.box2d.AbstractBody
import com.oceanstar.ballduinstar.game.box2d.BodyId
import com.oceanstar.ballduinstar.game.box2d.WorldUtil
import com.oceanstar.ballduinstar.game.box2d.bodies.BCupcake
import com.oceanstar.ballduinstar.game.box2d.bodies.BPlatform
import com.oceanstar.ballduinstar.game.box2d.bodies.BShar
import com.oceanstar.ballduinstar.game.utils.Block
import com.oceanstar.ballduinstar.game.utils.HEIGHT_UI
import com.oceanstar.ballduinstar.game.utils.TIME_ANIM_SCREEN
import com.oceanstar.ballduinstar.game.utils.WIDTH_UI
import com.oceanstar.ballduinstar.game.utils.actor.HAlign
import com.oceanstar.ballduinstar.game.utils.actor.VAlign
import com.oceanstar.ballduinstar.game.utils.actor.addActorAligned
import com.oceanstar.ballduinstar.game.utils.actor.addActors
import com.oceanstar.ballduinstar.game.utils.actor.addAndFillActor
import com.oceanstar.ballduinstar.game.utils.actor.animDelay
import com.oceanstar.ballduinstar.game.utils.actor.animHide
import com.oceanstar.ballduinstar.game.utils.actor.animShow
import com.oceanstar.ballduinstar.game.utils.actor.setBounds
import com.oceanstar.ballduinstar.game.utils.actor.setOnClickListener
import com.oceanstar.ballduinstar.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.oceanstar.ballduinstar.game.utils.font.FontParameter
import com.oceanstar.ballduinstar.game.utils.gdxGame
import com.oceanstar.ballduinstar.game.utils.runGDX
import com.oceanstar.ballduinstar.util.log

class GameScreen(): AdvancedBox2dScreen(WorldUtil()) {

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(76)
    private val font   = fontGenerator_AsapCondensed_Regular.generateFont(params)

    private val aPanelGroup = ATmpGroup(this)

    private val aPanelImg  = Image(gdxGame.assetsAll.SCORE_PAN)
    private val aCountLbl  = Label("0", Label.LabelStyle(font, Color.valueOf("3D1900")))
    private val aMenuBtn   = AButton(this, AButton.Type.Menu)
    private val aStartImg  = Image(gdxGame.assetsAll.BTNS)

    // Body
    private val bPlatform = List(8) { BPlatform(this) }
    private val bCupcake  = List(4) { BCupcake(this) }
    private val bShar     = BShar(this)

    // Field
    private var counter = 0

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_GAME)
        super.show()
    }

    override fun Group.addActorsOnStageWorld() {
        createB_Platform()
        createB_Ball()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()
        addStartBtn()

        animShow()
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShow(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.CENTER)

        aPanelGroup.apply {
            addPanel()
            addMenuBtn()
        }
    }

    private fun Group.addPanel() {
        addActors(aPanelImg, aCountLbl)
        aPanelImg.setBounds(419f, 1769f, 243f, 97f)
        aCountLbl.setBounds(523f, 1774f, 36f, 87f)
        aCountLbl.setAlignment(Align.center)
    }

    private fun Group.addMenuBtn() {
        addActor(aMenuBtn)
        aMenuBtn.setBounds(25f, 1681f, 206f, 206f)
        aMenuBtn.setOnClickListener { this@GameScreen.animHide { gdxGame.navigationManager.back() } }
    }

    private fun Group.addStartBtn() {
        val aTmpGroup = ATmpGroup(this@GameScreen)
        aTmpGroup.setSize(765f, 119f)
        addActorAligned(aTmpGroup, HAlign.CENTER, VAlign.BOTTOM)
        aTmpGroup.y = 80f

        val aStart = Actor()
        val aLeft  = Actor()
        val aRight = Actor()

        aTmpGroup.addAndFillActor(aStartImg)
        aTmpGroup.addActors(aStart, aLeft, aRight)

        aStart.setBounds(414f, 0f, 351f, 119f)
        aLeft .setBounds(0f, 0f, 174f, 119f)
        aRight.setBounds(182f, 0f, 174f, 119f)

        var isStart = false
        aStart.setOnClickListener {
            isStart = true
            bShar.body!!.gravityScale = 1f
            bShar.body!!.applyTorque(1f, true)
        }
        aLeft.setOnClickListener {
            if (isStart.not()) return@setOnClickListener
            bShar.body!!.applyForceToCenter(-100f, 0f, true)
        }
        aRight.setOnClickListener {
            if (isStart.not()) return@setOnClickListener
            bShar.body!!.applyForceToCenter(100f, 0f, true)
        }
    }

    // ------------------------------------------------------------------------
    // Create Body
    // ------------------------------------------------------------------------

    private fun createB_Platform() {
        val listPos = listOf(
            Vector2(418f, 1333f),
            Vector2(67f, 1131f),
            Vector2(739f, 1104f),
            Vector2(433f, 875f),
            Vector2(37f, 709f),
            Vector2(729f, 639f),
            Vector2(433f, 437f),
            Vector2(64f, 287f),
        )
        val listPosCupcake = listPos.shuffled().take(4)
        bPlatform.onEachIndexed { index, platform ->
            platform.apply {
                id = BodyId.BORDERS
                collisionList.add(BodyId.SHAR)

                val pos = listPos[index]
                create(pos, Vector2(350f, 201f))
            }
        }

        bCupcake.onEachIndexed  { index, itr -> itr.apply {
            id = BodyId.CUPCAKE
            collisionList.add(BodyId.SHAR)

            val pos = listPosCupcake[index].add(100f, 115f)
            create(pos, Vector2(150f, 150f))
            isTransformActor = false
        } }
    }

    private fun createB_Ball() {
        bShar.apply {
            id = BodyId.SHAR
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.CUPCAKE))

            create((320..700).random().toFloat(), stageUI.height, 106f, 106f)
            body?.gravityScale = 0f

            beginContactBlockArray.add(AbstractBody.ContactBlock { body, contact ->
                when (body.id) {
                    BodyId.BORDERS -> gdxGame.soundUtil.apply { play(touch) }
                    BodyId.CUPCAKE -> {
                        gdxGame.soundUtil.apply { play(bonus) }

                        body.id = BodyId.NONE
                        body.actor?.animHide(0.2f)
                        counter++
                        aCountLbl.setText(counter)
                    }
                }
            })
            var time = 0f
            renderBlockArray.add(AbstractBody.RenderBlock {
                time += it
                if (time >= 1f) {
                    time = 0f
                    if (body!!.position.y < 0) {
                        log("destroy BALL")
                        runGDX { destroy() }

                        this@GameScreen.animHide { gdxGame.navigationManager.navigate(WinScreen::class.java.name) }
                    }
                }
            })
        }
    }

}