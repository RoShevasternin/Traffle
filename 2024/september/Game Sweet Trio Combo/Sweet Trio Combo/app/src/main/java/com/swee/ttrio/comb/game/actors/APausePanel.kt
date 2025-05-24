package com.swee.ttrio.comb.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.swee.ttrio.comb.game.screens.GameScreen
import com.swee.ttrio.comb.game.utils.GColor
import com.swee.ttrio.comb.game.utils.TIME_ANIM
import com.swee.ttrio.comb.game.utils.actor.animHide
import com.swee.ttrio.comb.game.utils.actor.setOnClickListener
import com.swee.ttrio.comb.game.utils.advanced.AdvancedGroup
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.disable

class APausePanel(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val panel    = Image(screen.game.all.PAUSE_PANEL)
    private val aPlay    = Actor()
    private val aMenu    = Actor()
    private val aRestart = Actor()

    var playBlock = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getRegion(GColor.pause)))
        addActors(panel, aPlay, aMenu, aRestart)
        panel.setBounds(20f,220f,351f,404f)

        aPlay.apply {
            setBounds(130f,412f,130f,130f)
            setOnClickListener(screen.game.soundUtil) {
                this@APausePanel.animHide(TIME_ANIM) {
                    this@APausePanel.disable()
                    playBlock()
                }
            }
        }
        aMenu.apply {
            setBounds(79f,284f,94f,94f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.back()
                }
            }
        }
        aRestart.apply {
            setBounds(216f,284f,94f,94f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }
    }

}