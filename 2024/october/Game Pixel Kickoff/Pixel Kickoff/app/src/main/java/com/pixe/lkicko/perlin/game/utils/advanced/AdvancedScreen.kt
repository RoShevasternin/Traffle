package com.pixe.lkicko.perlin.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.utils.*
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.font.FontGenerator
import com.pixe.lkicko.perlin.util.cancelCoroutinesAll
import com.pixe.lkicko.perlin.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AdvancedScreen(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), AdvancedInputProcessor {

    abstract val game: LibGDXGame

    val viewportBack by lazy { ScreenViewport() }
    val stageBack    by lazy { AdvancedStage(viewportBack) }

    val viewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val stageUI    by lazy { AdvancedStage(viewportUI) }

    val topViewportBack by lazy { ScreenViewport() }
    val topStageBack    by lazy { AdvancedStage(viewportBack) }

    val topViewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val topStageUI    by lazy { AdvancedStage(viewportUI) }

    val inputMultiplexer    = InputMultiplexer()
    val backBackgroundImage by lazy { Image(game.loading.LOADER_background_blue.region) }
    val uiBackgroundImage   = Image()
    val disposableSet       = mutableSetOf<Disposable>()
    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val drawerUtil by lazy { ShapeDrawerUtil(stageUI.batch) }

    val fontGenerator_Jua = FontGenerator(FontGenerator.Companion.FontPath.Jua)

    override fun resize(width: Int, height: Int) {
        viewportBack.update(width, height, true)
        viewportUI.update(width, height, true)

        topViewportBack.update(width, height, true)
        topViewportUI.update(width, height, true)
    }


    override fun show() {
        stageBack.addAndFillActor(backBackgroundImage)
        stageUI.addAndFillActor(uiBackgroundImage)

        stageUI.addActorsOnStageUI()

        Gdx.input.inputProcessor = inputMultiplexer.apply {
            addProcessors(this@AdvancedScreen, stageUI,

                    topStageBack, topStageUI
            )
        }

        game.activity.backBlock = {
            if (game.navigationManager.isBackStackEmpty()) game.navigationManager.exit()
            else stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
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
            stageBack, stageUI, drawerUtil,

            fontGenerator_Jua
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

    open fun AdvancedStage.addActorsOnStageUI() {}

    fun setUIBackground(texture: Texture) {
        uiBackgroundImage.drawable = TextureRegionDrawable(texture)
    }

}