package com.fishfestival.bubbleparty.game.utils.advanced.box2d

import com.badlogic.gdx.utils.viewport.FitViewport
import com.fishfestival.bubbleparty.game.box2d.WorldUtil
import com.fishfestival.bubbleparty.game.utils.HEIGHT_UI
import com.fishfestival.bubbleparty.game.utils.HEIGHT_WORLD
import com.fishfestival.bubbleparty.game.utils.WIDTH_UI
import com.fishfestival.bubbleparty.game.utils.WIDTH_WORLD
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedStage
import com.fishfestival.bubbleparty.game.utils.advanced.PARANAMA
import com.fishfestival.bubbleparty.game.utils.runGDX
import com.fishfestival.bubbleparty.util.log

abstract class AdvancedBox2dScreen(
    val worldUtil: WorldUtil,
    val uiW  : Float = WIDTH_UI,
    val uiH  : Float = HEIGHT_UI,
    val boxW : Float = WIDTH_WORLD,
    val boxH : Float = HEIGHT_WORLD,
): PARANAMA(uiW, uiH) {

    private val viewportDebug by lazy { FitViewport(boxW, boxH) }

    val viewportWorld by lazy { FitViewport(uiW, uiH) }
    val stageWorld    by lazy { AdvancedStage(viewportWorld) }

    var isPauseWorld = false

    override fun show() {
        stageWorld.addActorsOnStageBox2d()
        super.show()

        inputMultiplexer.addProcessor(stageWorld)
    }

    override fun resize(width: Int, height: Int) {
        viewportDebug.update(width, height, true)
        viewportWorld.update(width, height, true)
        super.resize(width, height)
    }

    override fun render(delta: Float) {
        if (isPauseWorld.not()) worldUtil.update(delta)

        stageBack.render()
        stageWorld.render()
        stageUI.render()

        drawerUtil.update()

        worldUtil.debug(viewportDebug.camera.combined)
    }

    override fun dispose() {
        runGDX {
            log("dispose AdvancedBox2dScreen: ${this::class.java.name.substringAfterLast('.')}")
            worldUtil.dispose()
            super.dispose()
        }
    }

    abstract fun AdvancedStage.addActorsOnStageBox2d()

}