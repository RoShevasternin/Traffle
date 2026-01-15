package com.cosmostarsca.quantumfruits.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cosmostarsca.quantumfruits.game.LibGDXGame
import com.cosmostarsca.quantumfruits.game.actors.AButton
import com.cosmostarsca.quantumfruits.game.box2d.AbstractBody
import com.cosmostarsca.quantumfruits.game.box2d.WorldUtil
import com.cosmostarsca.quantumfruits.game.box2d.bodies.BItem
import com.cosmostarsca.quantumfruits.game.utils.*
import com.cosmostarsca.quantumfruits.game.utils.actor.animHide
import com.cosmostarsca.quantumfruits.game.utils.actor.animShow
import com.cosmostarsca.quantumfruits.game.utils.actor.setOnClickListener
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedBox2dScreen
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedStage
import com.cosmostarsca.quantumfruits.game.utils.font.FontParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class DishchScreen(override val game: LibGDXGame): AdvancedBox2dScreen(WorldUtil()) {
companion object {
     var RECORD = 0

}
    private val recordText = "Record\n" + "--------------------\n"

    private val parmezan = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + recordText).setSize(30)
    private val font     = fontGenerator_Bold.generateFont(parmezan)

    // Actor
    private val aBack      = AButton(this, AButton.Static.Type.Back)
    private val aRecordLbl = Label(recordText + RECORD, Label.LabelStyle(font, Color.WHITE))

    // Body
    private val bItemList = List(20) { BItem(this) }

    // Fluid
    private val itemFlow = MutableSharedFlow<BItem>(20)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.load.Loader.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAcatacors()
        createB_Item()
    }

    // Add
    private fun AdvancedStage.addAcatacors() {
        addActor(aBack)
        aBack.apply {
            setBounds(1281f, 18f, 213f, 67f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }

        addActor(aRecordLbl)
        aRecordLbl.apply {
            setBounds(1279f, 126f, 218f,150f)
            setAlignment(Align.center)
        }
    }

    // ------------------------------------------------------------------------
    // Create Body
    // ------------------------------------------------------------------------

    private fun createB_Item() {
        bItemList.onEach { bItem ->
            bItem.create(0f, HEIGHT+300, 180f, 180f)

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

                    RECORD += (1..10).random()
                    aRecordLbl.setText(recordText+RECORD)
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
                                (90..1350).random().toFloat(),
                                HEIGHT+300
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
                delay((50..320L).random())

                bItem.body?.apply {
                    gravityScale = 1f
                    isAwake = true
                    applyLinearImpulse(Vector2(0f, -(10..20).random().toFloat()), worldCenter, true)
                }
            }
        }

    }

}