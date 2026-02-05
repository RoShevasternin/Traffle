package com.moonarcade.starlabyrinth.game.utils

import com.moonarcade.starlabyrinth.game.data.DataAvatar
import com.moonarcade.starlabyrinth.game.data.DataLocation
import com.moonarcade.starlabyrinth.game.utils.actor.PosSize

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
    DataLocation(0, 1000, 0, "Burial Treasures", List(27) { it + 1 }, PosSize(4f, 91f, 587f, 587f)), // Available

    DataLocation(1, 5000,   5,  "Golden Vault", List(MAX_LEVEL_JACKPOT) { (it + 1) * 2 }, PosSize(4f, 91f, 587f, 587f)),
    DataLocation(2, 10_000, 10, "Anubis' Temple", List(MAX_LEVEL_JACKPOT) { (it + 1) * 3 }, PosSize(4f, 91f, 587f, 587f)),
    DataLocation(3, 20_000, 20, "Sand Dig", List(MAX_LEVEL_JACKPOT) { (it + 1) * 4 }, PosSize(4f, 91f, 587f, 587f)),
    DataLocation(4, 30_000, 30, "Pharaoh's Throne", List(MAX_LEVEL_JACKPOT) { (it + 1) * 5 }, PosSize(4f, 91f, 587f, 587f)),
    DataLocation(5, 40_000, 40, "Relic Chamber", List(MAX_LEVEL_JACKPOT) { (it + 1) * 6 }, PosSize(4f, 91f, 587f, 587f)),
    DataLocation(6, 50_000, 50, "Gold Treasury", List(MAX_LEVEL_JACKPOT) { (it + 1) * 7 }, PosSize(4f, 91f, 587f, 587f)),
)
