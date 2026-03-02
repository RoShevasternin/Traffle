package com.jellymp.jumpem.game.screens

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.jellymp.jumpem.game.actors.AImage
import com.jellymp.jumpem.game.actors.ATimer
import com.jellymp.jumpem.game.actors.ATmpGroup
import com.jellymp.jumpem.game.actors.button.AButton
import com.jellymp.jumpem.game.box2d.BodyId
import com.jellymp.jumpem.game.box2d.bodies.BBall
import com.jellymp.jumpem.game.box2d.bodies.BGreener
import com.jellymp.jumpem.game.box2d.bodies.BHor
import com.jellymp.jumpem.game.box2d.bodies.BStar
import com.jellymp.jumpem.game.box2d.bodies.BVer
import com.jellymp.jumpem.game.utils.Block
import com.jellymp.jumpem.game.utils.GameColor
import com.jellymp.jumpem.game.utils.HEIGHT_UI
import com.jellymp.jumpem.game.utils.TIME_ANIM_SCREEN
import com.jellymp.jumpem.game.utils.WIDTH_UI
import com.jellymp.jumpem.game.utils.actor.addActorWithConstraints
import com.jellymp.jumpem.game.utils.actor.addActors
import com.jellymp.jumpem.game.utils.actor.addAndFillActor
import com.jellymp.jumpem.game.utils.actor.animDelay
import com.jellymp.jumpem.game.utils.actor.animHide
import com.jellymp.jumpem.game.utils.actor.animShow
import com.jellymp.jumpem.game.utils.actor.setOnClickListener
import com.jellymp.jumpem.game.utils.advanced.box2d.AdvancedBox2dUserScreen
import com.jellymp.jumpem.game.utils.font.FontParameter
import com.jellymp.jumpem.game.utils.gdxGame

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
        addActor(aGreener)
        aGreener.setBounds(676f, -144f, 1367f, 536f)

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
        aHomeBtn.setSize(160f, 160f)
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
        aControlGroup.setSize(344f, 160f)
        addActorWithConstraints(aControlGroup) {
            startToStartOf   = this@addControlGroup
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

        aLeft.setBounds(0f, 0f, 160f, 160f)
        aRight.setBounds(184f, 0f, 160f, 160f)

        val left  = Vector2(-50f, 30f)
        val right = Vector2(50f, 30f)

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
        bG1.create(724f, -313f, 526f, 526f)
        bG2.create(1384f, -186f, 526f, 526f)
    }

    private fun createB_Star() {
        val listRECT = listOf(
            Rectangle(352f, 771f, 158f, 158f),
            Rectangle(522f, 553f, 93f, 96f),
            Rectangle(1396f, 796f, 195f, 195f),
            Rectangle(885f, 745f, 140f, 140f),
            Rectangle(1314f, 369f, 82f, 82f),
            Rectangle(1810f, 575f, 82f, 82f),
            Rectangle(59f, 319f, 82f, 82f),
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