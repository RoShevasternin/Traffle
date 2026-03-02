package com.shoote.maniapink.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.shoote.maniapink.game.LibGDXGame
import com.shoote.maniapink.game.utils.*
import com.shoote.maniapink.game.utils.actor.animHide
import com.shoote.maniapink.game.utils.actor.animShow
import com.shoote.maniapink.game.utils.actor.setOnClickListener
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen
import com.shoote.maniapink.game.utils.advanced.AdvancedStage
import com.shoote.maniapink.game.utils.font.FontParameter

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirstShow = true
    }

    private val fontParameter = FontParameter()
    private val font77        = fontGenerator_Itim.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(77))

    private val ls77 = LabelStyle(font77, GColor.brown_4C)

    // Actors
    private val lblStars = Label(game.dataStore.star.toString(), ls77)

    override fun show() {
        if (isFirstShow) {
            isFirstShow = false

            game.musicUtil.also { mu ->
                mu.music = mu.musida.apply { isLooping = true }
                mu.volumeLevelFlow.value = 17f
            }
        }

        if (game.dataStore.isBackground) Global.indexBackground = 1

        setBackBackground(game.loading.background)
        setUIBackground(game.all.menu)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        val img = Image(game.all.shariks)
        stageBack.addAndFillActor(img)
        img.width  = stageBack.width
        img.height = (stageBack.width * 1.777f)

        addLblStar()
        addBtns()
    }

    private fun AdvancedStage.addLblStar() {
        addActor(lblStars)
        lblStars.setBounds(492f,1599f,170f,92f)
        lblStars.setAlignment(Align.center)
    }

    private fun AdvancedStage.addBtns() {
        val aShop        = Actor()
        val aShopStar    = Actor()
        val aPlay        = Actor()
        val aSettings    = Actor()
        val aLeaderboard = Actor()
        val aExit        = Actor()

        addActors(aShop, aShopStar, aPlay, aSettings, aLeaderboard, aExit)

        aShop.apply {
            setBounds(892f, 1732f, 138f, 138f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(ShopScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
//        aShopStar.apply {
//            setBounds(297f, 1566f, 485f, 178f)
//            setOnClickListener(game.soundUtil) {
//                root.animHide(TIME_ANIM) {
//                    game.navigationManager.navigate(ShopStarScreen::class.java.name, MenuScreen::class.java.name)
//                }
//            }
//        }
        aPlay.apply {
            setBounds(150f, 1179f, 779f, 224f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(ModeScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aSettings.apply {
            setBounds(150f, 894f, 779f, 224f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
//        aLeaderboard.apply {
//            setBounds(150f, 609f, 779f, 224f)
//            setOnClickListener(game.soundUtil) {
//                root.animHide(TIME_ANIM) {
//                    game.navigationManager.navigate(MenuScreen::class.java.name, MenuScreen::class.java.name)
//                }
//            }
//        }
        aExit.apply {
            setBounds(316f, 185f, 448f, 161f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.exit()
                }
            }
        }

    }

}