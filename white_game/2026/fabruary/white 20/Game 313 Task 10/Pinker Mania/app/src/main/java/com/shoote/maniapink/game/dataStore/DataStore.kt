package com.shoote.maniapink.game.dataStore

import android.content.SharedPreferences

class DataStore(private val sharedPreferences: SharedPreferences) {

    private val keyStar       = "key_star"
    private val keyBomb       = "key_bomb"
    private val keyLaser      = "key_laser"
    private val keyBackground = "key_background"

    var star = sharedPreferences.getInt(keyStar, 350)
        private set
    var isBomb = sharedPreferences.getBoolean(keyBomb, false)
        private set
    var isLaser = sharedPreferences.getBoolean(keyLaser, false)
        private set
    var isBackground = sharedPreferences.getBoolean(keyBackground, false)
        private set

    fun updateStar(block: (Int) -> Int) {
        star = block(star)
        sharedPreferences.edit().putInt(keyStar, star).apply()
    }
    fun updateIsBomb(value: Boolean) {
        isBomb = value
        sharedPreferences.edit().putBoolean(keyBomb, isBomb).apply()
    }
    fun updateIsLaser(value: Boolean) {
        isLaser = value
        sharedPreferences.edit().putBoolean(keyLaser, isLaser).apply()
    }
    fun updateIsBackground(value: Boolean) {
        isBackground = value
        sharedPreferences.edit().putBoolean(keyBackground, isBackground).apply()
    }

}