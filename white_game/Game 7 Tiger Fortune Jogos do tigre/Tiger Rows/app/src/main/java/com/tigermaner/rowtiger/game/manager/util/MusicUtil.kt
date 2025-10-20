package com.tigermaner.rowtiger.game.manager.util

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import com.tigermaner.rowtiger.game.manager.AudioManager
import com.tigermaner.rowtiger.game.manager.MusicManager
import com.tigermaner.rowtiger.game.utils.runGDX
import com.tigermaner.rowtiger.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MusicUtil: Disposable {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    val JAPAN_MUSIC = MusicManager.EnumMusic.JAPAN_MUSIC.data.music

    // 0..100
    val volumeLevelFlow = 10f //MutableStateFlow(AudioManager.volumeLevelPercent / 100)

    private var _music: Music? = null
    var music: Music?
        get() = _music
        set(value) { runGDX {
            if (value != null) {
                if (_music != value) {
                    _music?.stop()
                    _music = value
                    _music?.volume = volumeLevelFlow / 100f //.value
                    _music?.play()
                }
            } else {
                _music?.stop()
                _music = null
            }
        } }

    init {
        //coroutine.launch { volumeLevelFlow.collect { level -> runGDX { _music?.volume = level / 100f } } }
    }

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
        _music = null
        music  = null
    }

}