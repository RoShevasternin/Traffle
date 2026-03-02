package com.shoote.maniapink.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.shoote.maniapink.game.utils.actor.setBounds
import com.shoote.maniapink.game.utils.actor.setOnClickListener
import com.shoote.maniapink.game.utils.advanced.AdvancedGroup
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen

class APause(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val imgPanel = Image(screen.game.all.pause)

    var blockPlay    = {}
    var blockRestart = {}
    var blockHome    = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)

        val aPlay    = Actor()
        val aRestart = Actor()
        val aHome    = Actor()
        val aX       = Actor()
        addActors(aPlay, aRestart, aHome, aX)

        aPlay.apply {
            setBounds(297f, 323f, 279f, 279f)
            setOnClickListener(screen.game.soundUtil) {
                blockPlay()
            }
        }
        aRestart.apply {
            setBounds(175f, 112f, 186f, 186f)
            setOnClickListener(screen.game.soundUtil) {
                blockRestart()
            }
        }
        aHome.apply {
            setBounds(518f, 114f, 184f, 184f)
            setOnClickListener(screen.game.soundUtil) {
                blockHome()
            }
        }
        aX.apply {
            setBounds(741f, 645f, 130f, 130f)
            setOnClickListener(screen.game.soundUtil) {
                blockPlay()
            }
        }
    }

}