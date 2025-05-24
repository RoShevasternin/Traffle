package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.colderonetrains.battlesskates.game.actors.autoLayout.AVerticalGroup
import com.colderonetrains.battlesskates.game.data.DataTrick
import com.colderonetrains.battlesskates.game.dataStore.LevelType
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.font.FontParameter

class AScrollerItem(
    override val screen: AdvancedScreen,
    listIcon     : List<Texture>,
    listDataTrick: List<DataTrick>,
    val levelType: LevelType,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font46        = screen.fontGenerator_GTWalsheimPro_Bold.generateFont(fontParameter.setSize(46))
    private val font36        = screen.fontGenerator_GTWalsheimPro_Light.generateFont(fontParameter.setSize(36))

    private val ls46 = Label.LabelStyle(font46, Color.WHITE)
    private val ls36 = Label.LabelStyle(font36, Color.WHITE)

    private val verticalGroup = AVerticalGroup(screen, 52f, isWrap = true)
    private val scroll        = AScrollPane(verticalGroup)

    private val listAItemTrick = List(10) { AItemTrick(
        screen,
        listIcon[it],
        listDataTrick[it],
        ls46, ls36,
        levelType
    ) }

    override fun addActorsOnGroup() {
        addAndFillActor(scroll)
        verticalGroup.addActorsToVerticalGroup()
    }

    private fun AVerticalGroup.addActorsToVerticalGroup() {
        setSize(width, height)

        listAItemTrick.forEachIndexed { index, trick ->
            trick.setSize(910f, 285f)
            addActor(trick)
        }
    }

}