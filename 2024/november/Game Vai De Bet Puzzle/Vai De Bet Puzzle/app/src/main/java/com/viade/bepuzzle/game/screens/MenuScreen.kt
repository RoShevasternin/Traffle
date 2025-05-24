package com.viade.bepuzzle.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viade.bepuzzle.R
import com.viade.bepuzzle.game.GDXGame
import com.viade.bepuzzle.game.actors.APanelTop
import com.viade.bepuzzle.game.actors.ASideMenu
import com.viade.bepuzzle.game.actors.main.AMainMenu
import com.viade.bepuzzle.game.utils.*
import com.viade.bepuzzle.game.utils.actor.*
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class MenuScreen(override val game: GDXGame): AdvancedScreen() {

    companion object {
        private var isFirstOpen = true

        var CATEGORY_INDEX = 0
    }

    private val aPanelTop = APanelTop(this)
    private val aMainMenu = AMainMenu(this)
    private val aSideMenu = ASideMenu(this)
    private val imgBlur   = Image(drawerUtil.getTexture(GameColor.blur))

    override fun show() {
        CATEGORY_INDEX = 0

        setBackBackground(game.assetsLoader.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addPanelTop()

    }

    override fun AdvancedStage.addActorsOnStageUI() {
        //addAndFillActor(Image(drawerUtil.getRegion(Color.GRAY.apply { a = 0.25f })))
        addMainMenu()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addImgBlur()
    }

    override fun AdvancedStage.addActorsOnStageTopUI() {
        addSideMenu()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            runGDX { hideSideMenu() }
            aMainMenu.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addPanelTop() {
        addActor(aPanelTop)
        if (isFirstOpen) {
            isFirstOpen = false
            aPanelTop.color.a = 0f
            game.activity.setStatusBarColor(R.color.top_panel)
        }

        val w = sizeScaler_Ui_Back.scaled(1080f)
        val h = sizeScaler_Ui_Back.scaled(228f)
        val x = (viewportBack.worldWidth / 2) - (w / 2)
        val y = (viewportBack.worldHeight - h)
        aPanelTop.setBounds(x, y, w, h)

        aPanelTop.blockMenu = { showSideMenu() }

        aPanelTop.animShow(0.15f)
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMainMenu() {
        addAndFillActor(aMainMenu)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addImgBlur() {
        addAndFillActor(imgBlur)
        imgBlur.apply {
            color.a = 0f
            disable()
            setOnClickListener(game.soundUtil) { hideSideMenu() }
        }
    }

    // Actors Top UI------------------------------------------------------------------------

    private fun AdvancedStage.addSideMenu() {
        addActor(aSideMenu)
        aSideMenu.apply {
            color.a = 0f
            disable()

            setBounds(-480f, 528f, 480f, 1058f)
        }
    }

    // Logic --------------------------------------------------------------------------------------

    private fun showSideMenu() {
        stageBack.root.disable()
        stageUI.root.disable()

        aSideMenu.apply {
            enable()
            clearActions()
            addAction(Actions.parallel(
                Actions.fadeIn(0.25f),
                Actions.moveTo(0f, 528f, 0.3f, Interpolation.sineOut)
            ))
        }
        imgBlur.apply {
            enable()
            animShow(0.25f)
        }
    }

    private fun hideSideMenu() {
        stageBack.root.enable()
        stageUI.root.enable()

        aSideMenu.apply {
            disable()
            clearActions()
            addAction(Actions.parallel(
                Actions.fadeOut(0.25f),
                Actions.moveTo(-480f, 528f, 0.3f, Interpolation.sineIn)
            ))
        }
        imgBlur.apply {
            disable()
            animHide(0.25f)
        }
    }

}