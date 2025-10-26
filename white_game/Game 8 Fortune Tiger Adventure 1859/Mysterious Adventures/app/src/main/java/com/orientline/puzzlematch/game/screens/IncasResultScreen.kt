package com.orientline.puzzlematch.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.orientline.puzzlematch.game.LibGDXGame
import com.orientline.puzzlematch.game.screens.common.IncasLevelScreen
import com.orientline.puzzlematch.game.screens.common.IncasMenuScreen
import com.orientline.puzzlematch.game.utils.actor.setOnClickListener
import com.orientline.puzzlematch.game.utils.advanced.AdvancedScreen
import com.orientline.puzzlematch.game.utils.advanced.AdvancedStage
import com.orientline.puzzlematch.game.utils.region

class IncasResultScreen(override val game: LibGDXGame): AdvancedScreen() {

    companion object {
        var isWin = true
    }

    override fun show() {
        setBackgrounds(if (isWin) game.allAssets.IncasPerfectly.region else game.allAssets.IncasVeryBad.region)
        super.show()
        if (isWin) game.soundUtil.apply { play(goodresult) } else game.soundUtil.apply { play(wrong_answer) }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        val play = Actor()
        val exit = Actor()
        addActors(play, exit)

        play.apply {
            setBounds(304f, 912f, 494f, 158f)
            setOnClickListener(game.soundUtil) { game.navigationManager.navigate(IncasLevelScreen::class.java.name) }
        }
        exit.apply {
            setBounds(304f, 695f, 494f, 158f)
            setOnClickListener(game.soundUtil) { game.navigationManager.navigate(IncasMenuScreen::class.java.name) }
        }
    }

}