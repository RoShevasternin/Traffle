package com.honeyhun.hunhoney.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.honeyhun.hunhoney.game.LibGDXGame
import com.honeyhun.hunhoney.game.utils.TIME_ANIM
import com.honeyhun.hunhoney.game.utils.actor.animHide
import com.honeyhun.hunhoney.game.utils.actor.animShow
import com.honeyhun.hunhoney.game.utils.advanced.AdvancedScreen
import com.honeyhun.hunhoney.game.utils.advanced.AdvancedStage
import com.honeyhun.hunhoney.game.utils.advanced.isBlue
import com.honeyhun.hunhoney.game.utils.region

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