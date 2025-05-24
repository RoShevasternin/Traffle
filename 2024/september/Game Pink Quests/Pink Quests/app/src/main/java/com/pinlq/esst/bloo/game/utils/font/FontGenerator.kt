package com.pinlq.esst.bloo.game.utils.font

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.Disposable
import com.pinlq.esst.bloo.game.utils.disposeAll

class FontGenerator(fontPath: FontPath): FreeTypeFontGenerator(Gdx.files.internal(fontPath.path)) {

    private val disposableSet = mutableSetOf<Disposable>()

    override fun generateFont(parameter: FreeTypeFontParameter?): BitmapFont {
        val font = super.generateFont(parameter)
        disposableSet.add(font)
        return font
    }

    override fun dispose() {
        super.dispose()
        disposableSet.disposeAll()
        disposableSet.clear()
    }

    companion object {
        enum class FontPath(val path: String) {
            IrishGrover_Regular("font/IrishGrover-Regular.ttf"),
            Jost_Regular       ("font/Jost-Regular.ttf"),
            Jost_SemiBold      ("font/Jost-SemiBold.ttf"),
        }
    }

}