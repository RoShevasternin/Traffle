package com.bounceques.ternationaret.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bounceques.ternationaret.game.LibGDXGame
import com.bounceques.ternationaret.game.actors.checkbox.ACheckBox
import com.bounceques.ternationaret.game.utils.TIME_ANIM
import com.bounceques.ternationaret.game.utils.actor.animHide
import com.bounceques.ternationaret.game.utils.actor.animShow
import com.bounceques.ternationaret.game.utils.actor.setOnClickListener
import com.bounceques.ternationaret.game.utils.actor.setPosition
import com.bounceques.ternationaret.game.utils.advanced.AdvancedScreen
import com.bounceques.ternationaret.game.utils.advanced.AdvancedStage
import com.bounceques.ternationaret.game.utils.gdxGame
import com.bounceques.ternationaret.game.utils.region

class PinkMenuScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val panelImg = Image(game.assetsAll.MENU_PAN)
    private val aSoundBox  = ACheckBox(this, ACheckBox.Static.Type.SOUND)
    private val aMusicBox  = ACheckBox(this, ACheckBox.Static.Type.MUSIC)

    override fun show() {
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(game.assetsAll.B1.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addButtons()
        addSoundBox()
        addMusicBox()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActors(panelImg)
        panelImg.setBounds(118f, 361f, 845f, 1198f)
    }

    private fun AdvancedStage.addButtons() {
        var ny = 620f
        arrayOf(
            "EXIT",
            PinkRulesScreen::class.java.name,
            PinkYrowniScreen::class.java.name,
        ).onEach { sName ->
            addActor(Actor().apply {
                setBounds(268f, ny, 544f, 137f)
                ny += (80+137)

                setOnClickListener(game.soundUtil) {
                    if (sName == "EXIT") game.navigationManager.exit()
                    else stageUI.root.animHide(TIME_ANIM) {
                        game.navigationManager.navigate(sName, PinkMenuScreen::class.java.name)
                    }
                }
            })
        }
    }

    private fun AdvancedStage.addSoundBox() {
        aSoundBox.setSize(156f, 165f)
        addActor(aSoundBox)
        aSoundBox.setPosition(68f, 1707f)

        if (gdxGame.soundUtil.isPause) aSoundBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }

    }

    private fun AdvancedStage.addMusicBox() {
        aMusicBox.setSize(156f, 165f)
        addActor(aMusicBox)
        aMusicBox.setPosition(848f, 1707f)

        if (gdxGame.musicUtil.music?.isPlaying == false) aMusicBox.check()

        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.music?.pause() else gdxGame.musicUtil.music?.play()
        }

    }

}