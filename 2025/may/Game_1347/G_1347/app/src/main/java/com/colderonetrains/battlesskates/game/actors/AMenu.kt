package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBox
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBoxGroup
import com.colderonetrains.battlesskates.game.screens.AchievementsScreen
import com.colderonetrains.battlesskates.game.screens.BattleSetupScreen
import com.colderonetrains.battlesskates.game.screens.GameScreen
import com.colderonetrains.battlesskates.game.screens.HomeScreen
import com.colderonetrains.battlesskates.game.screens.RandomScreen
import com.colderonetrains.battlesskates.game.screens.TrickCatalogScreen
import com.colderonetrains.battlesskates.game.utils.Acts
import com.colderonetrains.battlesskates.game.utils.actor.PosSize
import com.colderonetrains.battlesskates.game.utils.actor.setBounds
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame
import com.colderonetrains.battlesskates.util.log

class AMenu(
    override val screen: AdvancedScreen,
    private val checkType: CheckType
): AdvancedGroup() {

    enum class CheckType { TRACK_CATALOG,RANDOM,GAME,ACHIVE }

    private val listBoxType = listOf(
        ACheckBox.Type.TRACK_CATALOG,
        ACheckBox.Type.RANDOM,
        ACheckBox.Type.GAME,
        ACheckBox.Type.ACHIVE,
    )

    private val imgPanel = Image(gdxGame.assetsAll.MENU_PANEL)

    private val listBox = List(4) { ACheckBox(screen, listBoxType[it]) }
    private val btnHome = AButton(screen, AButton.Type.Home)

    // Field
    private var blockTRACK_CATALOG = {}
    private var blockRANDOM        = {}
    private var blockGAME          = {}
    private var blockACHIVE        = {}

    private val listBlock = listOf(::blockTRACK_CATALOG, ::blockRANDOM, ::blockGAME, ::blockACHIVE)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addBtnHome()
        addListBox()

        blockTRACK_CATALOG = {
            log("blockTRACK_CATALOG")
            screen.hideScreen {
                gdxGame.navigationManager.navigate(TrickCatalogScreen::class.java.name, screen::class.java.name)
            }
        }
        blockRANDOM        = {
            log("blockRANDOM")
            screen.hideScreen {
                gdxGame.navigationManager.navigate(RandomScreen::class.java.name, screen::class.java.name)
            }
        }
        blockGAME          = {
            log("blockGAME")
            screen.hideScreen {
                gdxGame.navigationManager.navigate(BattleSetupScreen::class.java.name, screen::class.java.name)
            }
        }
        blockACHIVE        = {
            log("blockACHIVE")
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AchievementsScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addBtnHome() {
        addActor(btnHome)
        btnHome.setBounds(400f, 0f, 180f, 180f)
        btnHome.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(HomeScreen::class.java.name)
            }
        }
    }

    private fun addListBox() {
        val listPosSize = listOf(
            PosSize(36f, 29f, 121f, 121f),
            PosSize(240f, 42f, 96f, 96f),
            PosSize(634f, 42f, 101f, 101f),
            PosSize(838f, 47f, 88f, 88f),
        )

        val cbg = ACheckBoxGroup()

        listBox.forEachIndexed { index, box ->
            box.checkBoxGroup = cbg

            if (checkType.ordinal == index) box.check(false)

            addActor(box)
            box.setBounds(listPosSize[index])

            box.setOnCheckListener {  isCheck ->
                if (isCheck) {
                    listBlock[index].get().invoke()
                }
            }
        }
    }

}