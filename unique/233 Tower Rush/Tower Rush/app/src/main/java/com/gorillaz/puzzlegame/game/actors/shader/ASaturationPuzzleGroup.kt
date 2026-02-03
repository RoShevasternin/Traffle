package com.gorillaz.puzzlegame.game.actors.shader

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.disposeAll

class ASaturationPuzzleGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        private var vertexShader   = Gdx.files.internal("shader/defaultVS.glsl").readString()
        private var fragmentShader = Gdx.files.internal("shader/saturationFS.glsl").readString()
    }

    private var shaderProgram: ShaderProgram? = null

    private val tileSize        = 1f / 3f // Розмір одного квадрата (3x3 сітка)
    private val saturationArray = FloatArray(9) { 0f } // Масив насиченості для кожного квадрата
    private var activeIndices   = listOf<Int>() // Індекси активних квадратів (0..8)

    override fun addActorsOnGroup() {
        createShaders()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (batch == null || shaderProgram == null) return

        batch.end()
        batch.begin()

        batch.shader = shaderProgram

        shaderProgram!!.setUniformf("u_tileSize", tileSize)
        shaderProgram!!.setUniform1fv("u_saturation", saturationArray, 0, saturationArray.size)
        super.draw(batch, parentAlpha)

        batch.shader = null
        batch.end()
        batch.begin()
    }

    override fun dispose() {
        super.dispose()
        disposeAll(shaderProgram)
    }

    // Logic ------------------------------------------------------------------------

    private fun createShaders() {
        ShaderProgram.pedantic = true
        shaderProgram = ShaderProgram(vertexShader, fragmentShader)

        if (shaderProgram?.isCompiled == false) {
            throw IllegalStateException("shader compilation failed:\n" + shaderProgram?.log)
        }
    }

    fun updateActiveIndices(newActiveIndices: List<Int>) {
        activeIndices = newActiveIndices
        saturationArray.fill(0f)
        activeIndices.forEach { index -> saturationArray[index] = 1f }
    }

}