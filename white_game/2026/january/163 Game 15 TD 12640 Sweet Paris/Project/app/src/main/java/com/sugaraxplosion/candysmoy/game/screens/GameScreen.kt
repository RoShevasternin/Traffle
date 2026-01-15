package com.sugaraxplosion.candysmoy.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.QueryCallback
import com.badlogic.gdx.physics.box2d.joints.MouseJoint
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.sugaraxplosion.candysmoy.game.LibGDXGame
import com.sugaraxplosion.candysmoy.game.actors.AButton
import com.sugaraxplosion.candysmoy.game.actors.AImage
import com.sugaraxplosion.candysmoy.game.box2d.AbstractBody
import com.sugaraxplosion.candysmoy.game.box2d.AbstractJoint
import com.sugaraxplosion.candysmoy.game.box2d.BodyId
import com.sugaraxplosion.candysmoy.game.box2d.WorldUtil
import com.sugaraxplosion.candysmoy.game.box2d.bodies.BBomb
import com.sugaraxplosion.candysmoy.game.box2d.bodies.BItem
import com.sugaraxplosion.candysmoy.game.box2d.bodies.BStatic
import com.sugaraxplosion.candysmoy.game.utils.*
import com.sugaraxplosion.candysmoy.game.utils.actor.animHide
import com.sugaraxplosion.candysmoy.game.utils.actor.animShow
import com.sugaraxplosion.candysmoy.game.utils.actor.setOnClickListener
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedBox2dScreen
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedStage
import com.sugaraxplosion.candysmoy.game.utils.font.FontParameter
import com.sugaraxplosion.candysmoy.util.log
import kotlinx.coroutines.*
import androidx.core.content.edit

class GameScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private var randomItemIndex = (0..14).random()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font25        = fontGenerator_Rowdies.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(25))
    private val font45        = fontGenerator_Rowdies.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(45))
    private val font58        = fontGenerator_Rowdies.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(58))

    private var starsCounter = game.sharedPreferences.getInt("stars", 0)
    private var bombsCounter = game.sharedPreferences.getInt("bomb", 0)

    private val imgPanelCounter = Image(game.all.counter_pan)
    private val imgBombCounter  = Image(game.all.bomb_counter)
    private val lblStars = Label(starsCounter.toString(), Label.LabelStyle(font45, GColor.text))
    private val lblBomb  = Label(bombsCounter.toString(), Label.LabelStyle(font25, GColor.text))
    private val lblTimer = Label("00:00", Label.LabelStyle(font58, GColor.text))

    private val btnExit = AButton(this, AButton.Static.Type.Exit)
    private val btnPita = AButton(this, AButton.Static.Type.Pitanie)

    private val imgBlures = Image(game.all.BLURES)
    private val imgPanel  = Image(game.all.panel)
    private val imgItem   = Image(game.all.sweets[randomItemIndex])

    // Body
    private val bStatic = BStatic(this)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.VAFLA.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addExit()
                addImgAndLbl()
                addTimer()
                addImgs()
                addImgItem()

                createB_Static()
            }

            repeat(4) { column ->
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
            setBounds(42f, 851f, 70f, 79f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.back()
                }
            }
        }

        addActor(btnPita)
        btnPita.apply {
            setBounds(42f, 751f, 71f, 78f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(PitanieScreen::class.java.name, GameScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addImgAndLbl() {
        addActors(imgPanelCounter, imgBombCounter)
        imgPanelCounter.setBounds(32f, 635f, 480f, 78f)
        imgBombCounter.setBounds(403f, 752f, 114f, 150f)
        imgBombCounter.addListener(getBombInputListener())

        addActors(lblStars, lblBomb)
        lblStars.setBounds(91f, 646f, 135f, 56f)
        lblStars.setAlignment(Align.center)
        lblBomb.setBounds(450f, 754f, 24f, 31f)
        lblBomb.setAlignment(Align.center)
    }

    private fun AdvancedStage.addTimer() {
        addActor(lblTimer)
        lblTimer.setBounds(329f, 638f, 156f, 72f)
        lblTimer.setAlignment(Align.center)

        fun timer() {
            runGDX {
                coroutine?.launch {
                    var timer  = 30
                    var second = 5
                    while (timer > 0 && isActive) {
                        delay(200)
                        second--
                        runGDX { lblTimer.setText("${timer}:${second}0") }
                        if (second == 0) {
                            second = 5
                            timer--
                            runGDX { lblTimer.setText("$timer:${second}0") }
                        }
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
        addActors(imgBlures, imgPanel)
        imgBlures.setBounds(0f, 0f, 540f, 608f)
        imgPanel.setBounds(159f, 741f, 222f, 199f)
    }

    private fun AdvancedStage.addImgItem() {
        addActor(imgItem)
        imgItem.setBounds(215f, 785f, 110f, 110f)
    }

    // Create Body ------------------------------------------------------------------------

    private fun createB_Static() {
        bStatic.create(-50f, -105f, 100f, 100f)
    }

    private fun createB_Items(column: Int) {
        val nx = 27f + ((column) * (15 + 110))

        val bItem = BItem(this).apply {
            id = BodyId.ITEM
            collisionList.addAll(arrayOf(BodyId.ITEM, BodyId.BOMB))
            this.column = column

            actor?.setOnClickListener {
                if (randomItemIndex == randomId) {
                    randomItemIndex = (0..14).random()
                    imgItem.drawable = TextureRegionDrawable(game.all.sweets[randomItemIndex])

                    game.soundUtil.apply { play(item, 1f) }

                    this.destroy()
                    createB_Items(column)
                    starsCounter += (10..100).random()
                    lblStars.setText(starsCounter)
                    game.sharedPreferences.edit { putInt("stars", starsCounter) }
                }
            }
        }
        bItem.create(nx, -110f, 110f, 110f)

        AbstractJoint<PrismaticJoint, PrismaticJointDef>(this).also { j ->
            j.create(PrismaticJointDef().apply {
                bodyA = bStatic.body
                bodyB = bItem.body
                localAxisA.set(0f, 1f)

                localAnchorA.set((nx+55).toB2, 0f)
                enableLimit      = true
                upperTranslation = 577f.toB2
            })
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

            jMouse = AbstractJoint<MouseJoint, MouseJointDef>(this@GameScreen)

            bombsCounter--
            lblBomb.setText(bombsCounter)
            game.sharedPreferences.edit().putInt("bomb", bombsCounter).apply()

            itemList.clear()

            touchPointInBox.set(tmpVector2.set(x, y).toB2)

            val bBomb = BBomb(this@GameScreen).also { bomb ->
                bomb.id = BodyId.BOMB
                bomb.collisionList.add(BodyId.ITEM)

                bomb.beginContactBlockArray.add(AbstractBody.ContactBlock { item ->
                    (item as? BItem)?.let {
                        (it.actor as? AImage)?.drawable = TextureRegionDrawable(game.all.sweets_gray[it.randomId])
                        itemList.add(it)
                        log("begin: ${itemList.joinToString()}")
                    }
                })
                bomb.endContactBlockArray.add(AbstractBody.ContactBlock { item ->
                    (item as? BItem)?.let {
                        (it.actor as? AImage)?.drawable = TextureRegionDrawable(game.all.sweets[it.randomId])
                        itemList.remove(it)
                        log("end: ${itemList.joinToString()}")
                    }
                })
            }
            bBomb.create(403+x-57f, 752+y-57f, 114f, 114f)

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