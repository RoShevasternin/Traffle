package com.bunnypanny.eggcatch.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bunnypanny.eggcatch.game.GDXGame
import com.bunnypanny.eggcatch.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.bunnypanny.eggcatch.game.utils.actor.animHide
import com.bunnypanny.eggcatch.game.utils.actor.animShow
import com.bunnypanny.eggcatch.game.utils.actor.setOnClickListener
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedScreen
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedStage
import com.bunnypanny.eggcatch.game.utils.gdxGame
import com.bunnypanny.eggcatch.game.utils.region

class MenuScreen(override val game: GDXGame) : AdvancedScreen() {

    private val aMenuImg  = Image(gdxGame.assetsAll.MENU_PAN)
    private val aBunnyImg = Image(gdxGame.assetsAll.BUNNY)

    private val aPlayBtn      = Actor()
    private val aSettingsBtn  = Actor()
    private val aRulesBtn     = Actor()

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.assetsAll.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenu()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addMenu() {
        addActor(aMenuImg)
        aMenuImg.setBounds(216f, 516f, 648f, 887f)

        addActor(aBunnyImg)
        aBunnyImg.setBounds(571f, -114f, 585f, 904f)

        addActors(aPlayBtn, aSettingsBtn, aRulesBtn)
        aPlayBtn    .setBounds(216f, 1128f, 647f, 274f)
        aSettingsBtn.setBounds(216f, 822f, 647f, 274f)
        aRulesBtn   .setBounds(216f, 516f, 647f, 274f)

        aPlayBtn.setOnClickListener(game.soundUtil) { game.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) }
        aSettingsBtn.setOnClickListener(game.soundUtil) { game.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name) }
        aRulesBtn.setOnClickListener(game.soundUtil) { game.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) }

    }

}