package com.greciao.candyrocket.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.greciao.candyrocket.game.screens.GameScreen
import com.greciao.candyrocket.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.greciao.candyrocket.game.utils.actor.animHide
import com.greciao.candyrocket.game.utils.actor.setOnClickListener
import com.greciao.candyrocket.game.utils.advanced.AdvancedGroup
import com.greciao.candyrocket.game.utils.advanced.AdvancedScreen
import com.greciao.candyrocket.game.utils.font.FontParameter
import com.greciao.candyrocket.game.utils.region

class AResultGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(80)
    private val font          = screen.fontGenerator_Averta.generateFont(fontParameter)

    private val imgBackground = Image()
    private val coinLbl       = Label("0", Label.LabelStyle(font, Color.WHITE))

    val a = Image()

    override fun addActorsOnGroup() {
        touchable = Touchable.disabled
        imgBackground.color.a = 0.5f

        addAndFillActor(imgBackground)

        addActor(a)
        a.setBounds(386f, 67f, 527f, 482f)

        addBtns()

        coinLbl.apply {
            setBounds(622f, 253f, 191f, 93f)
            setAlignment(Align.right)
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
            screen.setBackBackground(screen.game.allAssets.win.region)
            screen.game.soundUtil.apply { play(win) }

            a.drawable = TextureRegionDrawable(screen.game.allAssets.new_w)
            imgBackground.drawable = TextureRegionDrawable(screen.game.allAssets.win)

        } else {
            screen.setBackBackground(screen.game.allAssets.lose.region)
            screen.game.soundUtil.apply { play(lose) }

            a.drawable = TextureRegionDrawable(screen.game.allAssets.new_l)
            imgBackground.drawable = TextureRegionDrawable(screen.game.allAssets.lose)
        }

        addActor(coinLbl)

    }

}