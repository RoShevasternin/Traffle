package com.royaltombsecrets.miniquizler.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.royaltombsecrets.miniquizler.R
import com.royaltombsecrets.miniquizler.appContext
import com.royaltombsecrets.miniquizler.game.LibGDXGame
import com.royaltombsecrets.miniquizler.game.actors.AButton
import com.royaltombsecrets.miniquizler.game.actors.AInfoGroup
import com.royaltombsecrets.miniquizler.game.box2d.AbstractBody
import com.royaltombsecrets.miniquizler.game.box2d.WorldUtil
import com.royaltombsecrets.miniquizler.game.box2d.bodies.BItem
import com.royaltombsecrets.miniquizler.game.utils.*
import com.royaltombsecrets.miniquizler.game.utils.actor.animHide
import com.royaltombsecrets.miniquizler.game.utils.actor.animShow
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedBox2dScreen
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedStage
import com.royaltombsecrets.miniquizler.game.utils.font.FontParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class CollectInfoScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font50        = fontGenerator_GlassAntiqua.generateFont(fontParameter.setSize(50))

    private val btnMenu = AButton(this, AButton.Static.Type.Menu)
    private val imgTop  = Image(game.all.top)
    private val lblTop  = Label(appContext.resources.getString(R.string.rules_text), Label.LabelStyle(font50, Color.WHITE))

    private val info   = AInfoGroup(this)
    private val scroll = ScrollPane(info)

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
        addImgTop()
        addScroll()
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(691f, 37f, 138f, 84f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgTop() {
        addActor(imgTop)
        imgTop.setBounds(0f, 699f, 1521f, 201f)
        addActor(lblTop)
        lblTop.setBounds(34f, 699f, 1453f, 201f)
        lblTop.setAlignment(Align.center)
        lblTop.wrap = true
    }

    private fun AdvancedStage.addScroll() {
       addActor(scroll)
       scroll.setBounds(0f, 220f, 1581f, 377f)
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