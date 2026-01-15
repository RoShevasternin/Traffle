package com.sweetfruit.catchermandarin.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.sweetfruit.catchermandarin.game.PoromGame
import com.sweetfruit.catchermandarin.game.actors.button.AButton
import com.sweetfruit.catchermandarin.game.box2d.AbstractBody
import com.sweetfruit.catchermandarin.game.box2d.WorldUtil
import com.sweetfruit.catchermandarin.game.box2d.bodies.BItem
import com.sweetfruit.catchermandarin.game.utils.Poshlo_VREMA
import com.sweetfruit.catchermandarin.game.utils.actor.animHide
import com.sweetfruit.catchermandarin.game.utils.actor.animShow
import com.sweetfruit.catchermandarin.game.utils.actor.setBounds
import com.sweetfruit.catchermandarin.game.utils.actor.setOnClickListener
import com.sweetfruit.catchermandarin.game.utils.advanced.AdvancedBox2dScreen
import com.sweetfruit.catchermandarin.game.utils.advanced.AdvancedStage
import com.sweetfruit.catchermandarin.game.utils.font.FontParameter
import com.sweetfruit.catchermandarin.game.utils.region
import com.sweetfruit.catchermandarin.game.utils.runGDX
import com.sweetfruit.catchermandarin.game.utils.toB2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SviterScreen(override val game: PoromGame): AdvancedBox2dScreen(WorldUtil()) {

    private val parmezan = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(70)
    private val font = fontGenerator_LeagueGothic.generateFont(parmezan)

    private val listValue = mutableListOf(0,0,0,0,0)

    // Actor
    private val aMenu = AButton(this, AButton.Static.Type.Menu)
    private val aLblList = List(5) { Label("0", Label.LabelStyle(font, Color.WHITE)) }

    // Body
    private val bItemList = List(10) { BItem(this, it) }

    // Fluid
    private val itemFlow = MutableSharedFlow<BItem>(10)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.guglas.lodrinking.region)
        super.show()
        stageUI.root.animShow(Poshlo_VREMA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenu()
        createB_Item()

        addActor(Image(game.faradeo.blures).apply { setBounds(0f, 0f, 255f, 900f) })

        var ny = 747f
        if (SetsScreen.isCandy) {
            game.faradeo.confeti.take(5).onEach {
                addActor(Image(it).apply { setBounds(110f, ny, 85f, 85f) })
                ny -= 85+85
            }
        } else {
            game.faradeo.confeti.takeLast(5).onEach {
                addActor(Image(it).apply { setBounds(110f, ny, 85f, 85f) })
                ny -= 85+85
            }
        }

        var lNY = 748f
        repeat(5) {
            addActor(aLblList[it].apply {
                setBounds(6f, lNY, 98f, 84f)
                setAlignment(Align.center)
                lNY -= 86+84
            })
        }
    }

    // Add
    private fun AdvancedStage.addMenu() {
        addActors(aMenu)

        aMenu.apply {
            setBounds(1345f, 28f, 165f, 165f)
            setOnClickListener {
                stageUI.root.animHide(Poshlo_VREMA) { game.navigationManager.back() }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Create Body
    // ------------------------------------------------------------------------

    private fun createB_Item() {
        bItemList.onEach { bItem ->
            bItem.create(0f, HEIGHT+500, 197f, 197f)

            var timer = 0f
            bItem.renderBlockArray.add(AbstractBody.RenderBlock {
                timer += it
                if (timer >= 1) {
                    timer = 0f
                    if((bItem.body?.position?.y ?: 0f) <= 0f) {
                        if (bItem.isOnStart.getAndSet(false)) {
                            itemFlow.tryEmit(bItem)
                        }
                    }
                }
            })

            itemFlow.tryEmit(bItem)

            bItem.actor?.setOnClickListener {
                if (bItem.isOnStart.getAndSet(false)) {

                    game.soundUtil.apply { play(boom) }

                    bItem.startEffect()

                    listValue[bItem.IDID] = listValue[bItem.IDID] + 1
                    aLblList[bItem.IDID].setText(
                        listValue[bItem.IDID]
                    )
                    itemFlow.tryEmit(bItem)
                }
            }
        }

        val startPos = Vector2()

        coroutine?.launch {
            itemFlow.collect { bItem ->
                bItem.body?.apply {
                    setLinearVelocity(0f, 0f)
                    isAwake      = false
                    gravityScale = 0f

                    runGDX {
                        setTransform(
                            startPos.set(
                                (330..1400).random().toFloat(),
                                HEIGHT+500
                            ).toB2,
                            0f
                        )
                        bItem.isOnStart.set(true)
                    }
                }
            }
        }
        coroutine?.launch {
            itemFlow.collect { bItem ->
                delay((500..1000L).random())

                bItem.body?.apply {
                    gravityScale = 1f
                    isAwake = true
                    applyLinearImpulse(Vector2(0f, -(10..20).random().toFloat()), worldCenter, true)
                }
            }
        }

    }

}