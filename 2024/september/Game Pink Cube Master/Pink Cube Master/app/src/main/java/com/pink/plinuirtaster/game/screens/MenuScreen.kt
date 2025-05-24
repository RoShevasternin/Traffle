package com.pink.plinuirtaster.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.actors.AButton
import com.pink.plinuirtaster.game.screens.SettingsScreen.Companion.IS_THEME_LIGHT
import com.pink.plinuirtaster.game.utils.*
import com.pink.plinuirtaster.game.utils.actor.animHideScreen
import com.pink.plinuirtaster.game.utils.actor.animShowScreen
import com.pink.plinuirtaster.game.utils.actor.setOnClickListener
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true
    }

    private val imgRules = Image(game.all.RULES_BOOK)
    private val btnPlay  = AButton(this, AButton.Static.Type.Play)
    private val imgSett  = Image(game.all.e_sett)
    private val imgExit  = Image(game.all.e_exit)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = LIGHT_AUDIO.apply {
                isLooping = true
                volumeLevelFlow.value = 35f
            } }
        }

        setBackBackground(if (IS_THEME_LIGHT) game.splash.BACKGROUND.region else game.all.Dark.region)

        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgRules()
                addBtnPlay()

                addActors(imgSett, imgExit)
                imgSett.apply {
                    setBounds(52f,1730f,140f,140f)
                    setOnClickListener(game.soundUtil) {
                        stageUI.root.animHideScreen(TIME_ANIM) {
                            game.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name)
                        }
                    }
                }
                imgExit.apply {
                    setBounds(403f,109f,274f,137f)
                    setOnClickListener(game.soundUtil) {
                        stageUI.root.animHideScreen(TIME_ANIM) {
                            game.navigationManager.exit()
                        }
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addImgRules() {
        addActor(imgRules)
        imgRules.apply {
            setBounds(111f, 759f, 818f, 971f)
            setOrigin(Align.center)
            val scale = 0.2f
            addAction(Actions.forever(
                Actions.sequence(
                    Actions.scaleBy(-scale, -scale, 0.5f, Interpolation.slowFast),
                    Actions.scaleBy(scale, scale, 0.5f, Interpolation.fastSlow),
                )
            ))
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addBtnPlay() {
        addActor(btnPlay)
        btnPlay.apply {
            setBounds(240f, 296f, 600f, 300f)
            setOnClickListener {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(NamesScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

}