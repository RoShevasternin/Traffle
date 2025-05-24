package com.viade.bepuzzle.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viade.bepuzzle.game.GDXGame
import com.viade.bepuzzle.game.actors.AFail
import com.viade.bepuzzle.game.actors.APanelTopGame
import com.viade.bepuzzle.game.actors.AWin
import com.viade.bepuzzle.game.actors.main.AMainGame
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.GameColor
import com.viade.bepuzzle.game.utils.actor.animShow
import com.viade.bepuzzle.game.utils.actor.disable
import com.viade.bepuzzle.game.utils.actor.enable
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.advanced.AdvancedStage
import com.viade.bepuzzle.game.utils.dataStore.DataStoreGalleryStarsUtil
import com.viade.bepuzzle.game.utils.region
import com.viade.bepuzzle.game.utils.runGDX
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameScreen(override val game: GDXGame): AdvancedScreen() {

    private val aPanelTop = APanelTopGame(this)
    private val aMainGame = AMainGame(this)
    private val aWin      = AWin(this)
    private val aFail     = AFail(this)
    private val imgBlur   = Image(drawerUtil.getTexture(GameColor.blurGold))

    override fun show() {
        setBackBackground(game.assetsLoader.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addPanelTop()

    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMainGame()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addImgBlur()
    }

    override fun AdvancedStage.addActorsOnStageTopUI() {
        addWin()
        addFail()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMainGame.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addPanelTop() {
        addActor(aPanelTop)

        val w = sizeScaler_Ui_Back.scaled(1080f)
        val h = sizeScaler_Ui_Back.scaled(228f)
        val x = (viewportBack.worldWidth / 2) - (w / 2)
        val y = (viewportBack.worldHeight - h)
        aPanelTop.setBounds(x, y, w, h)

        aPanelTop.blockPause = { isPause ->
            if (isPause) aMainGame.disable() else aMainGame.enable()
        }
        aPanelTop.blockTimeOut = {
            game.soundUtil.apply { play(fail) }
            log("FAIL")
            showFail()
        }
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMainGame() {
        addAndFillActor(aMainGame)
        aMainGame.apply {
            blockK1 = { aPanelTop.tMinutes += 1 }

            blockPuzzleCompleted = {
                screen.game.soundUtil.apply { play(win) }
                aPanelTop.isStopTimer = true
                log("WIN")
                showWin()

                game.coroutine.launch(Dispatchers.IO) {
                    val nextLevel = SelectLevelScreen.LEVEL_INDEX + 2

                    game.dataStoreLevelOpenedUtil.update { listLevels ->
                        listLevels.toMutableList().apply {
                            val currentOpenedLevel = get(MenuScreen.CATEGORY_INDEX)
                            val newLevel =
                                if (nextLevel <= 6 && (nextLevel - currentOpenedLevel) == 1) nextLevel else currentOpenedLevel

                            set(MenuScreen.CATEGORY_INDEX, newLevel)
                        }
                    }

                    game.dataStoreGalleryStarsUtil.update { set ->
                        set.toMutableSet().apply {
                            add(
                                DataStoreGalleryStarsUtil.Stars(
                                    MenuScreen.CATEGORY_INDEX,
                                    SelectLevelScreen.LEVEL_INDEX,
                                    SelectLevelScreen.DIFFICULTY_INDEX
                                )
                            )
                        }
                    }

                    game.dataStoreBalanceUtil.update { it + 100 }
                }

            }
        }
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addImgBlur() {
        addAndFillActor(imgBlur)
        imgBlur.apply {
            color.a = 0f
            disable()
        }
    }

    // Actors Top UI------------------------------------------------------------------------

    private fun AdvancedStage.addWin() {
        addActor(aWin)
        aWin.apply {
            disable()
            color.a = 0f
            setBounds(-21f, 378f, 1122f, 1163f)
        }
    }

    private fun AdvancedStage.addFail() {
        addActor(aFail)
        aFail.apply {
            disable()
            color.a = 0f
            setBounds(-21f, 378f, 1122f, 1163f)
        }
    }

    // Logic ------------------------------------------------------------------------------

    private fun showWin() {
        stageBack.root.disable()
        stageUI.root.disable()

        aWin.apply {
            enable()
            animShow(0.25f)
        }
        imgBlur.apply {
            enable()
            animShow(0.25f)
        }
    }

    private fun showFail() {
        stageBack.root.disable()
        stageUI.root.disable()

        aFail.apply {
            enable()
            animShow(0.25f)
        }
        imgBlur.apply {
            enable()
            animShow(0.25f)
        }
    }

}