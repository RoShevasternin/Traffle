package com.smallmini.races.gonca.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.smallmini.races.gonca.game.LibGDXGame
import com.smallmini.races.gonca.game.actors.checkbox.ACheckBox
import com.smallmini.races.gonca.game.utils.TIME_ANIM
import com.smallmini.races.gonca.game.utils.actor.animHide
import com.smallmini.races.gonca.game.utils.actor.animShow
import com.smallmini.races.gonca.game.utils.actor.setBounds
import com.smallmini.races.gonca.game.utils.actor.setOnClickListener
import com.smallmini.races.gonca.game.utils.advanced.AdvancedScreen
import com.smallmini.races.gonca.game.utils.advanced.AdvancedStage
import com.smallmini.races.gonca.game.utils.region

class MiniSettScreen(override val game: LibGDXGame): AdvancedScreen() {

    // Actor
    private val imgMenu = Image(game.allAssets.sett)

    private val boxMus = ACheckBox(this, ACheckBox.Static.Type.MUSIC)
    private val boxSou = ACheckBox(this, ACheckBox.Static.Type.SOUND)

    override fun show() {
        setBackBackground(game.loaderAssets.mini.region)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgMenu)
        imgMenu.setBounds(192f, 482f, 697f, 957f)

        val actor = Actor()
        addActor(actor)
        actor.setBounds(285f, 482f, 511f, 221f)

        actor.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            }
        }



        addActors(boxMus, boxSou)
        boxMus.setBounds(634f, 872f, 176f, 176f)
        boxSou.setBounds(243f, 826f, 268f, 268f)

        game.musicUtil.music?.let { mmm ->
            if (mmm.isPlaying.not()) boxMus.check(false)

            boxMus.setOnCheckListener { if (it) {
                mmm.pause()
            } else {
                mmm.play()
            } }
        }

        if (game.soundUtil.isPause) boxSou.check(false)
        boxSou.setOnCheckListener { game.soundUtil.isPause = it }
    }

}