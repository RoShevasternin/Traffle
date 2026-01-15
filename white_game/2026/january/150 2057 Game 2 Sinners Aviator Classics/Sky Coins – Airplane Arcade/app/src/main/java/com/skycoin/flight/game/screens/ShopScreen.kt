package com.skycoin.flight.game.screens

import android.graphics.Paint
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.skycoin.flight.game.LibGDXGame
import com.skycoin.flight.game.actors.button.AButton
import com.skycoin.flight.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.skycoin.flight.game.utils.actor.animHide
import com.skycoin.flight.game.utils.actor.animShow
import com.skycoin.flight.game.utils.actor.setOnClickListener
import com.skycoin.flight.game.utils.advanced.AdvancedScreen
import com.skycoin.flight.game.utils.advanced.AdvancedStage
import com.skycoin.flight.game.utils.region
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.skycoin.flight.game.utils.actor.setBounds
import com.skycoin.flight.game.utils.font.FontParameter

class ShopScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var AVIA = AviaType._100
            private set
    }
    private val assets = game.gameAssets

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(30)
    private val font          = fontGenerator_28Days.generateFont(fontParameter)
    private val aCoinLbl      = Label("$GLOBAL_coinCount", Label.LabelStyle(font, Color.WHITE))
    private val imgBvs        = Image(assets.bls)

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.gameAssets.sipos.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addAvias()

        addActor(imgBvs)
        imgBvs.setBounds(483f, 1283f, 156f, 48f)
        addActor(aCoinLbl)
        aCoinLbl.setBounds(544f, 1287f, 65f, 40f)
        aCoinLbl.setAlignment(Align.center)
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        val menu = AButton(this@ShopScreen, AButton.Static.Type.MENU)
        addActor(menu)
        menu.setBounds(21f, 1257f, 102f, 102f)

        menu.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addAvias() {
        arrayOf(
            Vector2(21f, 868f),
            Vector2(351f, 868f),
            Vector2(186f, 481f),
            Vector2(21f, 83f),
            Vector2(351f, 83f),
        ).onEachIndexed { index, pos ->
            Actor().also { a ->
                addActor(a)
                a.setBounds(pos,Vector2(275f, 347f))

                val listCost = listOf(100, 200, 1000, 500, 850)

                a.setOnClickListener(game.soundUtil) {
                    if (GLOBAL_coinCount >= listCost[index]) {
                        GLOBAL_coinCount -= listCost[index]
                        aCoinLbl.setText(GLOBAL_coinCount)
                    } else return@setOnClickListener

                    AVIA = when(index) {
                        0 -> AviaType._100
                        1 -> AviaType._200
                        2 -> AviaType._1000
                        3 -> AviaType._500
                        4 -> AviaType._850
                        else -> AviaType._100
                    }
                    stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                        game.navigationManager.navigate(GameScreen::class.java.name, ShopScreen::class.java.name)
                    }
                }
            }
        }
    }

    // ---------------------------------------------------
    // classes
    // ---------------------------------------------------

    enum class AviaType(val avia_index: Int) {
        _100(0), _200(1), _500(2), _850(3), _1000(4)
    }

}