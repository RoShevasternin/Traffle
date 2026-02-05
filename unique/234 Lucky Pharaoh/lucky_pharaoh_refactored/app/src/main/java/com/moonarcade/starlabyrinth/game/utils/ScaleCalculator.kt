/*
 * Refactored Application Module
 * Build: 2E89A538
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.utils

import com.badlogic.gdx.math.Vector2

class ScaleCalculator(
    private val axis: Axis,
    private val originalSize: Float? = null
) {

    private var scale = 1f
    private var newAxisSize = 0f

    fun calculateScale(newX: Float, newY: Float) {
        newAxisSize = when(axis) {
            Axis.X -> newX
            Axis.Y -> newY
        }
        originalSize?.let { scale = originalSize.divOr0(newAxisSize) }
    }

    fun calculateScale(newSize: Vector2) {
        calculateScale(newSize.x, newSize.y)
    }

    fun scaled(size: Vector2): Vector2 {
        return size.divOr0(scale)
    }

    fun scaledInverse(size: Vector2): Vector2 {
        return size.scl(scale)
    }

    fun scaled(size: Float): Float {
        return size.divOr0(scale)
    }
    fun scaledInverse(size: Float): Float {
        return (size * scale)
    }

    enum class Axis {
        X, Y
    }

}