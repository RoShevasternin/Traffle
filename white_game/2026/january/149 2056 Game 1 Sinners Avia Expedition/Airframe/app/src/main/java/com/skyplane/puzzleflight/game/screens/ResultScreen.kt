package com.skyplane.puzzleflight.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.skyplane.puzzleflight.game.LibGDXGame
import com.skyplane.puzzleflight.game.utils.actor.setOnClickListener
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedScreen
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedStage
import com.skyplane.puzzleflight.game.utils.region

class ResultScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var IS_WIN = true
    }

    override fun show() {
        if (IS_WIN) game.soundUtil.apply { play(bonus) } else game.soundUtil.apply { play(lose) }

        setBackgrounds(if (IS_WIN) game.allAssets.Win.region else game.allAssets.Fail.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBtns()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBtns() {
        val cont = Actor()
        addActor(cont)
        cont.setBounds(198f, 746f, 252f, 110f)
        cont.setOnClickListener(game.soundUtil) {
            animHideScreen { game.navigationManager.navigate(PuzzleScreen::class.java.name) }
        }

        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(259f, 533f, 131f, 131f)
            setOnClickListener(game.soundUtil) { animHideScreen {
                game.navigationManager.clearBackStack()
                game.navigationManager.navigate(MenuScreen::class.java.name)
            } }
        }
    }

}