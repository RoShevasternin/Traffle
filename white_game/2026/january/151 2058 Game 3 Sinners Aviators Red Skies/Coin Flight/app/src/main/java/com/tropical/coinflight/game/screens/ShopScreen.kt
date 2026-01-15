package com.tropical.coinflight.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.tropical.coinflight.game.LibGDXGame
import com.tropical.coinflight.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.tropical.coinflight.game.utils.actor.animHide
import com.tropical.coinflight.game.utils.actor.animShow
import com.tropical.coinflight.game.utils.actor.setBounds
import com.tropical.coinflight.game.utils.actor.setOnClickListener
import com.tropical.coinflight.game.utils.advanced.AdvancedScreen
import com.tropical.coinflight.game.utils.advanced.AdvancedStage
import com.tropical.coinflight.game.utils.font.FontParameter
import com.tropical.coinflight.game.utils.region

class ShopScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var AVIA = AviaType._300
            private set
    }

    private val assets = game.allAssets

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(35)
    private val font          = fontGenerator_Averta.generateFont(fontParameter)

    private val aCoinLbl = Label("$CANONcoinCount", Label.LabelStyle(font, Color.WHITE))

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.allAssets.shop.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addAvias()

        val a = Image(assets.ase)
        addActor(a)
        a.setBounds(1079f, 468f, 209f, 74f)

        addActor(aCoinLbl)
        aCoinLbl.setBounds(1149f, 485f, 68f, 41f)
        aCoinLbl.setAlignment(Align.center)
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        val menu = Actor()
        addActor(menu)
        menu.setBounds(36f, 505f, 66f, 66f)

        menu.setOnClickListener(game.soundUtil) {
            //stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                game.navigationManager.back()
            //}
        }
    }

    private fun AdvancedStage.addAvias() {
        arrayOf(
            Vector2(63f, 72f) to Vector2(193f, 223f),
            Vector2(266f, 238f) to Vector2(247f, 285f),
            Vector2(816f, 238f) to Vector2(247f, 285f),
            Vector2(1073f, 72f) to Vector2(193f, 223f),
            Vector2(518f, 25f) to Vector2(275f, 318f),
        ).onEachIndexed { index, data ->
            Actor().also { a ->
                addActor(a)
                a.setBounds(data.first, data.second)
                val lis = listOf(300, 500, 700, 800, 1000)
                a.setOnClickListener(game.soundUtil) {
                    if (CANONcoinCount >= lis[index]) {
                        CANONcoinCount -= lis[index]
                        aCoinLbl.setText(CANONcoinCount)
                    } else return@setOnClickListener
                    AVIA = AviaType.entries.toTypedArray()[index]
                    //stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                        game.navigationManager.navigate(GameScreen::class.java.name, ShopScreen::class.java.name)
                    //}
                }
            }
        }
    }

    // ---------------------------------------------------
    // classes
    // ---------------------------------------------------

    enum class AviaType(val avia_index: Int) {
        _300(0), _500(1), _700(2), _800(3), _1000(4)
    }

}