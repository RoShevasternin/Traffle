package com.sugaraxplosion.candysmoy.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.sugaraxplosion.candysmoy.game.LibGDXGame
import com.sugaraxplosion.candysmoy.game.actors.AButton
import com.sugaraxplosion.candysmoy.game.box2d.WorldUtil
import com.sugaraxplosion.candysmoy.game.utils.TIME_ANIM
import com.sugaraxplosion.candysmoy.game.utils.actor.animHide
import com.sugaraxplosion.candysmoy.game.utils.actor.animShow
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedBox2dScreen
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedStage
import com.sugaraxplosion.candysmoy.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    companion object {
        private var isFirst = true
    }

    private val imgLogo = Image(game.all.logo)
    private val imgGirl = Image(game.all.GIRL)

    private val btnSweet = AButton(this, AButton.Static.Type.Sweet)
    private val btnStar  = AButton(this, AButton.Static.Type.Star)
    private val btnOclic = AButton(this, AButton.Static.Type.Oclic)
    private val btnPlus  = AButton(this, AButton.Static.Type.Plus)
    private val btnExit  = AButton(this, AButton.Static.Type.Exit)

    override fun show() {
        if (isFirst) {
            isFirst = false
//            game.musicUtil.apply {
//                music = ilumina.apply {
//                    isLooping = true
//                    volumeLevelFlow.value = 18f
//                }
//            }
        }

        stageUI.root.animHide()
        setBackBackground(game.splash.LOAD.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgLogo()
        addBtns()
    }

    private fun AdvancedStage.addImgLogo() {
        //addActor(imgLogo)
        //imgLogo.setBounds(18f, 800f, 147f, 147f)

        addActor(imgGirl)
        imgGirl.setBounds(154f, 0f, 279f, 326f)
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnSweet, btnStar, btnOclic, btnPlus, btnExit)
        btnSweet.apply {
            setBounds(141f, 546f, 259f, 181f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnStar.apply {
            setBounds(28f, 369f, 101f, 111f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(StarsScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnOclic.apply {
            setBounds(154f, 369f, 101f, 111f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(InfoScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnPlus.apply {
            setBounds(283f, 369f, 101f, 111f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(ShopScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnExit.apply {
            setBounds(413f, 369f, 101f, 111f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.exit()
                }
            }
        }
    }

}