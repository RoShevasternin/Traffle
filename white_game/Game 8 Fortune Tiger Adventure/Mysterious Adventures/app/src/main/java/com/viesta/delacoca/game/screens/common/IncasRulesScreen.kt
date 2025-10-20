package com.viesta.delacoca.game.screens.common

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viesta.delacoca.game.LibGDXGame
import com.viesta.delacoca.game.utils.advanced.AdvancedGroup

class IncasRulesScreen(_game: LibGDXGame): ICommonScreen(_game, Static.TypeScreen.Menu) {

    private val rulesTextImg = Image(game.allAssets.rules_text)

    override fun AdvancedGroup.addActorsOnTmpGroup() {
        addRulesTextImg()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedGroup.addRulesTextImg() {
        addActor(rulesTextImg)
        rulesTextImg.setBounds(284f, 586f, 537f, 569f)
    }

}