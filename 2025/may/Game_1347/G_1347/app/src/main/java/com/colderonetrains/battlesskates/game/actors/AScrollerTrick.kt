package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.colderonetrains.battlesskates.game.actors.autoLayout.AVerticalGroup
import com.colderonetrains.battlesskates.game.dataStore.LevelType
import com.colderonetrains.battlesskates.game.utils.GameColor
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.font.FontParameter
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AScrollerTrick(
    override val screen: AdvancedScreen,
    typeTrick: TypeTrick,
): AdvancedGroup() {

    enum class TypeTrick {
        All, Beginner, Intermediate, Pro
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font43        = screen.fontGenerator_GTWalsheimPro_Bold.generateFont(fontParameter.setSize(43))

    private val ls43 = Label.LabelStyle(font43, Color.WHITE)

    private val verticalGroup = AVerticalGroup(screen, 15f, paddingBottom = 100f, isWrap = true)
    private val scroll        = AScrollPane(verticalGroup)

    private val items = when(typeTrick) {
        TypeTrick.All          -> gdxGame.ds_DataDidItTrick.flow.value
        TypeTrick.Beginner     -> gdxGame.ds_DataDidItTrick.flow.value.filter { it.type == LevelType.Beginner }
        TypeTrick.Intermediate -> gdxGame.ds_DataDidItTrick.flow.value.filter { it.type == LevelType.Intermediate }
        TypeTrick.Pro          -> gdxGame.ds_DataDidItTrick.flow.value.filter { it.type == LevelType.Pro }
    }

    private val listADidItItem = List(items.size) { ADidItItem(screen, items[it].nameTrick, ls43) }

    override fun addActorsOnGroup() {
        addAndFillActor(scroll)
        verticalGroup.addActorsToVerticalGroup()
    }

    private fun AVerticalGroup.addActorsToVerticalGroup() {
        setSize(width, height)

        listADidItItem.forEach {
            it.setSize(930f, 107f)
            addActor(it)
        }
    }

}