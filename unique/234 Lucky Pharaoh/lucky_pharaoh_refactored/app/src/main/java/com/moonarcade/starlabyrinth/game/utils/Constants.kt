/*
 * Refactored Application Module
 * Build: F8C6280C
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.utils

import com.moonarcade.starlabyrinth.game.data.AvatarData
import com.moonarcade.starlabyrinth.game.data.LocationData
import com.moonarcade.starlabyrinth.game.utils.actor.PosSize

const val WIDTH_UI = 1080f
const val HEIGHT_UI = 1920f

const val TIME_ANIM_SCREEN = 0.250f

const val SHARED_KEY = "SHARED_GAME_DATA"

const val KEY_GOLD_PER_HOUR = "gold_per_hour"
const val KEY_LAST_UPDATE_TIME = "last_update_time"

const val MAX_LEVEL_JACKPOT = 27

const val ITEM_JACKPOT_INDEX = 100

val GLOBAL_listDataAvatar = listOf(
    AvatarData(0,  50,   0), // Free Avatar

    AvatarData(1,  100,  100),
    AvatarData(2,  200,  200),
    AvatarData(3,  250,  250),
    AvatarData(4,  280,  280),
    AvatarData(5,  333,  333),
    AvatarData(6,  350,  350),
    AvatarData(7,  390,  390),
    AvatarData(8,  450,  450),
    AvatarData(9,  490,  490),
    AvatarData(10, 555,  555),
    AvatarData(11, 630,  630),
    AvatarData(12, 775,  775),
    AvatarData(13, 850,  850),
    AvatarData(14, 900,  900),
    AvatarData(15, 1000, 1000),
)

val GLOBAL_listDataLocation = listOf(
    LocationData(0, 1000, 0, "Burial Treasures", List(27) { it + 1 }, PosSize(4f, 91f, 587f, 587f)), // Available

    LocationData(1, 5000,   5,  "Golden Vault", List(MAX_LEVEL_JACKPOT) { (it + 1) * 2 }, PosSize(4f, 91f, 587f, 587f)),
    LocationData(2, 10_000, 10, "Anubis' Temple", List(MAX_LEVEL_JACKPOT) { (it + 1) * 3 }, PosSize(4f, 91f, 587f, 587f)),
    LocationData(3, 20_000, 20, "Sand Dig", List(MAX_LEVEL_JACKPOT) { (it + 1) * 4 }, PosSize(4f, 91f, 587f, 587f)),
    LocationData(4, 30_000, 30, "Pharaoh's Throne", List(MAX_LEVEL_JACKPOT) { (it + 1) * 5 }, PosSize(4f, 91f, 587f, 587f)),
    LocationData(5, 40_000, 40, "Relic Chamber", List(MAX_LEVEL_JACKPOT) { (it + 1) * 6 }, PosSize(4f, 91f, 587f, 587f)),
    LocationData(6, 50_000, 50, "Gold Treasury", List(MAX_LEVEL_JACKPOT) { (it + 1) * 7 }, PosSize(4f, 91f, 587f, 587f)),
)
