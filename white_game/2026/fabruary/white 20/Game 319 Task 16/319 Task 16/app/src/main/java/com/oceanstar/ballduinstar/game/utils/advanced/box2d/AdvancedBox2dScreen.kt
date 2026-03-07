package com.oceanstar.ballduinstar.game.utils.advanced.box2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.oceanstar.ballduinstar.game.box2d.WorldUtil
import com.oceanstar.ballduinstar.game.utils.HEIGHT_UI
import com.oceanstar.ballduinstar.game.utils.HEIGHT_WORLD
import com.oceanstar.ballduinstar.game.utils.WIDTH_UI
import com.oceanstar.ballduinstar.game.utils.WIDTH_WORLD
import com.oceanstar.ballduinstar.game.utils.addProcessors
import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedScreen
import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedStage
import com.oceanstar.ballduinstar.util.currentClassName
import com.oceanstar.ballduinstar.util.log

abstract class AdvancedBox2dScreen(
    val worldUtil: WorldUtil,
    val uiW  : Float = WIDTH_UI,
    val uiH  : Float = HEIGHT_UI,
    val worldW : Float = WIDTH_WORLD,
    val worldH : Float = HEIGHT_WORLD,
): AdvancedScreen(uiW, uiH) {

    val viewportDebug by lazy { ExtendViewport(worldW, worldH) }

    val viewportWorld by lazy { ExtendViewport(WIDTH, HEIGHT) }
    val stageWorld    by lazy { AdvancedStage(viewportWorld) }

    var isWorldPause = false

    override fun show() {
        super.show()

        val screenWidth  = Gdx.graphics.width
        val screenHeight = Gdx.graphics.height

        stageWorld.update(screenWidth, screenHeight, true)
        viewportDebug.update(screenWidth, screenHeight, true)

        stageWorld.root.addActorsOnStageWorld()

        inputMultiplexer.clear()
        inputMultiplexer.addProcessors(
            this,              // Твої клавіші (BACK і т.д.)
            stageUI,           // Кнопки інтерфейсу мають найвищий пріоритет
            stageWorld,        // Об'єкти в ігровому світі (наприклад, блоки)
            stageBack          // Фон
        )

    }

    override fun render(delta: Float) {
        if (isWorldPause.not()) worldUtil.update(delta)

        stageBack.render()
        stageWorld.render()
        stageUI.render()

        drawerUtil.update()

        if (WorldUtil.isDebug) {
            viewportDebug.apply()
            worldUtil.debug(viewportDebug.camera.combined)
        }
    }

    override fun dispose() {
        log("dispose AdvancedBox2dScreen: $currentClassName")//${this::class.java.name.substringAfterLast('.')}")
        worldUtil.dispose()
        stageWorld.dispose()
        super.dispose()
    }

    abstract fun Group.addActorsOnStageWorld()

}