package com.wintergroup.cupcakejump.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.wintergroup.cupcakejump.game.actors.ATmpGroup
import com.wintergroup.cupcakejump.game.actors.button.AButton
import com.wintergroup.cupcakejump.game.actors.checkbox.ACheckBox
import com.wintergroup.cupcakejump.game.box2d.AbstractBody
import com.wintergroup.cupcakejump.game.box2d.BodyId
import com.wintergroup.cupcakejump.game.box2d.WorldUtil
import com.wintergroup.cupcakejump.game.box2d.bodies.BCupcake
import com.wintergroup.cupcakejump.game.box2d.bodies.BPlatform
import com.wintergroup.cupcakejump.game.box2d.bodies.BShar
import com.wintergroup.cupcakejump.game.utils.Block
import com.wintergroup.cupcakejump.game.utils.HEIGHT_UI
import com.wintergroup.cupcakejump.game.utils.TIME_ANIM_SCREEN
import com.wintergroup.cupcakejump.game.utils.WIDTH_UI
import com.wintergroup.cupcakejump.game.utils.actor.HAlign
import com.wintergroup.cupcakejump.game.utils.actor.VAlign
import com.wintergroup.cupcakejump.game.utils.actor.addActorAligned
import com.wintergroup.cupcakejump.game.utils.actor.addActors
import com.wintergroup.cupcakejump.game.utils.actor.animDelay
import com.wintergroup.cupcakejump.game.utils.actor.animHide
import com.wintergroup.cupcakejump.game.utils.actor.animShow
import com.wintergroup.cupcakejump.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.wintergroup.cupcakejump.game.utils.font.FontParameter
import com.wintergroup.cupcakejump.game.utils.gdxGame
import com.wintergroup.cupcakejump.game.utils.runGDX
import com.wintergroup.cupcakejump.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameScreen(): AdvancedBox2dScreen(WorldUtil()) {

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(82).setBorder(2f, Color.valueOf("FF439A"))
    private val font   = fontGenerator_AsapCondensed_Regular.generateFont(params)

    private val aPanelGroup = ATmpGroup(this)

    private val aPanelImg  = Image(gdxGame.assetsAll.panel)
    private val aCountLbl  = Label("0", Label.LabelStyle(font, Color.valueOf("FFEE00")))
    private val aMenuBtn   = AButton(this, AButton.Type.Menu)
    private val aPauseBox  = ACheckBox(this, ACheckBox.Type.PAUSE)
    private val aStartBtn  = AButton(this, AButton.Type.Start)

    // Body
    private val bPlatform = List(8) { BPlatform(this) }
    private val bCupcake  = List(4) { BCupcake(this) }
    private val bShar     = BShar(this)

    // Field
    private var counter = 0

    override fun show() {
        setBackBackground(gdxGame.assetsAll.GAME)
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
        //stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.TOP)

        aPanelGroup.apply {
            addPanel()
            addMenuBtn()
            addPauseBox()
        }
    }

    private fun Group.addPanel() {
        addActors(aPanelImg, aCountLbl)
        aPanelImg.setBounds(329f, 1613f, 422f, 161f)
        aCountLbl.setBounds(472f, 1662f, 135f, 94f)
        aCountLbl.setAlignment(Align.center)
    }

    private fun Group.addMenuBtn() {
        addActor(aMenuBtn)
        aMenuBtn.setBounds(64f, 1673f, 180f, 183f)
        aMenuBtn.setOnClickListener { this@GameScreen.animHide { gdxGame.navigationManager.back() } }
    }

    private fun Group.addStartBtn() {
        aStartBtn.setSize(341f, 130f)
        addActorAligned(aStartBtn, HAlign.CENTER, VAlign.BOTTOM)
        aStartBtn.y = 53f

        aStartBtn.setOnClickListener {
            bShar.body!!.gravityScale = 1f
            bShar.body!!.applyTorque(1f, true)
        }
    }

    private fun Group.addPauseBox() {
        addActor(aPauseBox)
        aPauseBox.setBounds(852f, 1673f, 180f, 183f)
        aPauseBox.setOnCheckListener { isWorldPause = it }
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

                        this@GameScreen.animHide { gdxGame.navigationManager.navigate(ResultScreen::class.java.name) }
                    }
                }
            })
        }
    }

}