package com.pink.plinuirtaster.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.actors.AButton
import com.pink.plinuirtaster.game.screens.SettingsScreen.Companion.IS_THEME_LIGHT
import com.pink.plinuirtaster.game.utils.TIME_ANIM
import com.pink.plinuirtaster.game.utils.actor.animHideScreen
import com.pink.plinuirtaster.game.utils.actor.animShowScreen
import com.pink.plinuirtaster.game.utils.actor.setOnClickListener
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedStage
import com.pink.plinuirtaster.game.utils.region
import com.pink.plinuirtaster.game.utils.runGDX
import kotlinx.coroutines.launch

class ChooseScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var SELECTED_CARD = CardType.EASY
    }

    enum class CardType {
        EASY, MIDDLE, HARD
    }

    private val btnBeck     = AButton(this, AButton.Static.Type.Back)
    private val imgChoose   = Image(game.all.CHOOSE)
    private val imgEMH      = Image(game.all.EMH)
    private val imgCardList = List(3) { Image(game.all.CARD) }

    override fun show() {
        setBackBackground(if (IS_THEME_LIGHT) game.splash.BACKGROUND.region else game.all.Dark.region)

        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBack()
                addImgChoose()
                addImgEMH()
                addImgCardList()
            }
        }
    }

    private fun AdvancedStage.addBack() {
        addActor(btnBeck)
        btnBeck.apply {
            setBounds(50f,1795f,140f,76f)
            setOnClickListener {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgChoose() {
        addActor(imgChoose)
        imgChoose.setBounds(88f,1387f,919f,276f)
    }

    private fun AdvancedStage.addImgEMH() {
        addActor(imgEMH)
        imgEMH.setBounds(185f,711f,714f,62f)
    }

    private fun AdvancedStage.addImgCardList() {
        var nx = 124f
        imgCardList.onEachIndexed { index, image ->
            addActor(image)
            image.setBounds(nx, 784f, 256f, 418f)
            nx += 32+256

            image.setOnClickListener(game.soundUtil) {
                SELECTED_CARD = CardType.entries[index]
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(TaskScreen::class.java.name, ChooseScreen::class.java.name)
                }
            }
        }
    }

}