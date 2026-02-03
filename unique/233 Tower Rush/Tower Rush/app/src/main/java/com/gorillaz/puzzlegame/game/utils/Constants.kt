package com.gorillaz.puzzlegame.game.utils

import com.gorillaz.puzzlegame.game.data.DataAvatar
import com.gorillaz.puzzlegame.game.data.DataLocation
import com.gorillaz.puzzlegame.game.utils.actor.PosSize

const val WIDTH_UI  = 1080f
const val HEIGHT_UI = 1920f

const val TIME_ANIM_SCREEN = 0.250f

const val SHARED_KEY = "SHARED_GAME_DATA"

const val KEY_GOLD_PER_HOUR    = "gold_per_hour"
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
    DataLocation(0, 1000, 0, "JUNGLE OF LUCK", List(27) { it + 1 }, PosSize(1f, 189f, 596f, 626f)), // Available

    DataLocation(1, 5000,   5,  "GOLDEN CANYON",      List(MAX_LEVEL_JACKPOT) { (it + 1) * 2 }, PosSize(0f, 189f, 506f, 533f)),
    DataLocation(2, 10_000, 10, "CRYSTAL CAVES",      List(MAX_LEVEL_JACKPOT) { (it + 1) * 3 }, PosSize(11f, 189f, 446f, 507f)),
    DataLocation(3, 20_000, 20, "VALLEY OF ROULETTO", List(MAX_LEVEL_JACKPOT) { (it + 1) * 4 }, PosSize(107f, 189f, 369f, 560f)),
    DataLocation(4, 30_000, 30, "OCEAN OF TREASURES", List(MAX_LEVEL_JACKPOT) { (it + 1) * 5 }, PosSize(61f, 189f, 456f, 595f)),
    DataLocation(5, 40_000, 40, "CASINO CITY",        List(MAX_LEVEL_JACKPOT) { (it + 1) * 6 }, PosSize(120f, 189f, 335f, 534f)),
    DataLocation(6, 50_000, 50, "VOLCANO OF JACKPOTS",List(MAX_LEVEL_JACKPOT) { (it + 1) * 7 }, PosSize(-79f, 189f, 606f, 601f)),
)
