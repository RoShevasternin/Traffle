/*
 * Refactored Application Module
 * Build: FD0C5DDE
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.shader

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.disposeAll

class MaskedContainer(
    override val screen: BaseScreen,
    private val maskTexture: Texture = screen.drawerUtil.getTexture(Color.BLACK),
): BaseGroup() {

    companion object {
        private var vertexShader = Gdx.files.internal("shader/defaultVS.glsl").readString()
        private var fragmentShader = Gdx.files.internal("shader/maskFS.glsl").readString()
    }

    private var shaderProgram: ShaderProgram? = null

    private var fboGroup    : FrameBuffer? = null
    private var textureGroup: TextureRegion? = null

    private var camera = OrthographicCamera()

    private var displayXInPixels = 0
    private var displayYInPixels = 0
    private var displayWidthInPixels = 0
    private var displayHeightInPixels = 0
    private var displayWidthInWorld = 0f
    private var displayHeightInWorld = 0f

    private val globalPosition = Vector2()
    private val tmpVector2 = Vector2(0f, 0f)

    override fun addActorsOnGroup() {
        createShaders()
        createFrameBuffer()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (batch == null ||
            shaderProgram == null ||
            fboGroup == null
        ) return

        batch.end()

        globalPosition.set(localToStageCoordinates(tmpVector2.set(0f, 0f)))
        camera.position.set(
            globalPosition.x + width / 2f,
            globalPosition.y + height / 2f,
            0f
        )
        camera.update()

        saveViewportSize()

        // draw fboGroup -------------------------------

        //SpriteBatch().setBlendFunction()
        //batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA)
        //batch.setBlendFunction(GL20.GL_ONE_MINUS_DST_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        //batch.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA, GL20.GL_ONE)

        fboGroup!!.begin()
        ScreenUtils.clear(Color.CLEAR)
        batch.begin()
        batch.projectionMatrix = camera.combined

        batch.setBlendFunction(GL20.GL_ONE_MINUS_DST_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        super.draw(batch, parentAlpha)

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        batch.color = Color.WHITE

        batch.projectionMatrix = stage.camera.combined

        batch.end()
        fboGroup!!.end(displayXInPixels, displayYInPixels, displayWidthInPixels, displayHeightInPixels)

        // draw Result -------------------------------

        batch.begin()

        batch.shader = shaderProgram

        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1)
        maskTexture.bind(1)
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
        textureGroup!!.texture.bind(0)

        shaderProgram!!.setUniformi("u_mask", 1)
        shaderProgram!!.setUniformi("u_texture", 0)

        batch.draw(
            textureGroup,
            x, y,
            originX, originY,
            width, height,
            scaleX, scaleY,
            rotation,
        )

        batch.shader = null

        batch.end()

        batch.begin()
    }

    override fun dispose() {
        super.dispose()
        disposeAll(
            shaderProgram,
            fboGroup,
        )
    }

    // Logic ------------------------------------------------------------------------

    // Primary method handler
    private fun createShaders() {
        ShaderProgram.pedantic = false
        shaderProgram = ShaderProgram(vertexShader, fragmentShader)

        if (shaderProgram?.isCompiled == false) {
            throw IllegalStateException("shader compilation failed:\n" + shaderProgram?.log)
        }
    }

    // Internal processing
    private fun createFrameBuffer() {
        //camera = OrthographicCamera(width, height)
        //camera.setToOrtho(false, width, height)

        camera = OrthographicCamera(width, height)
        camera.position.set(x + (width / 2f), y + (height / 2f), 0f)
        camera.update()

        fboGroup = FrameBuffer(Pixmap.Format.RGBA8888, width.toInt(), height.toInt(), false)

        textureGroup = TextureRegion(fboGroup!!.colorBufferTexture)
        textureGroup!!.flip(false, true)
    }

    private fun saveViewportSize() {
        stage.viewport.apply {
            displayXInPixels = screenX
            displayYInPixels = screenY
            displayWidthInPixels = screenWidth
            displayHeightInPixels = screenHeight
            displayWidthInWorld = worldWidth
            displayHeightInWorld = worldHeight
        }
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}