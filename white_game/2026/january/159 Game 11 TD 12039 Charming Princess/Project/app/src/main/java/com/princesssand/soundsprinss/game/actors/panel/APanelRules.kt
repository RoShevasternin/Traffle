package com.princesssand.soundsprinss.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.princesssand.soundsprinss.game.actors.ATutorials
import com.princesssand.soundsprinss.game.actors.button.AButton
import com.princesssand.soundsprinss.game.utils.actor.disable
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedGroup
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedScreen

class APanelRules(override val screen: AdvancedScreen): AdvancedGroup() {

    private val panel = Image(screen.game.assetsAll.panel)
    private val rules = Image(screen.game.assetsAll.rules)
    private val back  = AButton(screen, AButton.Static.Type.Back)

    var hideBlock: (() -> Unit) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(panel)
        addRules()
        addBack()

        if (screen.game.isTutorialsUtil.isTutorials) {
            when (ATutorials.STEP) {
                ATutorials.Static.Step.RulesBack -> {
                    children.onEach { it.disable() }
                    back.enable()
                }
                else -> {}
            }
        }
    }

    // Add Actors
    private fun addRules() {
        addActor(rules)
        rules.setBounds(73f, 95f, 690f, 287f)
    }

    private fun addBack() {
        addActor(back)
        back.setBounds(676f, 25f, 98f, 58f)
        back.setOnClickListener {
            hideBlock {
                if (screen.game.isTutorialsUtil.isTutorials) ATutorials.nextStep()
                screen.game.navigationManager.back()
            }
        }
    }

}