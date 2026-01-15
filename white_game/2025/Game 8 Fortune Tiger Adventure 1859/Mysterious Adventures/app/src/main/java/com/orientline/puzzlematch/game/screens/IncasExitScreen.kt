package com.orientline.puzzlematch.game.screens

import com.orientline.puzzlematch.game.LibGDXGame
import com.orientline.puzzlematch.game.utils.advanced.AdvancedScreen

class IncasExitScreen(override val game: LibGDXGame): AdvancedScreen() {

    override fun show() {
        game.navigationManager.exit()
        super.show()
    }

}