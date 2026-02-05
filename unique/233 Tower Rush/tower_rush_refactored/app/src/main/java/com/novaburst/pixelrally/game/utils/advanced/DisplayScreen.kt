/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.utils.advanced

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
import com.novaburst.pixelrally.MainActivity
import com.novaburst.pixelrally.game.utils.*
import com.novaburst.pixelrally.game.utils.font.TypefaceCreator
import com.novaburst.pixelrally.game.utils.font.TypefaceCreator.Companion.FontPath
import com.novaburst.pixelrally.util.cancelCoroutinesAll
import com.novaburst.pixelrally.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class DisplayScreen(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), InputHandler {

    val viewportBack = ScreenViewport()
    val stageBack = RenderStage(viewportBack)

    val viewportUI = FitViewport(WIDTH, HEIGHT)
    val stageUI = RenderStage(viewportUI)

    val topViewportBack = ScreenViewport()
    val topStageBack    = RenderStage(viewportBack)

    val topViewportUI = FitViewport(WIDTH, HEIGHT)
    val topStageUI    = RenderStage(viewportUI)

    val inputMultiplexer = InputMultiplexer()
    val disposableSet    = mutableSetOf<Disposable>()

    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val backBackgroundImage = Image()

    val drawerUtil = GeometryRenderer(stageUI.batch)

    val fontGenerator_Bold = TypefaceCreator(FontPath.Bold)
    val fontGenerator_Regular = TypefaceCreator(FontPath.Regular)

    val sizeScalerScreen = DimensionCalculator(DimensionCalculator.Axis.X, WIDTH)

    // Core functionality
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

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@DisplayScreen, topStageUI, topStageBack, stageUI, stageBack) }
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
    }

    // Function implementation
    override fun render(delta: Float) {
        stageBack.render()
        stageUI.render()
        topStageBack.render()
        topStageUI.render()

        drawerUtil.update()
    }

    // Core functionality
    override fun dispose() {
        log("dispose DisplayScreen: ${this::class.simpleName}")
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

    abstract fun RenderStage.addActorsOnStageUI()
    open fun RenderStage.addActorsOnStageBack() {}

    open fun RenderStage.addActorsOnStageTopBack() {}

    // Handler method
    open fun RenderStage.addActorsOnStageTopUI() {}

    abstract fun hideScreen(block: Block = Block {})

    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.drawable = TextureRegionDrawable(region)
    }

}