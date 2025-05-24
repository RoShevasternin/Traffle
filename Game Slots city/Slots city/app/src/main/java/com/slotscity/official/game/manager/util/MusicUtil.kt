package com.slotscity.official.game.manager.util

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import com.slotscity.official.game.utils.runGDX
import com.slotscity.official.game.manager.AudioManager
import com.slotscity.official.game.manager.MusicManager
import com.slotscity.official.game.utils.runGDX
import com.slotscity.official.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MusicUtil: Disposable {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    val MUSIC   = MusicManager.EnumMusic.MUSIC.data.music
    val SUSPECT = MusicManager.EnumMusic.SUSPECT.data.music

    // 0..100
    val volumeLevelFlow = MutableStateFlow(AudioManager.volumeLevelPercent)

    var coff = 1f

    private var _music: Music? = null
    var music: Music?
        get() = _music
        set(value) { runGDX {
            if (value != null) {
                if (_music != value) {
                    _music?.stop()
                    _music = value
                    _music?.volume = (volumeLevelFlow.value / 100f) * coff
                    _music?.play()
                }
            } else {
                _music?.stop()
                _music = null
            }
        } }

    init {
        coroutine.launch { volumeLevelFlow.collect { level -> runGDX { _music?.volume = (level / 100f) * coff } } }
    }

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
        _music = null
        music  = null
    }


    // ---------------------------------------------------
    // class
    // ---------------------------------------------------

    class CarnavalCatMusic {
        val MUSIC = MusicManager.CarnavalCatMusic.MUSIC.data.music
    }

    class TreasureSnipesMusic {
        val MUSIC = MusicManager.TreasureSnipesMusic.MUSIC.data.music
    }

    class SweetBonanzaMusic {
        val MUSIC = MusicManager.SweetBonanzaMusic.MUSIC.data.music
    }

}