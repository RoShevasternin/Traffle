package com.buzzbee.honeyflight.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.buzzbee.honeyflight.game.LibGDXGame
import com.buzzbee.honeyflight.game.utils.TIME_ANIM
import com.buzzbee.honeyflight.game.utils.actor.animHide
import com.buzzbee.honeyflight.game.utils.actor.animShow
import com.buzzbee.honeyflight.game.utils.advanced.AdvancedScreen
import com.buzzbee.honeyflight.game.utils.advanced.AdvancedStage
import com.buzzbee.honeyflight.game.utils.advanced.isBlue
import com.buzzbee.honeyflight.game.utils.region

class BeeRulesScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val playImg = Image(game.allAssets.rules)

    override fun show() {
        isBlue = false
        stageUI.root.animHide()
        setBackBackground(game.startAssets.YELLOW.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(playImg)
        playImg.apply {
            setBounds(228f, 140f, 1464f, 800f)
        }
    }

}