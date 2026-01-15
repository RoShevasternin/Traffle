package com.relaxing.memorygame.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.relaxing.memorygame.game.LibGDXGame
import com.relaxing.memorygame.game.actors.ASettingsGroup
import com.relaxing.memorygame.game.actors.ASplashAnimal
import com.relaxing.memorygame.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.relaxing.memorygame.game.utils.actor.animHide
import com.relaxing.memorygame.game.utils.actor.animShow
import com.relaxing.memorygame.game.utils.actor.setOnClickListener
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen
import com.relaxing.memorygame.game.utils.advanced.AdvancedStage
import com.relaxing.memorygame.game.utils.region

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val animal   = ASplashAnimal(this, true)
    private val settings = ASettingsGroup(this).apply { color.a = 0f }
    private val btn      = Image(game.gameAssets.BTN_MENU).apply { color.a = 0f }

    override fun show() {
        setBackgrounds(game.splashAssets.BACGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAnimal()
        addSettings()
        addBtn()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addAnimal() {
       addAndFillActor(animal)
       animal.animAnimateAnimal()
    }

    private fun AdvancedStage.addSettings() {
        animal.addBeetween { addActor(settings) }
        settings.setBounds(144f, 421f, 791f, 1078f)
        settings.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    private fun AdvancedStage.addBtn() {
        addActor(btn)
        btn.setBounds(73f, 1764f, 117f, 117f)
        btn.animShow(TIME_ANIM_SCREEN_ALPHA)
        btn.setOnClickListener(game.soundUtil) {
            animal.animToStart {}
            btn.animHide()
            settings.animHide(TIME_ANIM_SCREEN_ALPHA) {
                game.navigationManager.back()
            }
        }
    }


}