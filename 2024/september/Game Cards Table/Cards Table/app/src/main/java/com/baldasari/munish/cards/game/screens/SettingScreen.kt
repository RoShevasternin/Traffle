package com.baldasari.munish.cards.game.screens

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

class SettingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var IS_VIBRO = true
    }

    private val musicBox = ACheckBox(this, ACheckBox.Static.Type.GALKA)
    private val soundBox = ACheckBox(this, ACheckBox.Static.Type.GALKA)
    private val vibroBox = ACheckBox(this, ACheckBox.Static.Type.GALKA)

    private val btnMenu = AButton(this, AButton.Static.Type.Back)
    private val imgMSV  = Image(game.all.msv)

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
                addBoxes()
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
        imgMSV.setBounds(82f,357f,916f,1198f)
    }

    private fun AdvancedStage.addBoxes() {
        addActors(musicBox, soundBox, vibroBox)
        musicBox.apply {
            setBounds(750f,1221f,143f,136f)

            screen.game.musicUtil.music?.also { mu ->
                if (mu.isPlaying) check(false)

                setOnCheckListener { isCHeck ->
                    if (isCHeck) mu.play() else mu.pause()
                }
            }
        }
        soundBox.apply {
            setBounds(750f,917f,143f,136f)

            screen.game.soundUtil.also { su ->
                if (su.isPause.not()) check(false)

                setOnCheckListener { isCHeck ->
                    su.isPause = isCHeck.not()
                }
            }
        }
        vibroBox.apply {
            setBounds(750f,603f,143f,136f)
            if (IS_VIBRO) check(false)
            setOnCheckListener { isCHeck ->
                IS_VIBRO = isCHeck
            }
        }
    }

}