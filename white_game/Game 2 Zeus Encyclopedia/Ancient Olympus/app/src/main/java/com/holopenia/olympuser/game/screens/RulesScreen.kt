package com.holopenia.olympuser.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.holopenia.olympuser.game.LibGDXGame
import com.holopenia.olympuser.game.actors.AButton
import com.holopenia.olympuser.game.utils.GColor
import com.holopenia.olympuser.game.utils.TIME_ANIM
import com.holopenia.olympuser.game.utils.WIDTH_UI
import com.holopenia.olympuser.game.utils.actor.animHideScreen
import com.holopenia.olympuser.game.utils.actor.animShowScreen
import com.holopenia.olympuser.game.utils.advanced.AdvancedScreen
import com.holopenia.olympuser.game.utils.advanced.AdvancedStage
import com.holopenia.olympuser.game.utils.region

class RulesScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val btnBack  = AButton(this, AButton.Static.Type.Back)
    private val btnSett  = AButton(this, AButton.Static.Type.Settings)
    private val btnBegin = AButton(this, AButton.Static.Type.BeginTutorial)
    private val imgPanel = Image(game.all.WELCOME_TZE)

    override fun show() {
        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(game.all.BACKGROUND_MAIN.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        stageBack.addAndFillActor(Image(drawerUtil.getRegion(Color.BLACK.cpy().apply { a = 0.4f })))
        addBtnBackAndSett()
        addImgPanel()
        addBtnBegin()
    }

    private fun AdvancedStage.addBtnBackAndSett() {
        addActors(btnBack, btnSett)
        btnBack.setBounds(38f,1744f,188f,104f)
        btnBack.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
        btnSett.setBounds(894f,1734f,136f,142f)
        btnSett.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(SettingsScreen::class.java.name, RulesScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(104f,1014f,871f,335f)
    }

    private fun AdvancedStage.addBtnBegin() {
        addActor(btnBegin)
        btnBegin.setBounds(155f,679f,770f,223f)
        btnBegin.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(TutorialScreen::class.java.name, RulesScreen::class.java.name)
            }
        }
    }


}