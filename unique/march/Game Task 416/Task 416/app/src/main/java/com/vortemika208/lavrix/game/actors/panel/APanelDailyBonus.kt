package com.vortemika208.lavrix.game.actors.panel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.vortemika208.lavrix.game.utils.actor.addAndFillActor
import com.vortemika208.lavrix.game.utils.advanced.AdvancedGroup
import com.vortemika208.lavrix.game.utils.advanced.AdvancedScreen
import com.vortemika208.lavrix.game.utils.font.FontParameter
import com.vortemika208.lavrix.game.utils.gdxGame
import com.vortemika208.lavrix.game.utils.runGDX
import com.vortemika208.lavrix.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class APanelDailyBonus(override val screen: AdvancedScreen): AdvancedGroup() {

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + ":").setSize(58)
    private val font   = screen.fontGenerator_Regular.generateFont(params)

    private val aPanelImg = Image(gdxGame.assetsAll.DAILY_BONUS)
    private val aTimeLbl  = Label("00:00:00", Label.LabelStyle(font, Color.WHITE))

    var blockBonusAvailable = {}

    override fun addActorsOnGroup() {
        addAndFillActor(aPanelImg)
        addCoinLbl()

        startTimer()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addCoinLbl() {
        addActor(aTimeLbl)
        aTimeLbl.setBounds(183f, 63f, 157f, 70f)
        aTimeLbl.setAlignment(Align.center)
    }

    // ------------------------------------------------------------------------
    // Helper
    // ------------------------------------------------------------------------
    fun formatTime(ms: Long): String {

        val totalSeconds = ms / 1000

        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return "%02d:%02d:%02d".format(hours, minutes, seconds)
    }

    private fun startTimer() {
        coroutine?.launch {
            while (isActive) {
                val remaining = gdxGame.modelPlayer.getRemainingDailyTime()
                runGDX {
                    if (remaining == 0L) blockBonusAvailable()
                    aTimeLbl.setText(formatTime(remaining))
                }
                delay(1000)
            }
        }
    }

}