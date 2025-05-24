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

class LoseScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font77        = fontGenerator_Itim.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(77))

    private val ls77 = LabelStyle(font77, GColor.brown_4C)

    // Actors
    private val imgPanel = Image(game.all.lose)
    private val lblStars = Label(game.dataStore.star.toString(), ls77)

    override fun show() {
        setBackBackground(game.all.listBackground[Global.indexBackground])

        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM) {
            game.soundUtil.apply { play(lose) }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgPanel()
        addLblStar()
    }

    private fun AdvancedStage.addLblStar() {
        addActor(lblStars)
        lblStars.setBounds(536f,1550f,170f,92f)
        lblStars.setAlignment(Align.center)
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(112f, 377f, 856f, 1320f)

        val aHome    = Actor()
        val aNext    = Actor()
        val aRestart = Actor()

        addActors(aHome, aNext, aRestart)

        aHome.apply {
            setBounds(150f, 407f, 184f, 184f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.clearBackStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
        aNext.apply {
            setBounds(418f, 377f, 245f, 245f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }
        aRestart.apply {
            setBounds(742f, 406f, 186f, 186f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }
    }

}