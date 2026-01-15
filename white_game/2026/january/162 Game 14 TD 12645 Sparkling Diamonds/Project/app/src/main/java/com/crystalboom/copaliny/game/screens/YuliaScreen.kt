package com.crystalboom.copaliny.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.crystalboom.copaliny.game.LibGDXGame
import com.crystalboom.copaliny.game.actors.AButton
import com.crystalboom.copaliny.game.box2d.WorldUtil
import com.crystalboom.copaliny.game.utils.GColor
import com.crystalboom.copaliny.game.utils.TIME_ANIM
import com.crystalboom.copaliny.game.utils.actor.animHide
import com.crystalboom.copaliny.game.utils.actor.animShow
import com.crystalboom.copaliny.game.utils.advanced.AdvancedBox2dScreen
import com.crystalboom.copaliny.game.utils.advanced.AdvancedStage
import com.crystalboom.copaliny.game.utils.font.FontParameter
import com.crystalboom.copaliny.game.utils.region

class YuliaScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {
    private val imgLogo = Image(game.all.sosite)
    private val btnExit = AButton(this, AButton.Static.Type.Bck)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.bgs.random().region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgLogo()
        addBtns()
    }

    private fun AdvancedStage.addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(46f, 418f, 633f, 566f)
    }

    private fun AdvancedStage.addBtns() {
        addActor(btnExit)
        btnExit.apply {
            setBounds(469f, 34f, 220f, 148f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.back()
                }
            }
        }
    }

}