package com.swee.ttrio.comb.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.swee.ttrio.comb.game.LibGDXGame
import com.swee.ttrio.comb.game.utils.TIME_ANIM
import com.swee.ttrio.comb.game.utils.actor.animHide
import com.swee.ttrio.comb.game.utils.actor.animShow
import com.swee.ttrio.comb.game.utils.actor.setOnClickListener
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.advanced.AdvancedStage
import com.swee.ttrio.comb.game.utils.region
import com.swee.ttrio.comb.game.utils.runGDX
import kotlinx.coroutines.launch

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true
    }

    private val imgBTNS = Image(game.all.START_SETT_ACHIE)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = FUN_AUDIO.apply {
                isLooping = true
                volumeLevelFlow.value = 18f
            } }
        }

        stageUI.root.animHide()
        setBackBackground(game.all.BACKGROUND_1.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgBtns()
                addBtns()
            }
        }
    }

    private fun AdvancedStage.addImgBtns() {
        addActor(imgBTNS)
        imgBTNS.setBounds(31f, 108f, 329f, 691f)
    }

    private fun AdvancedStage.addBtns() {
        val aHTP   = Actor()
        val aStart = Actor()
        val aSett  = Actor()
        val aAchi  = Actor()
        val aExit  = Actor()
        addActors(aHTP, aStart, aSett, aAchi, aExit)
        aHTP.apply {
            setBounds(99f,715f,191f,84f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(HowToPlayScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aStart.apply {
            setBounds(31f,494f,328f,101f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(StartScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aSett.apply {
            setBounds(31f,371f,328f,101f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(SettingScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aAchi.apply {
            setBounds(31f,249f,328f,101f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AchievementsScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aExit.apply {
            setBounds(82f,108f,226f,69f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.exit()
                }
            }
        }
    }

}