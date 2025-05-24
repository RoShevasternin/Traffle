package com.candies.balloons.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.candies.balloons.game.screens.GameScreen
import com.candies.balloons.game.screens.PlayScreen
import com.candies.balloons.game.utils.TIME_ANIM
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.advanced.AdvancedGroup
import com.candies.balloons.game.utils.advanced.AdvancedScreen

class AHTP(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return 3076f
    }

    init {
        setSize( 1062f,3076f,)
    }

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.all.howtoplay))

        val play = AButton(screen, AButton.Static.Type.Play)
        addActor(play)
        play.apply {
            setBounds(285f, 0f, 492f, 231f)
            setOnClickListener {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(PlayScreen::class.java.name)
                }
            }
        }
    }

}