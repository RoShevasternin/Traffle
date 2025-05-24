package com.baldasari.munish.cards.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.baldasari.munish.cards.game.LibGDXGame
import com.baldasari.munish.cards.game.actors.AButton
import com.baldasari.munish.cards.game.utils.*
import com.baldasari.munish.cards.game.utils.actor.animHide
import com.baldasari.munish.cards.game.utils.actor.animShow
import com.baldasari.munish.cards.game.utils.advanced.AdvancedScreen
import com.baldasari.munish.cards.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true
    }

    private val imgMag = Image(game.splash.magList.random())

    private val btnPlay     = AButton(this, AButton.Static.Type.Play)
    private val btnSettings = AButton(this, AButton.Static.Type.Sett)
    private val btnRules    = AButton(this, AButton.Static.Type.Rules)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = AUDIO.apply {
                isLooping = true
                volumeLevelFlow.value = 20f
            } }
        }

        stageUI.root.animHide()
        setBackBackground(game.splash.background.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgCandy()
                addBtns()
            }
        }
    }

    private fun AdvancedStage.addImgCandy() {
        addActor(imgMag)
        imgMag.apply {
            setBounds(218f, 638f, 645f, 645f)
            setOrigin(322f,0f)
            val scale = 0.18f
            addAction(Actions.forever(
                Actions.sequence(
                    Actions.scaleBy(-scale, -scale, 0.4f, Interpolation.sineIn),
                    Actions.scaleBy(scale, scale, 0.4f, Interpolation.sineOut),
                )
            ))
        }
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnPlay, btnSettings, btnRules)
        btnPlay.apply {
            setBounds(142f,209f,796f,316f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(SelectScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnSettings.apply {
            setBounds(0f,1424f,318f,276f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(SettingScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnRules.apply {
            setBounds(822f,1470f,218f,192f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

}