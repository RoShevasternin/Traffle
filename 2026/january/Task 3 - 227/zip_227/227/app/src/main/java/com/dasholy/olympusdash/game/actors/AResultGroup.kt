package com.dasholy.olympusdash.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.dasholy.olympusdash.game.screens.GLOBAL_coinCount
import com.dasholy.olympusdash.game.screens.GameScreen
import com.dasholy.olympusdash.game.screens.MenuScreen
import com.dasholy.olympusdash.game.screens.RulesScreen
import com.dasholy.olympusdash.game.screens.SettingsScreen
import com.dasholy.olympusdash.game.screens.ShopScreen
import com.dasholy.olympusdash.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.dasholy.olympusdash.game.utils.actor.animHide
import com.dasholy.olympusdash.game.utils.actor.setBounds
import com.dasholy.olympusdash.game.utils.actor.setOnClickListener
import com.dasholy.olympusdash.game.utils.advanced.AdvancedGroup
import com.dasholy.olympusdash.game.utils.advanced.AdvancedScreen
import com.dasholy.olympusdash.game.utils.font.FontParameter

class AResultGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars+":").setSize(62)
    private val font          = screen.fontGenerator_Akshar.generateFont(fontParameter)

    private val timeLbl = Label("$GLOBAL_coinCount", Label.LabelStyle(font, Color.WHITE))

    private val imgBackground = Image()

    override fun addActorsOnGroup() {
        touchable = Touchable.disabled
        addAndFillActor(imgBackground)
        addBtns()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addBtns() {
        val panel = Image(screen.game.gameAssets.new_result_pan)
        addActor(panel)
        panel.setBounds(80f, 258f, 488f, 670f)

        val names = listOf(
            GameScreen::class.java.name,
            ShopScreen::class.java.name,
            RulesScreen::class.java.name,
            SettingsScreen::class.java.name,
            "exit",
        )

        var ny = 723f

        names.onEach { sName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(192f, ny, 265f, 75f)
            ny -= 75f + 27f

            btn.setOnClickListener(screen.game.soundUtil) {
                navigateGo(sName)
            }
        }

        addActor(timeLbl)
        timeLbl.setAlignment(Align.right)
        timeLbl.setBounds(315f, 838f, 126f, 86f)
    }

    private fun navigateGo(sName: String) {
        screen.game.navigationManager.backStack.clear()

        screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
            if (sName == "exit") screen.game.navigationManager.exit()
            else screen.game.navigationManager.navigate(sName, MenuScreen::class.java.name)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun update(isWin: Boolean) {
        timeLbl.setText("$GLOBAL_coinCount")
        if (isWin) {
            val imgTXT = Image(screen.game.gameAssets.new_w)
            addActor(imgTXT)
            imgTXT.setBounds(196f, 1038f, 255f, 206f)

            imgBackground.drawable = TextureRegionDrawable(screen.game.gameAssets.win)
            screen.game.soundUtil.apply { play(bonus) }
        } else {
            val imgTXT = Image(screen.game.gameAssets.new_lose)
            addActor(imgTXT)
            imgTXT.setBounds(176f, 1038f, 294f, 206f)

            imgBackground.drawable = TextureRegionDrawable(screen.game.gameAssets.Fail)
            screen.game.soundUtil.apply { play(lose) }
        }
    }

}