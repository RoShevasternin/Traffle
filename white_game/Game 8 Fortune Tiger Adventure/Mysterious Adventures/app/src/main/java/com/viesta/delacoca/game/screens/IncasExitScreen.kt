package com.viesta.delacoca.game.screens

import com.viesta.delacoca.game.LibGDXGame
import com.viesta.delacoca.game.utils.advanced.AdvancedScreen

class IncasExitScreen(override val game: LibGDXGame): AdvancedScreen() {

    override fun show() {
        game.navigationManager.exit()
        super.show()
    }

}