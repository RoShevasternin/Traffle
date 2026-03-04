package com.tropical.coinflight.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.tropical.coinflight.game.screens.GameScreen
import com.tropical.coinflight.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.tropical.coinflight.game.utils.actor.animHide
import com.tropical.coinflight.game.utils.actor.setOnClickListener
import com.tropical.coinflight.game.utils.advanced.AdvancedGroup
import com.tropical.coinflight.game.utils.advanced.AdvancedScreen
import com.tropical.coinflight.game.utils.font.FontParameter

class AResultGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(80)
    private val font          = screen.fontGenerator_Averta.generateFont(fontParameter)

    private val imgBackground = Image()
    private val coinLbl       = Label("0", Label.LabelStyle(font, Color.WHITE))


    override fun addActorsOnGroup() {
        touchable = Touchable.disabled
        addAndFillActor(imgBackground)
        addBtns()

        addActor(coinLbl)
        coinLbl.apply {
            setBounds(541f, 236f, 355f, 102f)
            setAlignment(Align.center)
        }
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addBtns() {
        val menu    = Actor()
        val next    = Actor()

        addActors(menu, next)

        menu.apply {
            setBounds(467f, 67f, 91f, 91f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { screen.game.navigationManager.back() }
            }
        }
        next.apply {
            setBounds(607f, 67f, 227f, 91f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { screen.game.navigationManager.navigate(GameScreen::class.java.name) }
            }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun update(isWin: Boolean, coins: Int) {
        coinLbl.setText(coins)
        if (isWin) {
            imgBackground.drawable = TextureRegionDrawable(screen.game.allAssets.win)
            screen.game.soundUtil.apply { play(win) }
        } else {
            imgBackground.drawable = TextureRegionDrawable(screen.game.allAssets.lose)
            screen.game.soundUtil.apply { play(lose) }
        }
    }

}