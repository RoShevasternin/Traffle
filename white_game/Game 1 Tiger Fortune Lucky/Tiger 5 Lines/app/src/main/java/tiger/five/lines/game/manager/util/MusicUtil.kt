package tiger.five.lines.game.manager.util

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import tiger.five.lines.game.manager.AudioManager
import tiger.five.lines.game.manager.MusicManager
import tiger.five.lines.game.utils.runGDX
import tiger.five.lines.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MusicUtil: Disposable {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    val MUZLO = MusicManager.EnumMusic.MUZLO.data.music

    // 0..100
    val volumeLevelFlow = MutableStateFlow(AudioManager.volumeLevelPercent / 100)

    private var _music: Music? = null
    var music: Music?
        get() = _music
        set(value) { runGDX {
            if (value != null) {
                if (_music != value) {
                    _music?.stop()
                    _music = value
                    _music?.volume = volumeLevelFlow.value
                    _music?.play()
                }
            } else {
                _music?.stop()
                _music = null
            }
        } }

    init {
        coroutine.launch { volumeLevelFlow.collect { level -> runGDX { _music?.volume = level } } }
    }

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
        _music = null
        music  = null
    }

}