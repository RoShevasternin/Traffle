package com.pink.plinuirtaster.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.actors.checkbox.ACheckBox
import com.pink.plinuirtaster.game.utils.TIME_ANIM
import com.pink.plinuirtaster.game.utils.actor.animHideScreen
import com.pink.plinuirtaster.game.utils.actor.animShowScreen
import com.pink.plinuirtaster.game.utils.actor.setOnClickListener
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedStage
import com.pink.plinuirtaster.game.utils.region
import com.pink.plinuirtaster.game.utils.runGDX
import kotlinx.coroutines.launch

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var IS_THEME_LIGHT = true
            private set
        var IS_MUSIC = true
            private set
        var IS_SOUND = true
            private set
    }

    private val imgSett  = Image(game.all.e_tsm)
    private val imgExit  = Image(game.all.e_back)
    private val boxTheme = ACheckBox(this, ACheckBox.Static.Type.LIGHT_DARK)
    private val boxMusic = ACheckBox(this, ACheckBox.Static.Type.OFF_ON)
    private val boxSound = ACheckBox(this, ACheckBox.Static.Type.OFF_ON)

    override fun show() {
        setBackBackground(if (IS_THEME_LIGHT) game.splash.BACKGROUND.region else game.all.Dark.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {

                addActors(imgSett, imgExit, boxTheme, boxMusic, boxSound)
                imgSett.setBounds(162f,690f,352f,642f)

                imgExit.apply {
                    setBounds(403f,109f,274f,137f)
                    setOnClickListener(game.soundUtil) {
                        stageUI.root.animHideScreen(TIME_ANIM) {
                            game.navigationManager.back()
                        }
                    }
                }
                boxTheme.apply {
                    setBounds(644f,1207f,274f,137f)
                    if (IS_THEME_LIGHT.not()) check(false)
                    setOnCheckListener { isCheck ->
                        IS_THEME_LIGHT = isCheck.not()
                        setBackBackground(if (IS_THEME_LIGHT) game.splash.BACKGROUND.region else game.all.Dark.region)
                    }
                }
                boxMusic.apply {
                    setBounds(644f,679f,274f,137f)
                    if (IS_MUSIC) check(false)
                    setOnCheckListener { isCheck ->
                        IS_MUSIC = isCheck
                        if (IS_MUSIC) game.musicUtil.music?.play() else game.musicUtil.music?.pause()
                    }
                }
                boxSound.apply {
                    setBounds(644f,943f,274f,137f)
                    if (IS_SOUND) check(false)
                    setOnCheckListener { isCheck ->
                        IS_SOUND = isCheck
                        game.soundUtil.isPause = IS_SOUND.not()
                    }
                }
            }
        }
    }

}