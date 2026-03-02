package com.shoote.maniapink.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.shoote.maniapink.game.LibGDXGame
import com.shoote.maniapink.game.utils.TIME_ANIM
import com.shoote.maniapink.game.utils.actor.animHide
import com.shoote.maniapink.game.utils.actor.animShow
import com.shoote.maniapink.game.utils.actor.setOnClickListener
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen
import com.shoote.maniapink.game.utils.advanced.AdvancedStage

class ModeScreen(override val game: LibGDXGame) : AdvancedScreen() {

    override fun show() {
        setBackBackground(game.loading.background)
        setUIBackground(drawerUtil.getTexture(Color.BLACK.cpy().apply { a = 0.6f }))
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        val img = Image(game.all.mode)
        stageUI.addAndFillActor(img)

        addBtns()
    }

    private fun AdvancedStage.addBtns() {
        val aShop        = Actor()
        val aHome        = Actor()
        val aClassic     = Actor()
        val aPuzzle      = Actor()
        val aTime        = Actor()
        val aCompetitive = Actor()

        addActors(aShop, aHome, aClassic, aPuzzle, aTime, aCompetitive)

        aShop.apply {
            setBounds(892f, 1732f, 138f, 138f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(ShopScreen::class.java.name, ModeScreen::class.java.name)
                }
            }
        }
        aHome.apply {
            setBounds(50f, 1732f, 138f, 138f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
        aClassic.apply {
            setBounds(69f, 1292f, 637f, 258f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name, ModeScreen::class.java.name)
                }
            }
        }
        aPuzzle.apply {
            setBounds(372f, 916f, 637f, 258f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name, ModeScreen::class.java.name)
                }
            }
        }
        aTime.apply {
            setBounds(69f, 539f, 637f, 258f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name, ModeScreen::class.java.name)
                }
            }
        }
        aCompetitive.apply {
            setBounds(372f, 162f, 637f, 258f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name, ModeScreen::class.java.name)
                }
            }
        }

    }

}