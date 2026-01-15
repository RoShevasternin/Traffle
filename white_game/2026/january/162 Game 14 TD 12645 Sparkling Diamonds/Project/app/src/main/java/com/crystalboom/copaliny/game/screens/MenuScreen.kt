package com.crystalboom.copaliny.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.crystalboom.copaliny.game.LibGDXGame
import com.crystalboom.copaliny.game.actors.AButton
import com.crystalboom.copaliny.game.box2d.WorldUtil
import com.crystalboom.copaliny.game.utils.TIME_ANIM
import com.crystalboom.copaliny.game.utils.actor.animHide
import com.crystalboom.copaliny.game.utils.actor.animShow
import com.crystalboom.copaliny.game.utils.actor.setBounds
import com.crystalboom.copaliny.game.utils.actor.setOnClickListener
import com.crystalboom.copaliny.game.utils.advanced.AdvancedBox2dScreen
import com.crystalboom.copaliny.game.utils.advanced.AdvancedStage
import com.crystalboom.copaliny.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    companion object {
        private var isFirst = true
    }

    private val imgLogo = Image(game.all.dms)

    private val btnSweet = AButton(this, AButton.Static.Type.A)
    private val btnStar  = AButton(this, AButton.Static.Type.B)
    private val btnOclic = AButton(this, AButton.Static.Type.C)
    private val btnExit  = AButton(this, AButton.Static.Type.Ext)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply {
                music = ilumina.apply {
                    isLooping = true
                    volumeLevelFlow.value = 18f
                }
            }
        }

        stageUI.root.animHide()
        setBackBackground(game.all.bgs.random().region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        val iop = Image(game.all.hui)
        addActor(iop)
        iop.setBounds(106f, 147f, 580f, 917f)



        //addImgLogo()
        addBtns()

        val actorEsd = Actor()
        addActor(actorEsd)
        actorEsd.apply {
            setBounds(519f, 1007f, 182f, 245f)
            setOnClickListener(game.soundUtil) {
                animHideScreen {
                    game.navigationManager.navigate(YuliaScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }

    }

    private fun AdvancedStage.addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(515f, 1099f, 186f, 169f)
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnSweet, btnStar, btnOclic, btnExit)
        btnSweet.apply {
            setBounds(67f, 810f, 296f, 235f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnStar.apply {
            setBounds(376f, 496f, 277f, 252f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(StarsScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnOclic.apply {
            setBounds(67f, 233f, 272f, 263f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(ShopScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnExit.apply {
            setBounds(469f, 34f, 220f, 148f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.exit()
                }
            }
        }
    }

}