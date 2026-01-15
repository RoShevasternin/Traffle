package com.flightcoll.bridgertons.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.flightcoll.bridgertons.game.SuberGame
import com.flightcoll.bridgertons.game.actors.button.AButton
import com.flightcoll.bridgertons.game.actors.checkbox.ACheckBox
import com.flightcoll.bridgertons.game.utils.actor.animHide
import com.flightcoll.bridgertons.game.utils.actor.animShow
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedScreen
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedStage
import com.flightcoll.bridgertons.game.utils.region
import com.flightcoll.bridgertons.game.utils.vremia_ANIM

class StertoyScreen(override val game: SuberGame): AdvancedScreen() {

    companion object {
        var isX_15 = true
        var isX_25 = true
        var isX_60 = true
        var isX_105 = true
    }

    // Actor
    private val aBack = AButton(this, AButton.Static.Type.mEnU)

    private val musicBox = ACheckBox(this, ACheckBox.Static.Type.DEFAULT)
    private val soundBox = ACheckBox(this, ACheckBox.Static.Type.DEFAULT)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.fisters.zagruzon.region)
        super.show()
        stageUI.root.animShow(vremia_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(Image(game.assets.musishion).apply {
            setBounds(340f, 187f, 918f, 682f)
        })

        addBack()

        val x15 = ACheckBox(this@StertoyScreen, ACheckBox.Static.Type.DEFAULT)
        val x25 = ACheckBox(this@StertoyScreen, ACheckBox.Static.Type.DEFAULT)
        val x60 = ACheckBox(this@StertoyScreen, ACheckBox.Static.Type.DEFAULT)
        val x105 = ACheckBox(this@StertoyScreen, ACheckBox.Static.Type.DEFAULT)
        addActors(x15, x25, x60, x105)
        x15.apply {
            setBounds(565f, 644f, 77f, 77f)
            if (isX_15) check(false)
            setOnCheckListener { isX_15 = it }
        }
        x25.apply {
            setBounds(565f, 523f, 77f, 77f)
            if (isX_25) check(false)
                setOnCheckListener { isX_25 = it }
        }
        x60.apply {
            setBounds(565f, 403f, 77f, 77f)
            if (isX_60) check(false)
                setOnCheckListener { isX_60 = it }
        }
        x105.apply {
            setBounds(565f, 283f, 77f, 77f)
            if (isX_105) check(false)
                setOnCheckListener { isX_105 = it }
        }

        addTwo()

    }

    // ------------------------------------------------------------------------
    // Create Add Actor
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addBack() {
        addActors(aBack)

        aBack.apply {
            setBounds(96f, 458f, 156f, 92f)
            setOnClickListener {
                stageUI.root.animHide(vremia_ANIM) { game.navigationManager.back() }
            }
        }
    }

    private fun AdvancedStage.addTwo() {
        addActors(musicBox, soundBox)

        musicBox.apply {
            setBounds(897f, 523f, 77f, 77f)

            if (game.musicUtil.music?.isPlaying == true) check(false)

            setOnCheckListener {
                if (it) game.musicUtil.music?.play()
                else game.musicUtil.music?.pause()
            }
        }
        soundBox.apply {
            setBounds(897f, 283f, 77f, 77f)

            if (game.soundUtil.isPause.not()) check(false)

            setOnCheckListener {
                game.soundUtil.isPause = it.not()
            }
        }
    }

}