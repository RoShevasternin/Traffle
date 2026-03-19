package com.veldan.cosmolot.game.actors.label

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.veldan.cosmolot.game.manager.FontTTFManager

object ALabelStyle {

    val bowler_15_white get() = Label.LabelStyle(FontTTFManager.BOWLERFont.font_15.font, Color.WHITE)
    val bowler_20_white get() = Label.LabelStyle(FontTTFManager.BOWLERFont.font_20.font, Color.WHITE)
    val bowler_25_white get() = Label.LabelStyle(FontTTFManager.BOWLERFont.font_25.font, Color.WHITE)
    val bowler_30_white get() = Label.LabelStyle(FontTTFManager.BOWLERFont.font_30.font, Color.WHITE)
    val bowler_40_white get() = Label.LabelStyle(FontTTFManager.BOWLERFont.font_40.font, Color.WHITE)

}