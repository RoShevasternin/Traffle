package com.royaltombsecrets.miniquizler.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.royaltombsecrets.miniquizler.game.LibGDXGame
import com.royaltombsecrets.miniquizler.game.actors.AButton
import com.royaltombsecrets.miniquizler.game.box2d.AbstractBody
import com.royaltombsecrets.miniquizler.game.box2d.WorldUtil
import com.royaltombsecrets.miniquizler.game.box2d.bodies.BItem
import com.royaltombsecrets.miniquizler.game.utils.*
import com.royaltombsecrets.miniquizler.game.utils.actor.animHide
import com.royaltombsecrets.miniquizler.game.utils.actor.animShow
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedBox2dScreen
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class CollectionScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val btnMenu = AButton(this, AButton.Static.Type.Menu)
    private val imgList = List(9) { Image(game.all.grays[it]) }

    private val answersCount = game.sharedPreferences.getInt("Answers", 0)

    // Body
    private val bItemList = List(10) { BItem(this) }

    // Field
    private val bItemFlow = MutableSharedFlow<BItem>(10)


    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Items()
        addMenu()
        addAnswers()
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(1327f, 37f, 138f, 84f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addAnswers() {

        val imgCount = when {
            answersCount < 1 -> 0
            answersCount < 5 -> 1
            answersCount < 8 -> 2
            answersCount < 10 -> 3
            answersCount < 13 -> 4
            answersCount < 15 -> 5
            answersCount < 16 -> 6
            answersCount < 18 -> 7
            answersCount < 20 -> 8
            else -> 9
        }
        imgList.take(imgCount).onEachIndexed { index, image ->
            image.drawable = TextureRegionDrawable(game.all.colls[index])
        }

        val posList = listOf(
            Vector2(164f, 487f),
            Vector2(495f, 587f),
            Vector2(826f, 587f),
            Vector2(1157f, 487f),
            Vector2(164f, 193f),
            Vector2(495f, 91f),
            Vector2(826f, 91f),
            Vector2(1157f, 193f),
            Vector2(660f, 350f),
        )
        imgList.onEachIndexed { index, image ->
            addActor(image)
            image.setBounds(posList[index].x, posList[index].y, 200f, 200f)
        }
    }

    // Create Body ------------------------------------------------------------------------

    private fun createB_Items() {
        bItemList.onEach { bItem ->
            bItem.create(0f, HEIGHT_UI + 100f, 200f, 200f)

            var timer = 0f
            bItem.renderBlockArray.add(AbstractBody.RenderBlock {
                timer += it
                if (timer >= 1) {
                    timer = 0f
                    if ((bItem.body?.position?.y ?: 0f) < (-120f).toB2) {
                        if (bItem.isOnStart.getAndSet(false)) {
                            bItemFlow.tryEmit(bItem)
                        }
                    }
                }
            })

            bItemFlow.tryEmit(bItem)
        }

        val startPos = Vector2()

        coroutine?.launch {
            bItemFlow.collect { bItem ->
                bItem.body?.apply {
                    setLinearVelocity(0f, 0f)
                    isAwake      = false
                    gravityScale = 0f

                    runGDX {
                        val nx = (100..1420).random().toFloat()
                        setTransform(startPos.set(nx, HEIGHT_UI + 100f).toB2, 0f)
                        bItem.isOnStart.set(true)
                    }
                }
            }
        }
        coroutine?.launch {
            bItemFlow.collect { bItem ->
                delay((100..2000L).random())

                runGDX {
                    bItem.body?.apply {
                        gravityScale = 1f
                        isAwake = true

                        val torque = listOf(-50f, -25f, -10f, 50f, 25f, 10f).random()
                        applyTorque(torque, true)
                        applyForceToCenter(0f, -10f, true)
                    }
                }
            }
        }
    }

}