package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBox
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBoxGroup
import com.colderonetrains.battlesskates.game.utils.Acts
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame

class ABoxABIP(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val boxAll = ACheckBox(screen, ACheckBox.Type.ALL)
    private val box1   = ACheckBox(screen, ACheckBox.Type.BEGINNER_2)
    private val box2   = ACheckBox(screen, ACheckBox.Type.INTERMEDIATE_2)
    private val box3   = ACheckBox(screen, ACheckBox.Type.PRO_2)

    private val imgPimpa = Image(gdxGame.assetsAll.PIMPA_2)

    // Field
    var blockALL          = {}
    var blockBEGINNER     = {}
    var blockINTERMEDIATE = {}
    var blockPRO          = {}

    override fun addActorsOnGroup() {
        addActors(boxAll, box1, box2, box3, imgPimpa)
        boxAll.setBounds(4f, 24f, 116f, 58f)
        box1.setBounds(146f, 24f, 201f, 58f)
        box2.setBounds(427f, 24f, 289f, 58f)
        box3.setBounds(796f, 24f, 106f, 58f)
        imgPimpa.setBounds(0f, 0f, 70f, 8f)

        val cbg = ACheckBoxGroup()
        boxAll.checkBoxGroup = cbg
        box1.checkBoxGroup = cbg
        box2.checkBoxGroup = cbg
        box3.checkBoxGroup = cbg

        boxAll.check(false)

        boxAll.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockALL()

                imgPimpa.clearActions()
                imgPimpa.addAction(Acts.moveTo(0f, 0f, 0.25f))
            }
        }
        box1.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockBEGINNER()

                imgPimpa.clearActions()
                imgPimpa.addAction(Acts.moveTo(211f, 0f, 0.25f))
            }
        }
        box2.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockINTERMEDIATE()

                imgPimpa.clearActions()
                imgPimpa.addAction(Acts.moveTo(536f, 0f, 0.25f))
            }
        }
        box3.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockPRO()

                imgPimpa.clearActions()
                imgPimpa.addAction(Acts.moveTo(799f, 0f, 0.25f))
            }
        }
    }

}