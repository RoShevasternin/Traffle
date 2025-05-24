package com.pixe.lkicko.perlin.game.dataStore

import android.content.SharedPreferences

class DataStore(private val sharedPreferences: SharedPreferences) {

    private val keyPX        = "key_px"
    private val keyLVL       = "key_lvl"
    private val keyBonusTime = "key_bonus_time"
    private val keyBonusX3   = "key_bonus_x3"
    private val keyDay       = "key_day"
    private val keyDate      = "key_date"
    private val keyIsGetBonus = "key_is_get_bonus"

    private val keyIsCompleteLevelByIndex = "key_is_complete_level_by_index"
    private val keyIsGetBonusLevelByIndex = "key_is_get_bonus_level_by_index"

    var px = sharedPreferences.getInt(keyPX, 1_000)
        private set
    var lvl = sharedPreferences.getInt(keyLVL, 1)
        private set
    var bonusTime = sharedPreferences.getInt(keyBonusTime, 0)
        private set
    var bonusX3 = sharedPreferences.getInt(keyBonusX3, 0)
        private set
    var day = sharedPreferences.getInt(keyDay, 0)
        private set
    var date = sharedPreferences.getInt(keyDate, 0)
        private set
    var isGetBonus = sharedPreferences.getBoolean(keyIsGetBonus, false)
        private set

    fun getIsCompleteLevelByIndex(index: Int): Boolean {
        return sharedPreferences.getBoolean(keyIsCompleteLevelByIndex + index, false)
    }
    fun getIsGetBonusLevelByIndex(index: Int): Boolean {
        return sharedPreferences.getBoolean(keyIsGetBonusLevelByIndex + index, false)
    }


    fun updatePX(block: (Int) -> Int) {
        px = block(px)
        sharedPreferences.edit().putInt(keyPX, px).apply()
    }
    fun updateLVL(block: (Int) -> Int) {
        lvl = block(lvl)
        sharedPreferences.edit().putInt(keyLVL, lvl).apply()
    }
    fun updateBonusTime(block: (Int) -> Int) {
        bonusTime = block(bonusTime)
        sharedPreferences.edit().putInt(keyBonusTime, bonusTime).apply()
    }
    fun updateBonusX3(block: (Int) -> Int) {
        bonusX3 = block(bonusX3)
        sharedPreferences.edit().putInt(keyBonusX3, bonusX3).apply()
    }
    fun updateDay(block: (Int) -> Int) {
        day = block(day)
        sharedPreferences.edit().putInt(keyDay, day).apply()
    }
    fun updateDate(block: (Int) -> Int) {
        date = block(date)
        sharedPreferences.edit().putInt(keyDate, date).apply()
    }
    fun updateIsGetBonus(block: (Boolean) -> Boolean) {
        isGetBonus = block(isGetBonus)
        sharedPreferences.edit().putBoolean(keyIsGetBonus, isGetBonus).apply()
    }
    fun updateIsCompleteLevelByIndex(index: Int, block: (Boolean) -> Boolean) {
        val result = block(getIsCompleteLevelByIndex(index))
        sharedPreferences.edit().putBoolean(keyIsCompleteLevelByIndex + index, result).apply()
    }
    fun updateIsGetBonusLevelByIndex(index: Int, block: (Boolean) -> Boolean) {
        val result = block(getIsGetBonusLevelByIndex(index))
        sharedPreferences.edit().putBoolean(keyIsGetBonusLevelByIndex + index, result).apply()
    }

}