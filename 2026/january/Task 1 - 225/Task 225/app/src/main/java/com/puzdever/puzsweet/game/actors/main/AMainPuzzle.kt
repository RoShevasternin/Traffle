package com.puzdever.puzsweet.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzdever.puzsweet.game.actors.ATimer
import com.puzdever.puzsweet.game.actors.button.AButton
import com.puzdever.puzsweet.game.actors.puzzle.APuzzlePanel
import com.puzdever.puzsweet.game.screens.PuzzleScreen
import com.puzdever.puzsweet.game.screens.ResultDoneScreen
import com.puzdever.puzsweet.game.screens.ResultFailScreen
import com.puzdever.puzsweet.game.utils.Acts
import com.puzdever.puzsweet.game.utils.Block
import com.puzdever.puzsweet.game.utils.TIME_ANIM_SCREEN
import com.puzdever.puzsweet.game.utils.actor.animDelay
import com.puzdever.puzsweet.game.utils.actor.animHide
import com.puzdever.puzsweet.game.utils.actor.animMoveTo
import com.puzdever.puzsweet.game.utils.actor.animShow
import com.puzdever.puzsweet.game.utils.actor.disable
import com.puzdever.puzsweet.game.utils.advanced.AdvancedMainGroup
import com.puzdever.puzsweet.game.utils.gdxGame
import com.puzdever.puzsweet.game.utils.puzzle.Puzzles
import com.puzdever.puzsweet.game.utils.region

class AMainPuzzle(override val screen: PuzzleScreen): AdvancedMainGroup() {

    private val puzzleRegion = gdxGame.assetsAll.listPuzzles.random().region

    private val imgPanel  = Image(gdxGame.assetsAll.GAME_PAN)
    private val btnBack   = AButton(screen, AButton.Type.X)

    private val puzzlesPanel = APuzzlePanel(screen, puzzleRegion)
    private val aTimer       = ATimer(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgPanel()
        addBtnBack()
        addPuzzlePanel()
        addATimer()

        animShowMain {
            btnBack.animMoveTo(btnBack.x, 1728f, 0.5f)

            aTimer.start()
        }
    }

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(59f, 443f, 962f, 961f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(71f, 2000f, 132f, 132f)

        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addPuzzlePanel() {
        addActor(puzzlesPanel)
        puzzlesPanel.setBounds(197f, 576f, 684f, 684f)

        puzzlesPanel.finishBlock = {
            this.disable()

            //gdxGame.soundUtil.apply { play(win) }

            animDelay(0.35f) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ResultDoneScreen::class.java.name)
                }
            }
        }
    }

    private fun addATimer() {
        addActor(aTimer)
        aTimer.setBounds(283f, 1566f, 515f, 250f)

        aTimer.timeOut = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(ResultFailScreen::class.java.name)
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}