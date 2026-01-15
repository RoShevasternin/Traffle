package com.sugaraxplosion.candysmoy.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.sugaraxplosion.candysmoy.game.LibGDXGame
import com.sugaraxplosion.candysmoy.game.actors.AButton
import com.sugaraxplosion.candysmoy.game.box2d.WorldUtil
import com.sugaraxplosion.candysmoy.game.utils.GColor
import com.sugaraxplosion.candysmoy.game.utils.TIME_ANIM
import com.sugaraxplosion.candysmoy.game.utils.actor.animHide
import com.sugaraxplosion.candysmoy.game.utils.actor.animShow
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedBox2dScreen
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedStage
import com.sugaraxplosion.candysmoy.game.utils.font.FontParameter
import com.sugaraxplosion.candysmoy.game.utils.region

class ShopScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font57        = fontGenerator_Rowdies.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(57))

    private var bombs = game.sharedPreferences.getInt("bomb", 0)

    private val imgGirl = Image(game.all.GIRL)
    private val imgBomb = Image(game.all.bomb_counter)
    private val imgMax  = Image(game.all.max)
    private val btnExit = AButton(this, AButton.Static.Type.Exit)
    private val btnPlus = AButton(this, AButton.Static.Type.Plus)
    private val lblBomb = Label(bombs.toString(), Label.LabelStyle(font57, GColor.text))

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.LOAD.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgLogo()
        addBtns()
    }

    private fun AdvancedStage.addImgLogo() {
        addActor(imgGirl)
        imgGirl.setBounds(8f, 1f, 280f, 327f)

        addActor(imgBomb)
        imgBomb.setBounds(138f, 416f, 264f, 347f)

        addActor(imgMax)
        imgMax.setBounds(168f, 853f, 224f, 76f)

        addActor(lblBomb)
        lblBomb.setBounds(249f, 423f, 55f, 71f)
        lblBomb.setAlignment(Align.center)
    }

    private fun AdvancedStage.addBtns() {
        addActor(btnExit)
        btnExit.apply {
            setBounds(42f, 851f, 70f, 79f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.back()
                }
            }
        }


        addActor(btnPlus)
        btnPlus.apply {
            setBounds(312f, 60f, 170f, 179f)
            setOnClickListener {
                if (bombs < 5) {
                    game.soundUtil.apply { play(plus) }
                    bombs++
                    lblBomb.setText(bombs)
                    game.sharedPreferences.edit().putInt("bomb", bombs).apply()
                } else {
                    game.soundUtil.apply { play(click) }
                }
            }
        }
    }

}