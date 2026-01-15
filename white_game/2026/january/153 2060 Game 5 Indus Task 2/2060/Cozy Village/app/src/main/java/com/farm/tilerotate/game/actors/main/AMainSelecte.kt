package com.farm.tilerotate.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.farm.tilerotate.game.actors.AProgress
import com.farm.tilerotate.game.actors.button.AButton
import com.farm.tilerotate.game.actors.checkbox.ACheckBox
import com.farm.tilerotate.game.actors.checkbox.ACheckBoxGroup
import com.farm.tilerotate.game.screens.GameScreen
import com.farm.tilerotate.game.screens.SelecteScreen
import com.farm.tilerotate.game.screens.SettScreen
import com.farm.tilerotate.game.utils.Block
import com.farm.tilerotate.game.utils.TIME_ANIM_SCREEN
import com.farm.tilerotate.game.utils.actor.animDelay
import com.farm.tilerotate.game.utils.actor.animHide
import com.farm.tilerotate.game.utils.actor.animMoveTo
import com.farm.tilerotate.game.utils.actor.animShow
import com.farm.tilerotate.game.utils.actor.setOnClickListener
import com.farm.tilerotate.game.utils.advanced.AdvancedMainGroup
import com.farm.tilerotate.game.utils.gdxGame
import com.farm.tilerotate.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainSelecte(override val screen: SelecteScreen): AdvancedMainGroup() {

    companion object {
        var INDEX = 0
            private set
    }

    private val imgRules = Image(gdxGame.assetsAll.SCH)
    private val cb1 = ACheckBox(screen, ACheckBox.Type.DEF)
    private val cb2 = ACheckBox(screen, ACheckBox.Type.DEF)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBoxs()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(0f, 181f, 1109f, 1591f)
    }

    private fun addBoxs() {
        addActors(cb1, cb2)
        cb1.setBounds(233f, 817f, 613f, 588f)
        cb2.setBounds(233f, 181f, 613f, 588f)

        fun navToGame() {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(GameScreen::class.java.name)
            }
        }

        cb1.setOnCheckListener {
            if (it) {
                INDEX = 0
                navToGame()
            }
        }
        cb2.setOnCheckListener {
            if (it) {
                INDEX = 1
                navToGame()
            }
        }
    }

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