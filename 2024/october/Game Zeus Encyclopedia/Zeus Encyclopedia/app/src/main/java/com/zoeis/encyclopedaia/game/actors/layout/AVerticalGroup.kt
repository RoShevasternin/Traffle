package com.zoeis.encyclopedaia.game.actors.layout

import com.badlogic.gdx.scenes.scene2d.Actor
import com.zoeis.encyclopedaia.game.utils.advanced.AdvancedGroup
import com.zoeis.encyclopedaia.game.utils.advanced.AdvancedScreen

open class AVerticalGroup(
    override val screen: AdvancedScreen,
    val gap      : Float = 0f,
    val startGap : Float = 0f,
    val endGap   : Float = 0f,
    val alignmentV: Layout.AlignmentVertical   = Layout.AlignmentVertical.TOP,
    val alignmentH: Layout.AlignmentHorizontal = Layout.AlignmentHorizontal.START,
    val direction : Static.Direction = Static.Direction.DOWN,
    val isWrap    : Boolean = false
) : AdvancedGroup() {

    private var ny        = 0f
    private var newHeight = 0f

    override fun getPrefWidth(): Float {
        return width
    }

    override fun getPrefHeight(): Float {
        if (isWrap) {
            newHeight = 0f
            children.onEach { newHeight += it.height + gap }

            newHeight -= gap
            newHeight += (startGap + endGap)

            if (newHeight > height) height = newHeight else newHeight = height
        } else {
            newHeight = height
        }

        return newHeight
    }

    override fun addActorsOnGroup() {}

    override fun childrenChanged() {
        super.childrenChanged()

        update()
    }

    // Logic ------------------------------------------------------------------------

    fun update() {
        getPrefHeight()

        placeChildren()
    }

    private fun placeChildren() {
        when (alignmentV) {
            Layout.AlignmentVertical.TOP -> {
                ny = height

                when (direction) {
                    Static.Direction.DOWN -> children.onEachIndexed { index, a -> a.moveFromTOP(index) }
                    Static.Direction.UP -> children.reversed().onEachIndexed { index, a -> a.moveFromTOP(index) }
                }
            }
            Layout.AlignmentVertical.CENTER -> {
                val childrenHeight = children.map { it.height }.sum()
                val gapHeight      = (gap * children.count().dec())
                ny = (height / 2) - ((childrenHeight + gapHeight) / 2)

                when (direction) {
                    Static.Direction.UP -> children.onEach { a -> a.moveFromCENTER() }
                    Static.Direction.DOWN -> children.reversed().onEach { a -> a.moveFromCENTER() }
                }
            }
            Layout.AlignmentVertical.BOTTOM -> {
                ny = 0f

                when (direction) {
                    Static.Direction.UP -> children.onEachIndexed { index, a -> a.moveFromBOTTOM(index) }
                    Static.Direction.DOWN -> children.reversed().onEachIndexed { index, a -> a.moveFromBOTTOM(index) }
                }
            }
            Layout.AlignmentVertical.AUTO -> {
                val childrenHeight = children.map { it.height }.sum()
                val gapHeight      = (height - childrenHeight) / children.count().dec()

                ny = height

                when (direction) {
                    Static.Direction.UP -> children.reversed().onEach { a -> a.moveFromAUTO(gapHeight) }
                    Static.Direction.DOWN -> children.onEach { a -> a.moveFromAUTO(gapHeight) }
                }
            }
        }

        when(alignmentH) {
            Layout.AlignmentHorizontal.START -> {
                children.onEach { it.x = 0f }
            }
            Layout.AlignmentHorizontal.CENTER -> {
                children.onEach { it.x = (width / 2) - (it.width / 2) }
            }
            Layout.AlignmentHorizontal.END -> {
                children.onEach { it.x = width - it.width }
            }
            Layout.AlignmentHorizontal.AUTO -> {}
        }
    }

    private fun Int.gap() = (if (this == 0) startGap else gap)

    private fun Actor.moveFromTOP(index: Int) {
        ny -= (index.gap() + height)
        y  = ny
    }

    private fun Actor.moveFromBOTTOM(index: Int) {
        ny += index.gap()
        y  = ny
        ny += height
    }

    private fun Actor.moveFromCENTER() {
        y = ny
        ny += (height + gap)
    }

    private fun Actor.moveFromAUTO(autoGap: Float) {
        ny -= height
        y  = ny
        ny -= autoGap
    }

    object Static {
        enum class Direction {
            UP, DOWN
        }
    }

}