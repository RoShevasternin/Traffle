package com.relaxing.memorygame.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Splash {
        val loader   = LayoutData(148f,1018f,784f,88f)
        val progress = LayoutData(401f,1040f,278f,39f)
    }

    object Tile {
        val tiles5 = listOf(
            LayoutData(113f, 504f, 173f, 177f),
            LayoutData(517f, 504f, 173f, 177f),
            LayoutData(315f, 327f, 173f, 177f),
            LayoutData(113f, 150f, 173f, 177f),
            LayoutData(517f, 150f, 173f, 177f),

            LayoutData(142f, 357f, 115f, 117f),
            LayoutData(748f, 534f, 115f, 117f),
            LayoutData(748f, 357f, 115f, 117f),
            LayoutData(748f, 180f, 115f, 117f),

            LayoutData(360f, 551f, 82f, 84f),
            LayoutData(360f, 197f, 82f, 84f),
            LayoutData(562f, 374f, 82f, 84f),
        )
        val tiles6 = listOf(
            LayoutData(108f, 516f, 173f, 177f),
            LayoutData(108f, 135f, 173f, 177f),
            LayoutData(717f, 135f, 173f, 177f),
            LayoutData(717f, 516f, 173f, 177f),

            LayoutData(140f, 359f, 109f, 111f),
            LayoutData(749f, 359f, 109f, 111f),

            LayoutData(308f, 549f, 109f, 111f),
            LayoutData(445f, 549f, 109f, 111f),
            LayoutData(582f, 549f, 109f, 111f),

            LayoutData(308f, 168f, 109f, 111f),
            LayoutData(445f, 168f, 109f, 111f),
            LayoutData(582f, 168f, 109f, 111f),

            LayoutData(465f, 310f, 68f, 69f),
            LayoutData(465f, 451f, 68f, 69f),

            LayoutData(308f, 344f, 138f, 141f),
            LayoutData(553f, 344f, 138f, 141f),
        )
    }

    data class LayoutData(
        var x: Float = 0f,
        var y: Float = 0f,
        var w: Float = 0f,
        var h: Float = 0f,
        // horizontal space
        val hs: Float = 0f,
        // vertical space
        val vs: Float = 0f,
    ) {

        fun position() = Vector2(x, y)
        fun size() = Vector2(w, h)

    }

}












