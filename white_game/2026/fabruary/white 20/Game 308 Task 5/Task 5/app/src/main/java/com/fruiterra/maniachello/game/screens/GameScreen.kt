package com.fruiterra.maniachello.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.fruiterra.maniachello.game.actors.ATmpGroup
import com.fruiterra.maniachello.game.actors.button.AButton
import com.fruiterra.maniachello.game.box2d.BodyId
import com.fruiterra.maniachello.game.box2d.bodies.BFruit
import com.fruiterra.maniachello.game.box2d.bodies.BHor
import com.fruiterra.maniachello.game.utils.Block
import com.fruiterra.maniachello.game.utils.HEIGHT_UI
import com.fruiterra.maniachello.game.utils.TIME_ANIM_SCREEN
import com.fruiterra.maniachello.game.utils.actor.addActorWithConstraints
import com.fruiterra.maniachello.game.utils.actor.addAndFillActor
import com.fruiterra.maniachello.game.utils.actor.animDelay
import com.fruiterra.maniachello.game.utils.actor.animHide
import com.fruiterra.maniachello.game.utils.actor.animShow
import com.fruiterra.maniachello.game.utils.advanced.box2d.AdvancedBox2dUserScreen
import com.fruiterra.maniachello.game.utils.font.FontParameter
import com.fruiterra.maniachello.game.utils.gdxGame
import com.fruiterra.maniachello.util.log

class GameScreen(): AdvancedBox2dUserScreen() {

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "/").setSize(35)
    private val font   = fontGenerator_Kadwa_Bold.generateFont(params)

    private val aPanelGroup = ATmpGroup(this)
    private val aPanelImg   = Image(gdxGame.assetsAll.PANEL)
    private val aCountLbl   = Label("0 / 100", Label.LabelStyle(font, Color.WHITE))
    private val aMenuBtn    = AButton(this, AButton.Type.Back)

    // Body
    private val bBorders = BHor(this)

    private val fruitList  = mutableListOf<BFruit>()
    private var spawnTimer = 0f

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_GAME)
        super.show()
    }


    override fun render(delta: Float) {
        super.render(delta)

        spawnTimer += delta
        if (spawnTimer >= 1f) {
            spawnTimer = 0f
            spawnFruit()
        }
    }

    override fun Group.addActorsOnStageWorld() {
        createBG_Borders()

        spawnFruit()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()
        addMenuBtn()

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

    private fun Group.addMenuBtn() {
        aMenuBtn.setSize(87f, 87f)
        addActorWithConstraints(aMenuBtn) {
            startToStartOf = this@addMenuBtn
            topToTopOf     = this@addMenuBtn

            marginStart = 66f
            marginTop   = 53f
        }
        aMenuBtn.setOnClickListener { this@GameScreen.animHide { gdxGame.navigationManager.back() } }
    }

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(249f, 88f)
        addActorWithConstraints(aPanelGroup) {
            topToTopOf     = this@addPanelGroup
            startToStartOf = this@addPanelGroup
            endToEndOf     = this@addPanelGroup

            marginTop = 48f
        }

        aPanelGroup.apply {
            addAndFillActor(aPanelImg)
            addActor(aCountLbl)
        }

        aCountLbl.setBounds(62f, 9f, 122f, 71f)
        aCountLbl.setAlignment(Align.center)
    }


    // ------------------------------------------------------------------------
    // Create Body
    // ------------------------------------------------------------------------

    private fun createBG_Borders() {
        bBorders.create(-508f, 0f, 2577f, 50f)
        bBorders.id = BodyId.BORDER
        bBorders.collisionList.add(BodyId.FRUIT)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun spawnFruit() {

        val type = (0..9).random()

        val worldW = viewportWorld.worldWidth

        val fruit = BFruit(this, type).apply {

            id = BodyId.FRUIT
            collisionList.addAll(arrayOf(BodyId.FRUIT, BodyId.BORDER))

            val size    = 125f
            val randomX = MathUtils.random(size, (worldW - size))

            create(
                randomX,
                HEIGHT_UI - size,
                size,
                size
            )

            body?.gravityScale = 1f

            setupStickyLogic(this)
        }

        fruitList.add(fruit)
    }

    private fun setupStickyLogic(fruit: BFruit) {

        fruit.beginContactBlockArray.add { other, _ ->

            if (other is BFruit &&
                other.fruitType == fruit.fruitType &&
                other !in fruit.connected
            ) {
                stageWorld.root.animDelay(0.1f) {
                    stickFruits(fruit, other)
                    gdxGame.soundUtil.apply { play(bonus) }
                }
            }
        }
    }

    private fun stickFruits(a: BFruit, b: BFruit) {

        if (a.connected.contains(b)) return

        val jointDef = WeldJointDef().apply {
            bodyA = a.body
            bodyB = b.body
            collideConnected = false
            localAnchorA.setZero()
            localAnchorB.setZero()
        }

        counter += 10
        aCountLbl.setText(counter)

        if (counter >= 100) {
            gdxGame.navigationManager.navigate(ResultScreen::class.java.name)
        }

        worldUtil.world.createJoint(jointDef)

        a.connected.add(b)
        b.connected.add(a)
    }

    private var counter = 0

}