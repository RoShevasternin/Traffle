package com.shoote.maniapink.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.shoote.maniapink.game.LibGDXGame
import com.shoote.maniapink.game.actors.checkbox.ACheckBox
import com.shoote.maniapink.game.utils.TIME_ANIM
import com.shoote.maniapink.game.utils.actor.animHide
import com.shoote.maniapink.game.utils.actor.animShow
import com.shoote.maniapink.game.utils.actor.setBounds
import com.shoote.maniapink.game.utils.actor.setOnClickListener
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen
import com.shoote.maniapink.game.utils.advanced.AdvancedStage
import kotlin.math.truncate

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private companion object {
        var isMusic = true
        var isSound = true
    }

    // Actors
    private val imgSettings = Image(game.all.settings)
    private val boxMusic    = ACheckBox(this, ACheckBox.Type.CHECK)
    private val boxSound    = ACheckBox(this, ACheckBox.Type.CHECK)

    override fun show() {
        setBackBackground(game.all.Orange)

        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgSettings()
        addBoxes()

        val aX = Actor()
        addActor(aX)
        aX.apply {
            setBounds(845f, 1173f, 130f, 130f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgSettings() {
        addActor(imgSettings)
        imgSettings.setBounds(104f,528f,873f,863f)
    }

    private fun AdvancedStage.addBoxes() {
        addActors(boxMusic, boxSound)

        boxMusic.apply {
            setBounds(720f, 942f, 85f, 83f)
            if (isMusic) check(false)
            setOnCheckListener { isCheck ->
                isMusic = isCheck
                if (isMusic) game.musicUtil.music?.play() else game.musicUtil.music?.pause()
            }
        }
        boxSound.apply {
            setBounds(720f, 767f, 85f, 83f)
            if (isSound) check(false)
            setOnCheckListener { isCheck ->
                isSound = isCheck
                game.soundUtil.isPause = isSound.not()
            }
        }
    }

}