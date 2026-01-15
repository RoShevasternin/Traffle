package com.crystalboom.copaliny.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.MouseJoint
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.crystalboom.copaliny.game.LibGDXGame
import com.crystalboom.copaliny.game.actors.AButton
import com.crystalboom.copaliny.game.actors.AImage
import com.crystalboom.copaliny.game.box2d.AbstractBody
import com.crystalboom.copaliny.game.box2d.AbstractJoint
import com.crystalboom.copaliny.game.box2d.BodyId
import com.crystalboom.copaliny.game.box2d.WorldUtil
import com.crystalboom.copaliny.game.box2d.bodies.BBomb
import com.crystalboom.copaliny.game.box2d.bodies.BItem
import com.crystalboom.copaliny.game.box2d.bodies.BStatic
import com.crystalboom.copaliny.game.utils.*
import com.crystalboom.copaliny.game.utils.actor.animHide
import com.crystalboom.copaliny.game.utils.actor.animShow
import com.crystalboom.copaliny.game.utils.actor.setOnClickListener
import com.crystalboom.copaliny.game.utils.advanced.AdvancedBox2dScreen
import com.crystalboom.copaliny.game.utils.advanced.AdvancedStage
import com.crystalboom.copaliny.game.utils.font.FontParameter
import com.crystalboom.copaliny.util.log
import kotlinx.coroutines.*
import androidx.core.content.edit
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class GameScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private var randomItemIndex = (0..15).random()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font25        = fontGenerator_Bananas.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(49))
    private val font45        = fontGenerator_Bananas.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(69))
    private val font58        = fontGenerator_Bananas.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(69))

    private var starsCounter = game.sharedPreferences.getInt("stars", 0)
    private var bombsCounter = game.sharedPreferences.getInt("bomb", 0)

    private val imgPanelCounter = Image(game.all.GREMMY)
    private val imgBombCounter  = Actor()
    private val lblStars = Label(starsCounter.toString(), Label.LabelStyle(font45, GColor.text))
    private val lblBomb  = Label("$bombsCounter/10", Label.LabelStyle(font25, GColor.text))
    private val lblTimer = Label("00:00", Label.LabelStyle(font58, GColor.text))

    private val btnExit = AButton(this, AButton.Static.Type.Bck)

    private val imgBlures = Image(game.all.BLIUYTRE)
    private val imgItem   = Image(game.all.items[randomItemIndex])

    // Body
    private val bStatic = BStatic(this)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.bgs.random().region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgAndLbl()
                addTimer()
                addImgs()
                addImgItem()
                addExit()

                createB_Static()
            }

            repeat(5) { column ->
                repeat(5) { row ->
                    delay(50)
                    runGDX {
                        createB_Items(column)
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addExit() {
        addActor(btnExit)
        btnExit.apply {
            setBounds(27f, 1056f, 150f, 100f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgAndLbl() {
        addActors(imgPanelCounter, imgBombCounter)
        imgPanelCounter.setBounds(37f, 658f, 679f, 591f)
        imgBombCounter.setBounds(527f, 965f, 189f, 265f)
        imgBombCounter.addListener(getBombInputListener())

        addActors(lblStars, lblBomb)
        lblStars.setBounds(133f, 719f, 134f, 79f)
        lblStars.setAlignment(Align.center)
        lblBomb.setBounds(581f, 990f, 81f, 56f)
        lblBomb.setAlignment(Align.center)
    }

    private fun AdvancedStage.addTimer() {
        addActor(lblTimer)
        lblTimer.setBounds(485f, 719f, 83f, 79f)
        lblTimer.setAlignment(Align.center)

        fun timer() {
            runGDX {
                coroutine?.launch {
                    var timer  = 60
                    while (timer > 0 && isActive) {
                        delay(1000)
                        timer--
                        runGDX { lblTimer.setText("${timer}s") }
                    }
                    cancel()
                    animHideScreen {
                        game.navigationManager.navigate(StarsScreen::class.java.name)
                    }
                }
            }
        }

        timer()
    }

    private fun AdvancedStage.addImgs() {
        addActor(imgBlures)
        imgBlures.setBounds(-20f, -57f, 766f, 685f)
    }

    private fun AdvancedStage.addImgItem() {
        addActor(imgItem)
        imgItem.setBounds(273f, 1017f, 180f, 180f)
    }

    // Create Body ------------------------------------------------------------------------

    private fun createB_Static() {
        bStatic.create(-50f, -131f, 100f, 100f)
    }

    private fun createB_Items(column: Int) {
        val nx = 51f + ((column) * (22 + 107))

        val bItem = BItem(this).apply {
            id = BodyId.ITEM
            collisionList.addAll(arrayOf(BodyId.ITEM, BodyId.BOMB))
            this.column = column

            actor?.setOnClickListener {
                if (randomItemIndex == randomId) {
                    randomItemIndex = (0..15).random()
                    imgItem.drawable = TextureRegionDrawable(game.all.items[randomItemIndex])

                    game.soundUtil.apply { play(item, 1f) }

                    this.destroy()
                    createB_Items(column)
                    starsCounter += (100..150).random()
                    lblStars.setText(starsCounter)
                    game.sharedPreferences.edit { putInt("stars", starsCounter) }
                }
            }
        }
        bItem.create(nx, -134f, 107f, 107f)

        runGDX {
            AbstractJoint<PrismaticJoint, PrismaticJointDef>(this).also { j ->
                j.create(PrismaticJointDef().apply {
                    bodyA = bStatic.body
                    bodyB = bItem.body
                    localAxisA.set(0f, 1f)

                    localAnchorA.set((nx + 53.5f).toB2, 0f)
                    enableLimit = true
                    upperTranslation = 583f.toB2
                })
            }
        }
    }

    // Input Listener ------------------------------------------------------------------------

    private var jMouse = AbstractJoint<MouseJoint, MouseJointDef>(this)

    private fun getBombInputListener() = object : InputListener() {
        var hitAbstractBody: AbstractBody? = null
        val touchPointInBox = Vector2()
        val tmpVector2      = Vector2()

        val itemList = mutableListOf<BItem>()

        var isDown = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            isDown = false
            if (pointer != 0) return true
            if (bombsCounter == 0) return true

            isDown = true

            jMouse = AbstractJoint(this@GameScreen)

            bombsCounter--
            lblBomb.setText("$bombsCounter/10")
            game.sharedPreferences.edit().putInt("bomb", bombsCounter).apply()

            itemList.clear()

            touchPointInBox.set(tmpVector2.set(x, y).toB2)

            val bBomb = BBomb(this@GameScreen).also { bomb ->
                bomb.id = BodyId.BOMB
                bomb.collisionList.add(BodyId.ITEM)

                bomb.beginContactBlockArray.add(AbstractBody.ContactBlock { item ->
                    (item as? BItem)?.let {
                        (it.actor as? AImage)?.color?.a = 0.25f
                        itemList.add(it)
                        log("begin: ${itemList.joinToString()}")
                    }
                })
                bomb.endContactBlockArray.add(AbstractBody.ContactBlock { item ->
                    (item as? BItem)?.let {
                        (it.actor as? AImage)?.color?.a = 1f
                        itemList.remove(it)
                        log("end: ${itemList.joinToString()}")
                    }
                })
            }
            bBomb.create(527+x-100f, 965+y-100f, 189f, 189f)

            hitAbstractBody = bBomb

            hitAbstractBody?.let {
                jMouse.create(MouseJointDef().apply {
                    bodyA = bStatic.body
                    bodyB = it.body
                    collideConnected = true

                    target.set(touchPointInBox)

                    maxForce     = 1000f
                    frequencyHz  = 5.0f
                    dampingRatio = 0.7f
                })
            }

            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            if (pointer != 0) return
            if (!isDown) return

            jMouse.joint?.target = tmpVector2.set(x, y).toB2
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (pointer != 0) return
            if (!isDown) return

            hitAbstractBody?.let {
                jMouse.destroy()
            }

            coroutine?.launch {
                runGDX {
                    hitAbstractBody?.run {
                        beginContactBlockArray.clear()
                        endContactBlockArray.clear()
                    }
                }

                val columns = itemList.map { it.column }
                launch {
                    itemList.onEach {
                        runGDX { it.destroy() }
                    }
                }
                launch {
                    columns.onEach {
                        runGDX { createB_Items(it) }
                        delay(100)
                    }
                }

                runGDX {
                    game.soundUtil.apply { play(soft_explosion_bomb, 1f) }

                    hitAbstractBody?.destroy()

                    starsCounter += (100..200).random()
                    lblStars.setText(starsCounter)
                    game.sharedPreferences.edit().putInt("stars", starsCounter).apply()
                }
            }
        }

    }

}