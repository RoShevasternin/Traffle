package com.viesta.delacoca.game.screens.common

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viesta.delacoca.game.LibGDXGame
import com.viesta.delacoca.game.actors.TmpGroup
import com.viesta.delacoca.game.screens.common.ICommonScreen.Static.TypeScreen.*
import com.viesta.delacoca.game.utils.TIME_ANIM
import com.viesta.delacoca.game.utils.actor.animHide
import com.viesta.delacoca.game.utils.actor.animShow
import com.viesta.delacoca.game.utils.actor.disable
import com.viesta.delacoca.game.utils.advanced.AdvancedGroup
import com.viesta.delacoca.game.utils.advanced.AdvancedScreen
import com.viesta.delacoca.game.utils.advanced.AdvancedStage
import com.viesta.delacoca.game.utils.region

abstract class ICommonScreen(
    final override val game: LibGDXGame,
    val typeScreen: Static.TypeScreen,
) : AdvancedScreen() {

    private val panelImg = Image(game.allAssets.panel)
    private val nameImg  = Image(getNameRegionByTypeScreen())
//    private val tigerImg = Image(game.allAssets.mini_tiger)

    protected val tmpGroup by lazy { TmpGroup(this).apply { color.a = 0f } }

    override fun show() {
        setBackBackground(game.loadingAssets.IncasBackground.region)
        super.show()
    }

    final override fun AdvancedStage.addActorsOnStageUI() {
        addPanelImg()
        addNameImg()

        addAndFillActor(tmpGroup)
        tmpGroup.addActorsOnTmpGroup()
        tmpGroup.animShow(TIME_ANIM)

        addTigerImg()
    }

    abstract fun AdvancedGroup.addActorsOnTmpGroup()

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)
        panelImg.setBounds(159f, 122f, 788f, 1290f)
    }

    private fun AdvancedStage.addNameImg() {
        addActor(nameImg)
        nameImg.setBounds(266f, 1436f, 547f, 152f)
    }

    private fun AdvancedStage.addTigerImg() {
        //addActor(tigerImg)
        //tigerImg.setBounds(0f, 0f, 512f, 698f)
        //tigerImg.disable()
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getNameRegionByTypeScreen(): TextureRegion = when(typeScreen) {
        Menu    -> game.allAssets.menu
        Rules   -> game.allAssets.rules
        Setting -> game.allAssets.setting
        Level   -> game.allAssets.level
    }

    protected fun animHideTmpGroup(block: () -> Unit) {
        tmpGroup.animHide(TIME_ANIM, block)
    }

    // ---------------------------------------------------
    // Class
    // ---------------------------------------------------

    object Static {
        enum class TypeScreen {
            Menu, Rules, Setting, Level
        }
    }

}