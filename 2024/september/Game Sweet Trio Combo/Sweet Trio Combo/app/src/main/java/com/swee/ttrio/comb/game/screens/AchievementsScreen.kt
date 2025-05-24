package com.swee.ttrio.comb.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.swee.ttrio.comb.game.LibGDXGame
import com.swee.ttrio.comb.game.actors.AButton
import com.swee.ttrio.comb.game.actors.AProgress
import com.swee.ttrio.comb.game.actors.checkbox.ACheckBox
import com.swee.ttrio.comb.game.utils.TIME_ANIM
import com.swee.ttrio.comb.game.utils.actor.animHide
import com.swee.ttrio.comb.game.utils.actor.animShow
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.advanced.AdvancedStage
import com.swee.ttrio.comb.game.utils.region
import com.swee.ttrio.comb.game.utils.runGDX
import kotlinx.coroutines.launch
import kotlin.random.Random

class AchievementsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val btnMenu = AButton(this, AButton.Static.Type.Menu)
    private val btnSett = AButton(this, AButton.Static.Type.Sett)
    private val imgACHI = Image(game.all.ACHIEVEMENTS)
    private val boxList = List(6) { ACheckBox(this, ACheckBox.Static.listABCDEF[it]) }

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.BACKGROUND_2.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addSett()
                addImgACHI()
                addBoxes()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(26f,764f,51f,51f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addSett() {
        addActor(btnSett)
        btnSett.apply {
            setBounds(313f,764f,51f,51f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(SettingScreen::class.java.name, AchievementsScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addImgACHI() {
        addActor(imgACHI)
        imgACHI.setBounds(0f,0f,390f,749f)
    }

    private fun AdvancedStage.addBoxes() {
        var nx = 45f
        var ny = 467f

        boxList.onEachIndexed { index, aCheckBox ->
            addActor(aCheckBox)
            aCheckBox.disable()
            if (Random.nextBoolean()) aCheckBox.check()
            aCheckBox.setBounds(nx,ny,134f,156f)
            nx += 32+134
            if (index.inc() % 2 == 0) {
                nx = 45f
                ny -= 59+156
            }
        }
    }

}