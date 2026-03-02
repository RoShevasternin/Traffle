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

class ShopScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font48        = fontGenerator_Itim.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(48))

    private val ls48 = LabelStyle(font48, GColor.brown_4C)

    // Actors
    private val imgPanel = Image(game.all.shop)
    private val lblStars = Label(game.dataStore.star.toString(), ls48)

    override fun show() {
        setBackBackground(game.all.listBackground[Global.indexBackground])

        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgPanel()
        addLblStar()


//        val aShopStar = Actor()
//        addActor(aShopStar)
//        aShopStar.apply {
//            setBounds(734f, 1749f, 308f, 113f)
//            setOnClickListener(game.soundUtil) {
//                root.animHide(TIME_ANIM) {
//                    game.navigationManager.navigate(ShopStarScreen::class.java.name, ShopScreen::class.java.name)
//                }
//            }
//        }
    }

    private fun AdvancedStage.addLblStar() {
        addActor(lblStars)
        lblStars.setBounds(857f,1770f,106f,58f)
        lblStars.setAlignment(Align.center)
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(50f, 431f, 992f, 1439f)

        val aHome    = Actor()

        addActor(aHome)

        aHome.apply {
            setBounds(50f, 1732f, 138f, 138f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.clearBackStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }

        val aBomb       = Actor()
        val aLaser      = Actor()
        val aBackground = Actor()

        addActors(aBomb, aLaser, aBackground)

        aBomb.apply {
            setBounds(183f, 1077f, 233f, 67f)
            setOnClickListener(game.soundUtil) {
                if (game.dataStore.star >= 100) {
                    game.soundUtil.apply { play(bonus) }

                    game.dataStore.updateStar { it - 100 }
                    game.dataStore.updateIsBomb(true)

                    lblStars.setText(game.dataStore.star)
                } else {
                    game.soundUtil.apply { play(fail) }
                }
            }
        }
        aLaser.apply {
            setBounds(663f, 1077f, 233f, 67f)
            setOnClickListener(game.soundUtil) {
                if (game.dataStore.star >= 250) {
                    game.soundUtil.apply { play(bonus) }

                    game.dataStore.updateStar { it - 250 }
                    game.dataStore.updateIsLaser(true)

                    lblStars.setText(game.dataStore.star)
                } else {
                    game.soundUtil.apply { play(fail) }
                }
            }
        }
        aBackground.apply {
            setBounds(183f, 431f, 233f, 67f)
            setOnClickListener(game.soundUtil) {
                if (game.dataStore.star >= 500) {
                    game.soundUtil.apply { play(bonus) }

                    game.dataStore.updateStar { it - 500 }
                    game.dataStore.updateIsBackground(true)

                    lblStars.setText(game.dataStore.star)

                    Global.indexBackground = 1
                    setBackBackground(game.all.listBackground[Global.indexBackground])
                } else {
                    game.soundUtil.apply { play(fail) }
                }
            }
        }
    }

}