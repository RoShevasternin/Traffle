package com.viade.bepuzzle.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.viade.bepuzzle.game.GDXGame
import com.viade.bepuzzle.game.utils.*
import com.viade.bepuzzle.game.utils.font.FontGenerator
import com.viade.bepuzzle.game.utils.font.FontGenerator.Companion.FontPath
import com.viade.bepuzzle.util.cancelCoroutinesAll
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AdvancedScreen(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), AdvancedInputProcessor {

    abstract val game: GDXGame

    val viewportBack by lazy { ScreenViewport() }
    val stageBack    by lazy { AdvancedStage(viewportBack) }

    val viewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val stageUI    by lazy { AdvancedStage(viewportUI) }

    val topViewportBack by lazy { ScreenViewport() }
    val topStageBack    by lazy { AdvancedStage(topViewportBack) }

    val topViewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val topStageUI    by lazy { AdvancedStage(topViewportUI) }

    val inputMultiplexer = InputMultiplexer()

    val backBackgroundImage = Image()

    val disposableSet = mutableSetOf<Disposable>()
    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val drawerUtil by lazy { ShapeDrawerUtil(stageUI.batch) }

    val fontGenerator_Karantina = FontGenerator(FontPath.Karantina)

    val sizeScaler_Ui_Back = SizeScaler(SizeScaler.Axis.X, WIDTH)

    override fun resize(width: Int, height: Int) {
        viewportBack.update(width, height, true)
        viewportUI.update(width, height, true)
        topViewportBack.update(width, height, true)
        topViewportUI.update(width, height, true)

        sizeScaler_Ui_Back.calculateScale(Vector2(width.toFloat(), height.toFloat()))
    }

    override fun show() {
        stageBack.addAndFillActor(backBackgroundImage)

        stageBack.addActorsOnStageBack()
        stageUI.addActorsOnStageUI()
        topStageBack.addActorsOnStageTopBack()
        topStageUI.addActorsOnStageTopUI()

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@AdvancedScreen, stageUI, stageBack, topStageUI, topStageBack) }

        game.activity.backBlock = {
            if (game.navigationManager.isBackStackEmpty()) game.navigationManager.exit()
            else hideScreen { game.navigationManager.back() }
        }
    }

    override fun render(delta: Float) {
        stageBack.render()
        stageUI.render()
        topStageBack.render()
        topStageUI.render()

        drawerUtil.update()
    }

    override fun dispose() {
        log("dispose AdvancedScreen: ${this::class.java.name.substringAfterLast('.')}")
        disposeAll(
            stageBack, stageUI, topStageBack, topStageUI, drawerUtil,
            fontGenerator_Karantina,
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

    abstract fun AdvancedStage.addActorsOnStageUI()

    open fun AdvancedStage.addActorsOnStageBack() {}
    open fun AdvancedStage.addActorsOnStageTopBack() {}
    open fun AdvancedStage.addActorsOnStageTopUI() {}

    abstract fun hideScreen(block: Block = Block {  })

    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.drawable = TextureRegionDrawable(region)
    }

}