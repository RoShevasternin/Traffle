package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBox
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBoxGroup
import com.colderonetrains.battlesskates.game.utils.Acts
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame

class ABoxBIP(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val box1 = ACheckBox(screen, ACheckBox.Type.BEGINNER)
    private val box2 = ACheckBox(screen, ACheckBox.Type.INTERMEDIATE)
    private val box3 = ACheckBox(screen, ACheckBox.Type.PRO)

    private val imgPimpa = Image(gdxGame.assetsAll.PIMPA)

    // Field
    var blockBEGINNER     = {}
    var blockINTERMEDIATE = {}
    var blockPRO          = {}

    override fun addActorsOnGroup() {
        addActors(box1, box2, box3, imgPimpa)
        box1.setBounds(0f, 29f, 220f, 58f)
        box2.setBounds(300f, 29f, 289f, 58f)
        box3.setBounds(671f, 29f, 91f, 58f)
        imgPimpa.setBounds(51f, 0f, 116f, 8f)

        val cbg = ACheckBoxGroup()
        box1.checkBoxGroup = cbg
        box2.checkBoxGroup = cbg
        box3.checkBoxGroup = cbg

        box1.check(false)

        box1.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockBEGINNER()

                imgPimpa.clearActions()
                imgPimpa.addAction(Acts.moveTo(51f, 0f, 0.25f))
            }
        }
        box2.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockINTERMEDIATE()

                imgPimpa.clearActions()
                imgPimpa.addAction(Acts.moveTo(387f, 0f, 0.25f))
            }
        }
        box3.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockPRO()

                imgPimpa.clearActions()
                imgPimpa.addAction(Acts.moveTo(651f, 0f, 0.25f))
            }
        }
    }

}