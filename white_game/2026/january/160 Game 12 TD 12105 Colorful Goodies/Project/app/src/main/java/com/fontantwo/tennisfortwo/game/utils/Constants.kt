package com.fontantwo.tennisfortwo.game.utils

import com.badlogic.gdx.math.Vector2
import kotlin.math.PI

const val WIDTH_UI     = 1564f
const val HEIGHT_UI    = 920f
const val WIDTH_BOX2D  = 14f
const val HEIGHT_BOX2D = 8.23529f

const val METER_UI = WIDTH_UI / WIDTH_BOX2D

val Vector2.toB2 get() = this.divOr0(METER_UI) // convert UI to Box2d
val Vector2.toUI get() = this.scl(METER_UI) // convert Box2d to UI
val Float.toB2 get() = this.divOr0(METER_UI) // convert UI to Box2d
val Float.toUI get() = this * METER_UI // convert Box2d to UI

const val DEGTORAD = (PI / 180f).toFloat()
const val RADTODEG = (180f / PI).toFloat()

const val JOINT_WIDTH = 1f

const val TIME_ANIM_SCREEN_ALPHA = 0.5f