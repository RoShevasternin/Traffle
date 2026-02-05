/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.utils

import com.novaburst.pixelrally.game.data.DataAvatar
import com.novaburst.pixelrally.game.data.DataLocation
import com.novaburst.pixelrally.game.utils.actor.PosSize

const val WIDTH_UI  = 1080f
const val HEIGHT_UI = 1920f

const val TIME_ANIM_SCREEN = 0.250f

const val SHARED_KEY = "SHARED_GAME_DATA"

const val KEY_GOLD_PER_HOUR = "gold_per_hour"
const val KEY_LAST_UPDATE_TIME = "last_update_time"

const val MAX_LEVEL_JACKPOT = 27

const val ITEM_JACKPOT_INDEX = 100

val GLOBAL_listDataAvatar = listOf(
    DataAvatar(0,  50,   0), // Free Avatar

    DataAvatar(1,  100,  100),
    DataAvatar(2,  200,  200),
    DataAvatar(3,  250,  250),
    DataAvatar(4,  280,  280),
    DataAvatar(5,  333,  333),
    DataAvatar(6,  350,  350),
    DataAvatar(7,  390,  390),
    DataAvatar(8,  450,  450),
    DataAvatar(9,  490,  490),
    DataAvatar(10, 555,  555),
    DataAvatar(11, 630,  630),
    DataAvatar(12, 775,  775),
    DataAvatar(13, 850,  850),
    DataAvatar(14, 900,  900),
    DataAvatar(15, 1000, 1000),
)

val GLOBAL_listDataLocation = listOf(
    DataLocation(0, 1000, 0, "City Street Impact Zone", List(27) { it + 1 }, PosSize(75f, 98f, 427f, 568f)), // Available

    DataLocation(1, 5000,   5,  "Harbor Cargo Explosion", List(MAX_LEVEL_JACKPOT) { (it + 1) * 2 }, PosSize(42f, 98f, 445f, 593f)),
    DataLocation(2, 10_000, 10, "Warehouse Breakdown",    List(MAX_LEVEL_JACKPOT) { (it + 1) * 3 }, PosSize(43f, 98f, 469f, 624f)),
    DataLocation(3, 20_000, 20, "Rooftop Collapse Site",  List(MAX_LEVEL_JACKPOT) { (it + 1) * 4 }, PosSize(64f, 64f, 451f, 602f)),
    DataLocation(4, 30_000, 30, "Suburban Smash Block",   List(MAX_LEVEL_JACKPOT) { (it + 1) * 5 }, PosSize(106f, 125f, 383f, 511f)),
    DataLocation(5, 40_000, 40, "Industrial Power Yard",  List(MAX_LEVEL_JACKPOT) { (it + 1) * 6 }, PosSize(17f, 77f, 431f, 575f)),
    DataLocation(6, 50_000, 50, "Downtown Vertical Rush", List(MAX_LEVEL_JACKPOT) { (it + 1) * 7 }, PosSize(35f, 60f, 477f, 635f)),
)
