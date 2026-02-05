/*
 * Refactored Application Module
 * Build: 28C4EA8D
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.autoLayout

import com.badlogic.gdx.scenes.scene2d.Actor
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen

open class TableLayout(
    override val screen: BaseScreen,

    private var spaceVertical  : Float = 0f,
    private var spaceHorizontal: Float = 0f,

    private var paddingTop   : Float = 0f,
    private var paddingBottom: Float = 0f,
    private var paddingLeft  : Float = 0f,
    private var paddingRight : Float = 0f,

    private var alignmentVertical  : AutoLayout.AlignmentVertical = AutoLayout.AlignmentVertical.TOP,
    private var alignmentHorizontal: AutoLayout.AlignmentHorizontal = AutoLayout.AlignmentHorizontal.LEFT,
    private var directionVertical  : AutoLayout.DirectionVertical = AutoLayout.DirectionVertical.DOWN,

    private var alignmentItemsVertical  : AutoLayout.AlignmentVertical = AutoLayout.AlignmentVertical.BOTTOM,
    private var alignmentItemsHorizontal: AutoLayout.AlignmentHorizontal = AutoLayout.AlignmentHorizontal.LEFT,
    private var directionHorizontal     : AutoLayout.DirectionHorizontal = AutoLayout.DirectionHorizontal.RIGHT,

    private var isWrap: Boolean = false,
) : AVerticalGroup(
    screen = screen,
    space = spaceVertical,
    paddingTop = paddingTop,
    paddingBottom = paddingBottom,
    alignmentHorizontal = alignmentHorizontal,
    alignmentVertical = alignmentVertical,
    directionVertical = directionVertical,
    isWrap = isWrap
) {

    private val collectionHorizontalGroup = mutableListOf<AHorizontalGroup>()

    private lateinit var presentHorizontalGroup: AHorizontalGroup

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        presentHorizontalGroup = getNewHorizontalGroup()
        addActor(presentHorizontalGroup)
    }

    private fun getNewHorizontalGroup() =  AHorizontalGroup(
        screen = screen,
        space = spaceHorizontal,
        paddingLeft = paddingLeft,
        paddingRight = paddingRight,
        alignmentVertical = alignmentItemsVertical,
        alignmentHorizontal = alignmentItemsHorizontal,
        directionHorizontal = directionHorizontal,
        isWrapHorizontal = true,
        isWrapVertical = true
    ).also { collectionHorizontalGroup.add(it) }

    fun addActorToTable(actor: Actor) {
        // Перевіряємо, чи актор вміщається в поточну горизонтальну групу
        if (presentHorizontalGroup.width + actor.width > width - (paddingLeft + paddingRight)) {
            // Якщо не вміщається, створюємо нову горизонтальну групу
            presentHorizontalGroup = getNewHorizontalGroup()
            addActor(presentHorizontalGroup)
            //currentHorizontalGroup.debugAll()
        }

        // Додаємо актора до поточної горизонтальної групи
        presentHorizontalGroup.addActor(actor)
        presentHorizontalGroup.layout()
        layout()
    }

    fun setSpaceHorizontal(space: Float) {
        spaceHorizontal = space
        collectionHorizontalGroup.onEach { it.setSpaceHorizontal(space) }
    }

    fun setLeftPadding(padding: Float) {
        paddingLeft = padding
        collectionHorizontalGroup.onEach { it.setLeftPadding(padding) }
    }

    fun setRightPadding(padding: Float) {
        paddingRight = padding
        collectionHorizontalGroup.onEach { it.setRightPadding(padding) }
    }

    fun setAlignmentItemsHorizontal(alignment: AutoLayout.AlignmentHorizontal) {
        alignmentItemsHorizontal = alignment
        collectionHorizontalGroup.onEach { it.setAlignmentHorizontal(alignment) }
    }

    fun setAlignmentItemsVertical(alignment: AutoLayout.AlignmentVertical) {
        alignmentItemsVertical = alignment
        collectionHorizontalGroup.onEach { it.setAlignmentVertical(alignment) }
    }

    fun setDirectionHorizontal(direction: AutoLayout.DirectionHorizontal) {
        directionHorizontal = direction
        collectionHorizontalGroup.onEach { it.setDirectionHorizontal(direction) }
    }

}