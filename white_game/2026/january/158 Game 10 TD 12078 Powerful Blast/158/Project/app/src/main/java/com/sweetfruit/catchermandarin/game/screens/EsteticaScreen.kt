package com.sweetfruit.catchermandarin.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sweetfruit.catchermandarin.game.PoromGame
import com.sweetfruit.catchermandarin.game.actors.button.AButton
import com.sweetfruit.catchermandarin.game.actors.checkbox.ACheckBox
import com.sweetfruit.catchermandarin.game.utils.Poshlo_VREMA
import com.sweetfruit.catchermandarin.game.utils.actor.animHide
import com.sweetfruit.catchermandarin.game.utils.actor.animShow
import com.sweetfruit.catchermandarin.game.utils.advanced.AdvancedScreen
import com.sweetfruit.catchermandarin.game.utils.advanced.AdvancedStage
import com.sweetfruit.catchermandarin.game.utils.region

class EsteticaScreen(override val game: PoromGame): AdvancedScreen() {

    // Actor
    private val soundBox = ACheckBox(this, ACheckBox.Static.Type.Posrate)
    private val musicBox = ACheckBox(this, ACheckBox.Static.Type.Posrate)

    private val aMenu = AButton(this, AButton.Static.Type.Menu)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.guglas.lodrinking.region)
        super.show()
        stageUI.root.animShow(Poshlo_VREMA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenu()

        addActor(Image(game.faradeo.musical).apply {
            setBounds(65f, 183f, 533f, 533f)
        })
        addActor(Image(game.faradeo.soundel).apply {
            setBounds(929f, 183f, 533f, 533f)
        })

        addKnopki()
    }

    // ------------------------------------------------------------------------
    // Create A
    // ----------------------------------------
    private fun AdvancedStage.addMenu() {
        addActors(aMenu)

        aMenu.apply {
            setBounds(681f, 367f, 165f, 165f)
            setOnClickListener {
                stageUI.root.animHide(Poshlo_VREMA) { game.navigationManager.back() }
            }
        }
    }

    private fun AdvancedStage.addKnopki() {
        addActors(musicBox,soundBox)
        musicBox.apply {
            setBounds(287f, 265f, 90f, 74f)

            if (game.musicUtil.music?.isPlaying == true) check(false)

            setOnCheckListener {
                if (it) game.musicUtil.music?.play()
                else game.musicUtil.music?.pause()
            }
        }
        soundBox.apply {
            setBounds(1149f, 265f, 90f, 74f)

            if (game.soundUtil.isPause.not()) check(false)

            setOnCheckListener {
                game.soundUtil.isPause = it.not()
            }
        }
    }

}