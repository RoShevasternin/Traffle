package com.oriental.matchline.game.screens.level

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.oriental.matchline.game.LibGDXGame
import com.oriental.matchline.game.actors.ATimerGroup
import com.oriental.matchline.game.actors.TmpGroup
import com.oriental.matchline.game.actors.button.AButton
import com.oriental.matchline.game.actors.checkbox.ACheckBox
import com.oriental.matchline.game.screens.ValResultScreen
import com.oriental.matchline.game.utils.TIME_ANIM
import com.oriental.matchline.game.utils.actor.animHide
import com.oriental.matchline.game.utils.actor.animShow
import com.oriental.matchline.game.utils.actor.setOnClickListener
import com.oriental.matchline.game.utils.advanced.AdvancedScreen
import com.oriental.matchline.game.utils.advanced.AdvancedStage
import com.oriental.matchline.game.utils.region

abstract class ILevelScreen(final override val game: LibGDXGame, val level: Static.ELevel) : AdvancedScreen() {

    private val assets = game.allAssets

    private val initData: Static.InitData = getInitData()

    protected val tmpGroup by lazy { TmpGroup(this) }

    private val panelImg = Image(initData.panel)
    private val levelImg = Image(initData.level)
    private val menuBtn  by lazy { AButton(this, AButton.Static.Type.VAL_MENU) }
    private val pauseBox by lazy { ACheckBox(this, ACheckBox.Static.Type.PAUSE) }
    private val timer    by lazy { ATimerGroup(this) }

    // Field
    protected val indexList = (0..5).shuffled()

    override fun show() {
        stageUI.root.color.a = 0f
        setBackgrounds(initData.background)
        super.show()
    }

    final override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addLevel()
        addMenu()
        addPause()
        addTimer()

        stageUI.root.animShow(TIME_ANIM) {
            addActorsOnStage()
            timer.startTimer {
                ValResultScreen.apply {
                    levelScreen = level
                    isWin = false
                }
                game.navigationManager.navigate(ValResultScreen::class.java.name)
            }
        }
    }

    abstract fun AdvancedStage.addActorsOnStage()

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(0f, 161f, 1080f, 1399f)
    }

    private fun AdvancedStage.addLevel() {
        addActor(levelImg)
        levelImg.setBounds(324f, 1639f, 423f, 115f)
    }

    private fun AdvancedStage.addMenu() {
        addActor(menuBtn)
        menuBtn.setBounds(89f, 1621f, 171f, 175f)
        menuBtn.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addPause() {
        addActor(pauseBox)
        pauseBox.setBounds(840f, 1621f, 171f, 175f)
        pauseBox.setOnCheckListener { timer.isPause = it }
    }

    private fun AdvancedStage.addTimer() {
        addActor(timer)
        timer.setBounds(400f, 1460f, 265f, 115f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getInitData(): Static.InitData = when(level) {
        Static.ELevel.L1 -> Static.InitData(
            game.loadingAssets.ValBackground.region,
            assets.listLevel[0],
            assets.VAL_PAN1.region
        )
        Static.ELevel.L2 -> Static.InitData(
            assets.BG2.region,
            assets.listLevel[1],
            assets.VAL_PAN2.region
        )
        Static.ELevel.L3 -> Static.InitData(
            assets.BG3.region,
            assets.listLevel[2],
            assets.VAL_PAN3.region
        )
        Static.ELevel.L4 -> Static.InitData(
            assets.BG4.region,
            assets.listLevel[3],
            assets.VAL_PAN4.region
        )
    }

    protected fun stopTimer() {
        timer.isPause = true
    }

    // ---------------------------------------------------
    // class
    // ---------------------------------------------------

    object Static {
        data class InitData(
            val background: TextureRegion,
            val level: TextureRegion,
            val panel: TextureRegion,
        )

        enum class ELevel {
            L1, L2, L3, L4
        }
    }

}