package com.crystalboom.copaliny.game.screens

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
import com.crystalboom.copaliny.game.utils.advanced.AdvancedBox2dScreen
import com.crystalboom.copaliny.game.utils.advanced.AdvancedStage
import com.crystalboom.copaliny.game.utils.font.FontParameter
import com.crystalboom.copaliny.game.utils.region

class StarsScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font75        = fontGenerator_Bananas.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(95))

    private val imgLogo = Image(game.all.brulik)
    private val imgDed  = Image(game.all.king)
    private val btnExit = AButton(this, AButton.Static.Type.Bck)
    private val lblStar = Label("${game.sharedPreferences.getInt("stars", 0)}", Label.LabelStyle(font75, GColor.text))

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.bgs.random().region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
        game.soundUtil.apply { play(childs_joy, 1f) }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgLogo()
        addBtns()
    }

    private fun AdvancedStage.addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(138f, 694f, 449f, 421f)

        addActor(imgDed)
        imgDed.setBounds(0f, 0f, 404f, 635f)

        addActor(lblStar)
        lblStar.setBounds(271f, 779f, 184f, 109f)
        lblStar.setAlignment(Align.center)
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
    }

}