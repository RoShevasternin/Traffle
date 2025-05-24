package com.shoote.maniapink.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.shoote.maniapink.game.LibGDXGame
import com.shoote.maniapink.game.utils.GColor
import com.shoote.maniapink.game.utils.Global
import com.shoote.maniapink.game.utils.TIME_ANIM
import com.shoote.maniapink.game.utils.actor.animHide
import com.shoote.maniapink.game.utils.actor.animShow
import com.shoote.maniapink.game.utils.actor.setOnClickListener
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen
import com.shoote.maniapink.game.utils.advanced.AdvancedStage
import com.shoote.maniapink.game.utils.font.FontParameter

class ShopStarScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font95        = fontGenerator_Itim.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(95))

    private val ls95 = LabelStyle(font95, GColor.brown_4C)

    // Actors
    private val imgPanel = Image(game.all.shop_star)
    private val lblStars = Label(game.dataStore.star.toString(), ls95)

    override fun show() {
        setBackBackground(game.all.listBackground[Global.indexBackground])

        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgPanel()
        addLblStar()
    }

    private fun AdvancedStage.addLblStar() {
        addActor(lblStars)
        lblStars.setBounds(481f,1511f,210f,114f)
        lblStars.setAlignment(Align.center)
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(50f,757f,940f,1113f)

        val aHome = Actor()
        val a100  = Actor()
        val a300  = Actor()

        addActors(aHome, a100, a300)

        aHome.apply {
            setBounds(50f, 1732f, 138f, 138f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.clearBackStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
        a100.apply {
            setBounds(432f, 1063f, 233f, 67f)
            setOnClickListener(game.soundUtil) {
                game.dataStore.updateStar { it + 100 }
                root.animHide(TIME_ANIM) {
                    game.navigationManager.clearBackStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
        a300.apply {
            setBounds(432f, 757f, 233f, 67f)
            setOnClickListener(game.soundUtil) {
                game.dataStore.updateStar { it + 300 }
                root.animHide(TIME_ANIM) {
                    game.navigationManager.clearBackStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
    }

}