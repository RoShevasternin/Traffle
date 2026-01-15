package com.toyscatcher.factoryatoy.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.viewport.Viewport
import com.toyscatcher.factoryatoy.HEIGHT
import com.toyscatcher.factoryatoy.WIDTH
import com.toyscatcher.factoryatoy.utils.addProcessors
import com.toyscatcher.factoryatoy.utils.begend

abstract class AdvancedScreen : ScreenAdapter(), AdvancedInputProcessor {

    protected abstract val viewport: Viewport

    protected var background: TextureRegion? = null
    protected val stage by lazy { AdvancedStage(viewport) }
    protected val inputMultiplexer = InputMultiplexer()

    override fun show() {
        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@AdvancedScreen, stage) }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        drawBackground()
        stage.render()
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        stage.dispose()
        inputMultiplexer.clear()
    }

    private fun drawBackground() {
        stage.batch.apply {
            disableBlending()
            begend { background?.let { draw(it, 0f, 0f, WIDTH, HEIGHT) } }
            enableBlending()
        }
    }

}