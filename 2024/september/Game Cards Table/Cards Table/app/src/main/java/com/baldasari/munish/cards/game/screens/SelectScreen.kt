package com.baldasari.munish.cards.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.baldasari.munish.cards.game.LibGDXGame
import com.baldasari.munish.cards.game.actors.AButton
import com.baldasari.munish.cards.game.actors.checkbox.ACheckBox
import com.baldasari.munish.cards.game.utils.TIME_ANIM
import com.baldasari.munish.cards.game.utils.actor.animHide
import com.baldasari.munish.cards.game.utils.actor.animShow
import com.baldasari.munish.cards.game.utils.actor.setOnClickListener
import com.baldasari.munish.cards.game.utils.advanced.AdvancedScreen
import com.baldasari.munish.cards.game.utils.advanced.AdvancedStage
import com.baldasari.munish.cards.game.utils.region
import com.baldasari.munish.cards.game.utils.runGDX
import kotlinx.coroutines.launch

class SelectScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var SELECT_COUNT = 2
            private set
    }

    private val btnMenu = AButton(this, AButton.Static.Type.Back)
    private val imgMSV  = Image(game.all.select)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.background.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgMSV()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(27f,1703f,186f,186f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgMSV() {
        addActor(imgMSV)
        imgMSV.setBounds(54f,499f,971f,1052f)

        val a2 = Actor()
        val a3 = Actor()
        val a4 = Actor()
        addActors(a2,a3,a4)
        a2.apply {
            setBounds(290f,1036f,500f,200f)
            setOnClickListener(game.soundUtil) {
                SELECT_COUNT = 2
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(PlayerScreen::class.java.name, SelectScreen::class.java.name)
                }
            }
        }
        a3.apply {
            setBounds(290f,786f,500f,200f)
            setOnClickListener(game.soundUtil) {
                SELECT_COUNT = 3
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(PlayerScreen::class.java.name, SelectScreen::class.java.name)
                }
            }
        }
        a4.apply {
            setBounds(290f,536f,500f,200f)
            setOnClickListener(game.soundUtil) {
                SELECT_COUNT = 4
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(PlayerScreen::class.java.name, SelectScreen::class.java.name)
                }
            }
        }
    }

}