package com.fushflyacensee.adventcoral.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.fushflyacensee.adventcoral.game.actors.ATmpGroup
import com.fushflyacensee.adventcoral.game.actors.button.AButton
import com.fushflyacensee.adventcoral.game.actors.checkbox.ACheckBox
import com.fushflyacensee.adventcoral.game.box2d.AbstractBody
import com.fushflyacensee.adventcoral.game.box2d.BodyId
import com.fushflyacensee.adventcoral.game.box2d.WorldUtil
import com.fushflyacensee.adventcoral.game.box2d.bodies.BTop
import com.fushflyacensee.adventcoral.game.box2d.bodies.BBabka
import com.fushflyacensee.adventcoral.game.box2d.bodies.BBot
import com.fushflyacensee.adventcoral.game.utils.Block
import com.fushflyacensee.adventcoral.game.utils.TIME_ANIM_SCREEN
import com.fushflyacensee.adventcoral.game.utils.actor.addActorWithConstraints
import com.fushflyacensee.adventcoral.game.utils.actor.addActors
import com.fushflyacensee.adventcoral.game.utils.actor.animDelay
import com.fushflyacensee.adventcoral.game.utils.actor.animHide
import com.fushflyacensee.adventcoral.game.utils.actor.animShow
import com.fushflyacensee.adventcoral.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.fushflyacensee.adventcoral.game.utils.font.FontParameter
import com.fushflyacensee.adventcoral.game.utils.gdxGame
import com.fushflyacensee.adventcoral.game.utils.scaledToUI
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

var GDX_counter = 0
    private set

class GameScreen(): AdvancedBox2dScreen(WorldUtil()) {

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(77)
    private val font   = fontGenerator_GochiHand_Regular.generateFont(params)

    private val aPanelGroup = ATmpGroup(this)
    private val aPanelImg   = Image(gdxGame.assetsAll.PANEL_SCORE)
    private val aCountLbl   = Label("0", Label.LabelStyle(font, Color.WHITE))
    private val aMenuBtn    = AButton(this, AButton.Type.Back)
    private val aPauseBox   = ACheckBox(this, ACheckBox.Type.PAUSE)

    // Body
    private val bBabka = BBabka(this)

    // Field
    private val pipeList      = mutableListOf<AbstractBody>()

    override fun show() {
        GDX_counter = 0

        setBackBackground(gdxGame.assetsAll.BACKGROUND_GAME)
        super.show()
    }

    override fun Group.addActorsOnStageWorld() {
        createB_Babka()

        startPipeSpawner()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()
        addMenuBtn()
        addPauseBox()

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

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        flap()
        gdxGame.soundUtil.apply { play(fly) }
        return false
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(433f, 208f)
        addActorWithConstraints(aPanelGroup) {
            topToTopOf     = aPauseBox
            startToStartOf = this@addPanelGroup
            endToEndOf     = this@addPanelGroup
        }

        aPanelGroup.apply {
            addActors(aPanelImg, aCountLbl)
        }

        aPanelImg.setBounds(0f, 93f, 433f, 115f)
        aCountLbl.setBounds(178f, 104f, 76f, 92f)
        aCountLbl.setAlignment(Align.center)
    }

    private fun Group.addPauseBox() {
        aPauseBox.setSize(130f, 130f)
        addActorWithConstraints(aPauseBox) {
            endToEndOf     = this@addPauseBox
            topToTopOf     = this@addPauseBox

            marginEnd = 142f
            marginTop = 55f
        }
        aPauseBox.setOnCheckListener { isWorldPause = it }
    }

    private fun Group.addMenuBtn() {
        aMenuBtn.setSize(130f, 130f)
        addActorWithConstraints(aMenuBtn) {
            startToStartOf = this@addMenuBtn
            topToTopOf     = this@addMenuBtn

            marginStart = 142f
            marginTop   = 55f
        }
        aMenuBtn.setOnClickListener { this@GameScreen.animHide { gdxGame.navigationManager.back() } }
    }


    // ------------------------------------------------------------------------
    // Create Body
    // ------------------------------------------------------------------------

    private fun createB_Babka() {
        bBabka.apply {
            id = BodyId.BABA

            create(81f, stageWorld.height / 2f, 291f, 225f)

            body?.apply {
                gravityScale = 1f
                isBullet = true
            }

            collisionList.add(BodyId.PIPE)

            beginContactBlockArray.add(AbstractBody.ContactBlock { body, _ ->
                if (body.id == BodyId.PIPE) {
                    gdxGame.soundUtil.apply { play(fail) }
                    this@GameScreen.animHide { gdxGame.navigationManager.navigate(ResultScreen::class.java.name) }
                }
            })
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun flap() {
        bBabka.body?.apply {
            linearVelocity = Vector2(linearVelocity.x, 0f) // скидаємо падіння
            applyLinearImpulse(
                Vector2(0f, 2f),              // сила вверх
                worldCenter,
                true
            )
        }
    }

    private fun spawnPipePair() {
        val gapSize = 300f
        val minY = 200f
        val maxY = 800f
        val gapCenterY = MathUtils.random(minY, maxY)

        val startX = stageWorld.width + 200f

        val bottom = BBot(this).apply {
            id = BodyId.PIPE
            val ny = gapCenterY - ((gapSize / 2f) + 774f)
            create(startX, ny, 535f, 774f)
            body?.linearVelocity = Vector2(-3f, 0f)
            collisionList.add(BodyId.BABA)
        }

        val top = BTop(this).apply {
            id = BodyId.PIPE
            val ny = gapCenterY + (gapSize / 2f)
            create(startX, ny, 582f, 774f)
            body?.linearVelocity = Vector2(-3f, 0f)
            collisionList.add(BodyId.BABA)
        }

        pipeList.add(top)
        pipeList.add(bottom)
    }

    private var pipeTimer = 0f

    private fun startPipeSpawner() {
        bBabka.renderBlockArray.add(AbstractBody.RenderBlock { delta ->
            pipeTimer += delta

            if (pipeTimer >= 2f) {
                pipeTimer = 0f
                spawnPipePair()

                if (bBabka.body!!.position.y <= 0f) {
                    gdxGame.soundUtil.apply { play(fail) }
                    this.animHide { gdxGame.navigationManager.navigate(ResultScreen::class.java.name) }
                }

                // 🔥 Очищення труб
                val iterator = pipeList.iterator()
                while (iterator.hasNext()) {
                    val pipe = iterator.next()
                    val x = pipe.body?.position?.x ?: continue

                    if (x.scaledToUI < -300f) {
                        pipe.destroy()
                        iterator.remove()
                    }
                }

            }

            pipeList.forEach { pipe ->
                if (pipe is BBot) {
                    if (!pipe.isCounted && pipe.body!!.position.x < bBabka.body!!.position.x) {
                        GDX_counter++
                        aCountLbl.setText(GDX_counter)
                        pipe.isCounted = true
                        gdxGame.soundUtil.apply { play(bonus) }
                    }
                }
            }

            // Горизонтальний рух бабки
            bBabka.body?.let { body ->
                val currentX = body.position.x.scaledToUI

                if (moveRight && currentX >= maxX) {
                    moveRight = false
                } else if (!moveRight && currentX <= minX) {
                    moveRight = true
                }

                val vx = if (moveRight) speedX else -speedX
                body.linearVelocity = Vector2(vx, body.linearVelocity.y)
            }
        })
    }

    private var moveRight = true
    private val speedX = 2.5f
    private val minX = 500f
    private val maxX = 1500f

}