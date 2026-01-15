package com.crystalboom.copaliny.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.crystalboom.copaliny.game.LibGDXGame
import com.crystalboom.copaliny.game.actors.AButton
import com.crystalboom.copaliny.game.box2d.WorldUtil
import com.crystalboom.copaliny.game.utils.GColor
import com.crystalboom.copaliny.game.utils.TIME_ANIM
import com.crystalboom.copaliny.game.utils.actor.animHide
import com.crystalboom.copaliny.game.utils.actor.animShow
import com.crystalboom.copaliny.game.utils.actor.setOnClickListener
import com.crystalboom.copaliny.game.utils.advanced.AdvancedBox2dScreen
import com.crystalboom.copaliny.game.utils.advanced.AdvancedStage
import com.crystalboom.copaliny.game.utils.font.FontParameter
import com.crystalboom.copaliny.game.utils.region

class ShopScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font57        = fontGenerator_Bananas.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(90))

    private var bombs = game.sharedPreferences.getInt("bomb", 0)

    private val imgGirl = Image(game.all.king)
    private val imgBomb = Image(game.all.svecha)
    private val btnExit = AButton(this, AButton.Static.Type.Bck)
    private val btnPlus = Actor()
    private val lblBomb = Label("$bombs/10", Label.LabelStyle(font57, GColor.text))

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.bgs.random().region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgLogo()
        addBtns()
    }

    private fun AdvancedStage.addImgLogo() {
        addActor(imgGirl)
        imgGirl.setBounds(0f, 0f, 404f, 635f)

        addActor(imgBomb)
        imgBomb.setBounds(123f, 261f, 574f, 960f)

        addActor(lblBomb)
        lblBomb.setBounds(505f, 606f, 147f, 104f)
        lblBomb.setAlignment(Align.center)
    }

    private fun AdvancedStage.addBtns() {
        addActor(btnExit)
        btnExit.apply {
            setBounds(469f, 34f, 220f, 148f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.back()
                }
            }
        }


        addActor(btnPlus)
        btnPlus.apply {
            setBounds(461f, 261f, 236f, 237f)
            setOnClickListener {
                if (bombs < 10) {
                    game.soundUtil.apply { play(plus) }
                    bombs++
                    lblBomb.setText("$bombs/10")
                    game.sharedPreferences.edit().putInt("bomb", bombs).apply()
                } else {
                    game.soundUtil.apply { play(click) }
                }
            }
        }
    }

}