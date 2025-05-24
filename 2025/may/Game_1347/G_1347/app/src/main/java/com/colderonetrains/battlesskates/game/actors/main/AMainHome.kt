package com.colderonetrains.battlesskates.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.screens.AchievementsScreen
import com.colderonetrains.battlesskates.game.screens.BattleSetupScreen
import com.colderonetrains.battlesskates.game.screens.HomeScreen
import com.colderonetrains.battlesskates.game.screens.RandomScreen
import com.colderonetrains.battlesskates.game.screens.TrickCatalogScreen
import com.colderonetrains.battlesskates.game.utils.*
import com.colderonetrains.battlesskates.game.utils.actor.animDelay
import com.colderonetrains.battlesskates.game.utils.actor.animHide
import com.colderonetrains.battlesskates.game.utils.actor.animShow
import com.colderonetrains.battlesskates.game.utils.actor.setOnClickListener
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainGroup

class AMainHome(override val screen: HomeScreen): AdvancedMainGroup() {

    private val imgWhats = Image(gdxGame.assetsAll.WHATS)
    private val listBtn  = List(4) { Actor() }

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgWhats()
        addBtns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgWhats() {
        addActor(imgWhats)
        imgWhats.setBounds(63f, 251f, 953f, 1468f)
    }

    private fun addBtns() {
        var ny = 1126f

        val listScreenName = listOf(
            TrickCatalogScreen::class.java.name,
            RandomScreen::class.java.name,
            BattleSetupScreen::class.java.name,
            AchievementsScreen::class.java.name,
        )

        listBtn.forEachIndexed { index, button ->
            addActor(button)
            button.setBounds(63f, ny, 953f, 257f)

            ny -= 35 + 257

            button.setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(listScreenName[index], screen::class.java.name)
                }
            }
        }

    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}