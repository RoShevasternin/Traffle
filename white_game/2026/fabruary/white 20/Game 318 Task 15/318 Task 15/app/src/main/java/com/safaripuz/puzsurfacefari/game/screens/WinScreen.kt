package com.safaripuz.puzsurfacefari.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.safaripuz.puzsurfacefari.game.actors.ATmpGroup
import com.safaripuz.puzsurfacefari.game.utils.Block
import com.safaripuz.puzsurfacefari.game.utils.TIME_ANIM_SCREEN
import com.safaripuz.puzsurfacefari.game.utils.actor.addActorWithConstraints
import com.safaripuz.puzsurfacefari.game.utils.actor.addActors
import com.safaripuz.puzsurfacefari.game.utils.actor.addAndFillActor
import com.safaripuz.puzsurfacefari.game.utils.actor.animDelay
import com.safaripuz.puzsurfacefari.game.utils.actor.animHide
import com.safaripuz.puzsurfacefari.game.utils.actor.animShow
import com.safaripuz.puzsurfacefari.game.utils.actor.setOnClickListener
import com.safaripuz.puzsurfacefari.game.utils.advanced.AdvancedScreen
import com.safaripuz.puzsurfacefari.game.utils.gdxGame

class WinScreen: AdvancedScreen() {

    private val group    = ATmpGroup(this)
    private val imgPanel = Image(gdxGame.assetsAll.WIN_PAN)

    override fun show() {
        gdxGame.soundUtil.apply { play(win_game) }
        setBackBackground(gdxGame.assetsAll.B_WIN)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f
        addGroup()
        animShowScreen()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addGroup() {
        group.setSize(860f, 1201f)
        addActorWithConstraints(group) {
            startToStartOf   = this@addGroup
            endToEndOf       = this@addGroup
            topToTopOf       = this@addGroup
            bottomToBottomOf = this@addGroup
        }

        group.apply {
            addAndFillActor(imgPanel)

            val aRestart = Actor()
            val aHome    = Actor()
            addActors(aRestart, aHome)
            aRestart.setBounds(178f, 368f, 513f, 196f)
            aHome.setBounds(178f, 139f, 513f, 196f)

            aRestart.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
            aHome.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }

    }

}