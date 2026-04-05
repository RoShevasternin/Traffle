package com.bramlix.cas888.casino.game.manager

import android.content.Context
import android.media.AudioManager
import com.bramlix.cas888.casino.appContext

object AudioManager {

    val audioManager      = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val maxVolumeLevel    = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    val volumeLevel get() = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    val onePercentVolumeLevel = (maxVolumeLevel / 100f)

    val volumeLevel_100 get() = (volumeLevel / onePercentVolumeLevel).toInt()

}