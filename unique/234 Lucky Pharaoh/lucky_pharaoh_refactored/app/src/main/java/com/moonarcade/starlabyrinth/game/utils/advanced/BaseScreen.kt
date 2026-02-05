/*
 * Refactored Application Module
 * Build: 8D168C39
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.moonarcade.starlabyrinth.MainActivity
import com.moonarcade.starlabyrinth.game.utils.*
import com.moonarcade.starlabyrinth.game.utils.font.FontFactory
import com.moonarcade.starlabyrinth.game.utils.font.FontFactory.Companion.FontPath
import com.moonarcade.starlabyrinth.util.cancelCoroutinesAll
import com.moonarcade.starlabyrinth.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseScreen(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), InputEventHandler {

    val viewportBack = ScreenViewport()
    val stageBack = BaseStage(viewportBack)

    val viewportUI = FitViewport(WIDTH, HEIGHT)
    val stageUI = BaseStage(viewportUI)

    val topViewportBack = ScreenViewport()
    val topStageBack = BaseStage(viewportBack)

    val topViewportUI = FitViewport(WIDTH, HEIGHT)
    val topStageUI = BaseStage(viewportUI)

    val inputMultiplexer = InputMultiplexer()
    val disposableSet = mutableSetOf<Disposable>()

    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val backBackgroundImage = Image()

    val drawerUtil = ShapeRenderingSystem(stageUI.batch)

    val fontGenerator_Bold = FontFactory(FontPath.Bold)
    val fontGenerator_Regular = FontFactory(FontPath.Regular)

    val sizeScalerScreen = ScaleCalculator(ScaleCalculator.Axis.X, WIDTH)

    override fun resize(width: Int, height: Int) {
        sizeScalerScreen.calculateScale(width.toFloat(), height.toFloat())

        viewportBack.update(width, height, true)
        viewportUI.update(width, height - MainActivity.statusBarHeight, true)
        topViewportBack.update(width, height, true)
        topViewportUI.update(width, height, true)
    }

    override fun show() {
        stageBack.addAndFillActor(backBackgroundImage)

        stageBack.addActorsOnStageBack()
        stageUI.addActorsOnStageUI()
        topStageBack.addActorsOnStageTopBack()
        topStageUI.addActorsOnStageTopUI()

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@BaseScreen, topStageUI, topStageBack, stageUI, stageBack) }
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
    }

    override fun render(delta: Float) {
        stageBack.render()
        stageUI.render()
        topStageBack.render()
        topStageUI.render()

        drawerUtil.update()
    }

    override fun dispose() {
        log("dispose BaseScreen: ${this::class.simpleName}")
        disposeAll(
            stageBack, stageUI, topStageBack, topStageUI, drawerUtil,
            fontGenerator_Bold, fontGenerator_Regular,
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

    override fun keyDown(keycode: Int): Boolean {
        when(keycode) {
            Input.Keys.BACK -> {
                if (gdxGame.navigationManager.isBackStackEmpty()) gdxGame.navigationManager.exit()
                else hideScreen { gdxGame.navigationManager.back() }
            }
        }
        return true
    }

    abstract fun BaseStage.addActorsOnStageUI()
    open fun BaseStage.addActorsOnStageBack() {}

    open fun BaseStage.addActorsOnStageTopBack() {}

    open fun BaseStage.addActorsOnStageTopUI() {}

    abstract fun hideScreen(block: Block = Block {})

    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.drawable = TextureRegionDrawable(region)
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}