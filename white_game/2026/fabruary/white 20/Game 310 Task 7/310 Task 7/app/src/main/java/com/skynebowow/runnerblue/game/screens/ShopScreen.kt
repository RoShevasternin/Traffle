package com.skynebowow.runnerblue.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.skynebowow.runnerblue.game.GDXGame
import com.skynebowow.runnerblue.game.actors.button.AButton
import com.skynebowow.runnerblue.game.actors.checkbox.ACheckBox
import com.skynebowow.runnerblue.game.actors.checkbox.ACheckBoxGroup
import com.skynebowow.runnerblue.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.skynebowow.runnerblue.game.utils.actor.animHide
import com.skynebowow.runnerblue.game.utils.actor.animShow
import com.skynebowow.runnerblue.game.utils.actor.setBounds
import com.skynebowow.runnerblue.game.utils.actor.setOnClickListener
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedScreen
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedStage
import com.skynebowow.runnerblue.game.utils.region

class ShopScreen(override val game: GDXGame) : AdvancedScreen() {

    companion object {
        var AVIA = AviaType._1
            private set
    }

    private val assets = game.gameAssets

    private val aShopImg = Image(assets.SHOP)
    private val aBackBtn = AButton(this, AButton.Static.Type.BACK)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.gameAssets.BK_REST.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addShopImg()
        addBackBtn()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBackBtn() {
        addActor(aBackBtn)
        aBackBtn.setBounds(62f, 1757f, 110f, 110f)

        aBackBtn.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addShopImg() {
        addActor(aShopImg)
        aShopImg.setBounds(56f, 375f, 968f, 1506f)

        val cbg = ACheckBoxGroup()

        arrayOf(
            Vector2(56f, 971f),
            Vector2(552f, 971f),
            Vector2(56f, 375f),
            Vector2(552f, 375f),
        ).onEachIndexed { index, pos ->
            ACheckBox(this@ShopScreen, ACheckBox.Static.Type.CHECK).also { a ->
                addActor(a)
                a.setBounds(pos.add(190f, 8f), Vector2(99f, 99f))

                a.checkBoxGroup = cbg

                a.setOnCheckListener { isCheck ->
                    if (isCheck) {
                        AVIA = when (index) {
                            0 -> AviaType._1
                            1 -> AviaType._2
                            2 -> AviaType._3
                            3 -> AviaType._4
                            else -> AviaType._1
                        }
                    }
                }
            }
        }
    }

    // ---------------------------------------------------
    // classes
    // ---------------------------------------------------

    enum class AviaType(val avia_index: Int) {
        _1(0), _2(1), _3(2), _4(3)
    }

}