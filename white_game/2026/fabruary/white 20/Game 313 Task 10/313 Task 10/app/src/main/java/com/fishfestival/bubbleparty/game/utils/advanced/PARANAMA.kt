package com.fishfestival.bubbleparty.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.fishfestival.bubbleparty.game.utils.HEIGHT_UI
import com.fishfestival.bubbleparty.game.utils.ShapeDrawerUtil
import com.fishfestival.bubbleparty.game.utils.TIME_ANIM_SCREEN
import com.fishfestival.bubbleparty.game.utils.WIDTH_UI
import com.fishfestival.bubbleparty.game.utils.actor.addAndFillActor
import com.fishfestival.bubbleparty.game.utils.actor.animHide
import com.fishfestival.bubbleparty.game.utils.addProcessors
import com.fishfestival.bubbleparty.game.utils.disposeAll
import com.fishfestival.bubbleparty.game.utils.gdxGame
import com.fishfestival.bubbleparty.util.cancelCoroutinesAll
import com.fishfestival.bubbleparty.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class PARANAMA(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), IInputAdapter {

    val viewportBack by lazy { ScreenViewport() }
    val stageBack    by lazy { AdvancedStage(viewportBack) }

    val viewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val stageUI    by lazy { AdvancedStage(viewportUI) }

    val inputMultiplexer    = InputMultiplexer()

    val backBackgroundImage = Image()
    val uiBackgroundImage   = Image()

    val disposableSet       = mutableSetOf<Disposable>()
    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val drawerUtil by lazy { ShapeDrawerUtil(stageUI.batch) }

    override fun resize(width: Int, height: Int) {
        viewportBack.update(width, height, true)
        viewportUI.update(width, height, true)
    }


    override fun show() {
        stageBack.root.addActor(backBackgroundImage)
        backBackgroundImage.setSize(stageBack.width, stageBack.height)
        stageUI.root.addAndFillActor(uiBackgroundImage)

        stageUI.addActorsOnStageUI()

        Gdx.input.inputProcessor = inputMultiplexer.apply {
            addProcessors(this@PARANAMA, stageUI)
        }
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
    }

    override fun render(delta: Float) {
        stageBack.render()
        stageUI.render()

        drawerUtil.update()
    }



    override fun keyDown(keycode: Int): Boolean {
        when(keycode) {
            Input.Keys.BACK -> {
                if (gdxGame.navigationManager.isBackStackEmpty()) gdxGame.navigationManager.exit()
                else stageUI.root.animHide(TIME_ANIM_SCREEN) { gdxGame.navigationManager.back() }
            }
        }
        return true
    }

    override fun dispose() {
        log("dispose AdvancedScreen: ${this::class.java.name.substringAfterLast('.')}")
        disposeAll(
            stageBack, stageUI, drawerUtil,
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

    open fun AdvancedStage.addActorsOnStageUI() {}

    fun setBackBackground(texture: Texture) {
        backBackgroundImage.drawable = TextureRegionDrawable(texture)
    }

    fun setUIBackground(texture: Texture) {
        uiBackgroundImage.drawable = TextureRegionDrawable(texture)
    }

}