package com.puzdever.puzsweet.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzdever.puzsweet.game.actors.button.AButton
import com.puzdever.puzsweet.game.actors.checkbox.ACheckBox
import com.puzdever.puzsweet.game.screens.RulesScreen
import com.puzdever.puzsweet.game.utils.Block
import com.puzdever.puzsweet.game.utils.TIME_ANIM_SCREEN
import com.puzdever.puzsweet.game.utils.actor.animDelay
import com.puzdever.puzsweet.game.utils.actor.animHide
import com.puzdever.puzsweet.game.utils.actor.animMoveTo
import com.puzdever.puzsweet.game.utils.actor.animShow
import com.puzdever.puzsweet.game.utils.actor.setOnClickListener
import com.puzdever.puzsweet.game.utils.advanced.AdvancedMainGroup
import com.puzdever.puzsweet.game.utils.gdxGame

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules = Image(gdxGame.assetsAll.RULES)
    private val btnX     = AButton(screen, AButton.Type.X)
//    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
//    private val snd      = ACheckBox(screen, ACheckBox.Type.Sod)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBtnX()
//        addMus()
//        addSod()

        animShowMain {
            btnX.animMoveTo(btnX.x, 1728f, 0.5f)
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(67f, 376f, 947f, 1169f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(71f, 2000f, 133f, 133f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }
//
//    private fun addMus() {
//        addActor(mus)
//        mus.setBounds(40f, 1761f, 100f, 100f)
//        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) mus.check()
//        mus.setOnCheckListener {
//            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
//        }
//    }
//
//    private fun addSod() {
//        addActor(snd)
//        snd.setBounds(925f, 1761f, 100f, 100f)
//        if (gdxGame.soundUtil.isPause) snd.check()
//        snd.setOnCheckListener {
//            gdxGame.soundUtil.isPause = it
//        }
//    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}