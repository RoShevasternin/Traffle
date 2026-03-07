package com.candybostony.bonceria.game.screens

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.candybostony.bonceria.game.actors.AImage
import com.candybostony.bonceria.game.actors.ATimer
import com.candybostony.bonceria.game.actors.ATmpGroup
import com.candybostony.bonceria.game.actors.button.AButton
import com.candybostony.bonceria.game.box2d.BodyId
import com.candybostony.bonceria.game.box2d.bodies.BBall
import com.candybostony.bonceria.game.box2d.bodies.BGreener
import com.candybostony.bonceria.game.box2d.bodies.BHor
import com.candybostony.bonceria.game.box2d.bodies.BStar
import com.candybostony.bonceria.game.box2d.bodies.BVer
import com.candybostony.bonceria.game.utils.Block
import com.candybostony.bonceria.game.utils.GameColor
import com.candybostony.bonceria.game.utils.HEIGHT_UI
import com.candybostony.bonceria.game.utils.TIME_ANIM_SCREEN
import com.candybostony.bonceria.game.utils.WIDTH_UI
import com.candybostony.bonceria.game.utils.actor.HAlign
import com.candybostony.bonceria.game.utils.actor.VAlign
import com.candybostony.bonceria.game.utils.actor.addActorAligned
import com.candybostony.bonceria.game.utils.actor.addActorWithConstraints
import com.candybostony.bonceria.game.utils.actor.addActors
import com.candybostony.bonceria.game.utils.actor.addAndFillActor
import com.candybostony.bonceria.game.utils.actor.animDelay
import com.candybostony.bonceria.game.utils.actor.animHide
import com.candybostony.bonceria.game.utils.actor.animShow
import com.candybostony.bonceria.game.utils.actor.setBounds
import com.candybostony.bonceria.game.utils.actor.setOnClickListener
import com.candybostony.bonceria.game.utils.advanced.box2d.AdvancedBox2dUserScreen
import com.candybostony.bonceria.game.utils.font.FontParameter
import com.candybostony.bonceria.game.utils.gdxGame

class GameScreen(): AdvancedBox2dUserScreen() {

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "/:").setSize(50)
    private val font   = fontGenerator_Kadwa_Bold.generateFont(params)

    private val aHomeBtn      = AButton(this, AButton.Type.Home)
    private val aPanelGroup   = ATmpGroup(this)
    private val aControlGroup = ATmpGroup(this)
    private val aGreener      = Image(gdxGame.assetsAll.GREENER)
    private val aCountLbl     = Label("0/3", Label.LabelStyle(font, GameColor.green))
    private val timer         = ATimer(this, font)

    // Body
    private val bBall = BBall(this)
    private val bG1   = BGreener(this)
    private val bG2   = BGreener(this)

    private val listBStar = List(3) { BStar(this) }

    private var counterStar = 0

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageWorld() {
        addActor(aGreener)
        aGreener.setBounds(-424f, -313f, 2782f, 970f)

        createBG_Borders()
        createB_Ball()
        createB_Greener()
        createB_Star()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f
        addPanelGroup()
        addBtnMenu()
        addControlGroup()

        animShow { timer.startTimer(60) }
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addBtnMenu() {
        aHomeBtn.setSize(147f, 147f)
        addActorWithConstraints(aHomeBtn) {
            startToStartOf = this@addBtnMenu
            topToTopOf     = this@addBtnMenu

            marginStart = 64f
            marginTop   = 27f
        }
        aHomeBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(604f, 104f)
        addActorWithConstraints(aPanelGroup) {
            topToTopOf     = this@addPanelGroup
            startToStartOf = this@addPanelGroup
            endToEndOf     = this@addPanelGroup

            marginTop = 60f
        }

        val aPanelImg = Image(gdxGame.assetsAll.PANEL)

        aPanelGroup.apply {
            addAndFillActor(aPanelImg)
            addActor(aCountLbl)
            addActor(timer)
        }

        aCountLbl.setBounds(457f, 19f, 99f, 65f)
        aCountLbl.setAlignment(Align.center)

        timer.setBounds(66f, 19f, 154f, 65f)
        timer.finishBlock = {
            GDX_isWin = false
            animHideScreen { gdxGame.navigationManager.navigate(ResultScreen::class.java.name) }
        }
    }

    private fun Group.addControlGroup() {
        aControlGroup.setSize(318f, 147f)
        addActorWithConstraints(aControlGroup) {
            startToStartOf   = this@addControlGroup
            endToEndOf       = this@addControlGroup
            bottomToBottomOf = this@addControlGroup

            marginStart  = 83f
            marginBottom = 75f
        }

        val aLeftRightImg = Image(gdxGame.assetsAll.LEFT_RIGHT)
        val aLeft  = Actor()
        val aRight = Actor()
        aControlGroup.apply {
            addAndFillActor(aLeftRightImg)
            addActors(aLeft, aRight)
        }

        aLeft.setBounds(0f, 0f, 147f, 147f)
        aRight.setBounds(184f, 0f, 147f, 147f)

        val left  = Vector2(-40f, 35f)
        val right = Vector2(40f, 35f)

        aLeft.setOnClickListener { bBall.body!!.applyLinearImpulse(left, bBall.body!!.worldCenter, true) }
        aRight.setOnClickListener { bBall.body!!.applyLinearImpulse(right, bBall.body!!.worldCenter, true) }
    }


    // ------------------------------------------------------------------------
    // Create Body
    // ------------------------------------------------------------------------

    private fun createBG_Borders() {
//        (viewportDebug.camera as OrthographicCamera).apply {
//            zoom = 4f
//            update()
//        }
        val bH1 = BHor(this)
        val bH2 = BHor(this)

        bH1.create(0f, -50f, 3933f, 50f)
        bH2.create(0f, stageWorld.height, 3933f, 50f)

        val bV1 = BVer(this)
        val bV2 = BVer(this)

        bV1.create(-50f, -50f, 50f, 2111f)
        bV2.create(stageWorld.width, -50f, 50f, 2111f)

        listOf(bH1, bH2, bV1, bV2).forEach {
            it.id = BodyId.BORDER
            it.collisionList.add(BodyId.BALL)
        }
    }

    private fun createB_Ball() {
        bBall.create(WIDTH_UI / 2f - 117f, HEIGHT_UI / 2f - 117f, 234f, 234f)
        bBall.id = BodyId.BALL
        bBall.collisionList.addAll(arrayOf(BodyId.BORDER, BodyId.STAR))

        bBall.beginContactBlockArray.add { enemy, _ ->
            if (enemy.id == BodyId.STAR) {
                gdxGame.soundUtil.apply { play(bonus) }
                enemy.destroy()
                counterStar++
                aCountLbl.setText("$counterStar/3")
                if (counterStar >= 3) {
                    GDX_isWin = true
                    animHideScreen { gdxGame.navigationManager.navigate(ResultScreen::class.java.name) }
                }
            }
        }
    }

    private fun createB_Greener() {
        bG1.create(-26f, -188f, 659f, 659f)
        bG2.create(1360f, -225f, 659f, 659f)
    }

    private fun createB_Star() {
        val listRECT = listOf(
            Rectangle(211f, 570f, 82f, 82f),
            Rectangle(352f, 771f, 158f, 158f),
            Rectangle(885f, 745f, 140f, 140f),
            Rectangle(1810f, 575f, 82f, 82f),
            Rectangle(1396f, 796f, 195f, 195f),
            Rectangle(1288f, 608f, 82f, 82f),
            Rectangle(522f, 553f, 93f, 96f),
            Rectangle(61f, 803f, 38f, 38f),
        ).shuffled()

        repeat(3) { index ->
            val rect = listRECT[index]
            listBStar[index].create(rect.x, rect.y, rect.width, rect.height)
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------


}