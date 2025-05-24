package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viade.bepuzzle.game.screens.*
import com.viade.bepuzzle.game.utils.actor.setOnClickListener
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen

class ASideMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listScreenName = listOf(
        GalleryScreen::class.java.name,
        AwordsScreen::class.java.name,
        ShopScreen::class.java.name,
        SettingsScreen::class.java.name,
    )

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.assetsAll.menu))

        var ny    = 879f
        val listA = List(5) { Actor() }
        listA.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(53f, ny, 338f, 100f)
            ny -= (74 + 100)

            actor.setOnClickListener(screen.game.soundUtil) {
                screen.hideScreen {
                    if (index != 4) screen.game.navigationManager.navigate(listScreenName[index], screen::class.java.name)
                    else screen.game.navigationManager.exit()
                }
            }
        }
    }

}