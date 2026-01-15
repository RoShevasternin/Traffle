package com.bounceroval.mazedackq.game.screens

import com.badlogic.gdx.math.Interpolation
import com.bounceroval.mazedackq.game.LibGDXGame
import com.bounceroval.mazedackq.game.utils.actor.animHide
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedScreen
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedStage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bounceroval.mazedackq.game.actors.AButton
import com.bounceroval.mazedackq.game.actors.AProgress
import com.bounceroval.mazedackq.game.actors.checkbox.ACheckBox
import com.bounceroval.mazedackq.game.utils.*
import com.bounceroval.mazedackq.game.utils.actor.animShow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var isVIBRO = true
    }

    private val tSett = Image(game.all.t_settings)
    private val panel  = Image(game.all.big_sett)
    private val back   = AButton(this, AButton.Static.Type.Back)
    private val music  = AProgress(this).apply { color.a = 0f }
    private val sound  = AProgress(this).apply { color.a = 0f }
    private val box    = ACheckBox(this, ACheckBox.Static.Type.ONF).apply { color.a = 0f }

    override fun show() {
        setBackBackground(game.splash.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBtns()

                animShowScreen()
            }
            delay(300)
            runGDX {
                music.animShow(0.5f)
                sound.animShow(0.5f)
                box.animShow(0.5f)
            }
        }
    }

    private fun AdvancedStage.addBtns() {
        addActors(tSett,panel,back,music,sound,box)
        tSett.apply { setBounds(164f, HEIGHT_UI, 735f, 174f) }
        panel.apply { setBounds(126f, -1187f, 811f, 1187f) }
        music.apply {
            setBounds(277f, 700f, 183f, 640f)

            progressPercentFlow.value = screen.game.musicUtil.volumeLevelFlow.value

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        screen.game.musicUtil.volumeLevelFlow.value = it
                    }
                }
            }
        }
        sound.apply {
            setBounds(602f, 700f, 183f, 640f)

            progressPercentFlow.value = screen.game.soundUtil.volumeLevel

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        screen.game.soundUtil.volumeLevel = it
                    }
                }
            }
        }
        box.apply {
            setBounds(596f, 518f, 194f, 80f)
            if (isVIBRO.not()) check(false)
            setOnCheckListener { isCheck -> isVIBRO = isCheck.not() }
        }
        back.apply {
            setBounds(313f, -201f, 436f, 201f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun animShowScreen() {
        val time = 0.5f
        tSett.addAction(Actions.moveTo(164f, 1622f, time, Interpolation.swingOut))
        panel.addAction(Actions.moveTo(126f, 374f, time, Interpolation.swingOut))
        back.addAction(Actions.moveTo(313f, 82f, time, Interpolation.swingOut))
    }

    private fun animHideScreen(block: () -> Unit) {
        val time = 0.5f

        music.animHide(0.5f)
        sound.animHide(0.5f)
        box.animHide(0.5f) {
            tSett.addAction(Actions.moveTo(164f, HEIGHT_UI, time, Interpolation.swingIn))
            panel.addAction(Actions.moveTo(126f, -1187f, time, Interpolation.swingIn))

            back.addAction(Actions.sequence(
                Actions.moveTo(313f, -201f, time, Interpolation.swingIn),
                Actions.run { block() }
            ))
        }
    }

}