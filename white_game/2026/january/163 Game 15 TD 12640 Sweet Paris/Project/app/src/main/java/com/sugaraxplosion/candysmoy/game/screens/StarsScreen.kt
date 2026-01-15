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

class StarsScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font75        = fontGenerator_Rowdies.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(75))

    private val imgLogo = Image(game.all.RECORDS)
    private val btnExit = AButton(this, AButton.Static.Type.Exit)
    private val lblStar = Label("${game.sharedPreferences.getInt("stars", 0)}", Label.LabelStyle(font75, GColor.text))

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.BLUE.region)
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
        imgLogo.setBounds(68f, 0f, 403f, 813f)

        addActor(lblStar)
        lblStar.setBounds(140f, 370f, 259f, 93f)
        lblStar.setAlignment(Align.center)
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
    }

}