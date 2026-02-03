package com.greciao.candyrocket.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.greciao.candyrocket.game.LibGDXGame
import com.greciao.candyrocket.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.greciao.candyrocket.game.utils.WIDTH_UI
import com.greciao.candyrocket.game.utils.actor.animHide
import com.greciao.candyrocket.game.utils.actor.animShow
import com.greciao.candyrocket.game.utils.actor.setBounds
import com.greciao.candyrocket.game.utils.actor.setOnClickListener
import com.greciao.candyrocket.game.utils.advanced.AdvancedScreen
import com.greciao.candyrocket.game.utils.advanced.AdvancedStage
import com.greciao.candyrocket.game.utils.font.FontParameter
import com.greciao.candyrocket.game.utils.region

class ShopScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var AVIA = AviaType._500
            private set
    }

    private val assets = game.allAssets

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(35)
    private val font          = fontGenerator_Averta.generateFont(fontParameter)

    private val aCoinLbl = Label("$CANONcoinCount", Label.LabelStyle(font, Color.valueOf("F51616")))

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loaderAssets.background.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(Image(assets.shop).apply { setBounds(35f, 80f, 1090f, 492f) })

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
            Vector2(186f, 84f) to Vector2(247f, 285f),
            Vector2(878f, 85f) to Vector2(247f, 285f),
            Vector2(518f, 80f) to Vector2(275f, 318f),
        ).onEachIndexed { index, data ->
            Actor().also { a ->
                addActor(a)
                a.setBounds(data.first, data.second)
                val lis = listOf(500, 700, 1000)
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
        _500(0), _700(1), _1000(2)
    }

}