package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.utils.TIME_ANIM
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.actor.setOnClickListener
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import com.pinlq.esst.bloo.game.utils.region
import com.pinlq.esst.bloo.game.utils.runGDX
import kotlinx.coroutines.launch

class QuestScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val imgCard = Image(game.all.quest_card)

    override fun show() {
          setBackBackground(game.splash.listBackground[MenuScreen.BACKGROUND_INDEX].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgCard()
            }
        }
    }

    private fun AdvancedStage.addImgCard() {
        addActor(imgCard)
        imgCard.setBounds(39f,252f,1000f,1400f)
        imgCard.setOnClickListener {
            game.soundUtil.apply { play(CARD, 0.5f) }

            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(QuestShowScreen::class.java.name)
            }
        }
    }

}