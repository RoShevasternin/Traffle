package com.gorillaz.puzzlegame.game.utils.advanced

import com.gorillaz.puzzlegame.game.utils.gdxGame

abstract class AdvancedMainScreen : AdvancedScreen() {

    abstract val aMain: AdvancedMainGroup

    abstract fun AdvancedStage.addMain()

    override fun dispose() {
        gdxGame.soundUtil.spin.sound.stop()
        super.dispose()
    }

}