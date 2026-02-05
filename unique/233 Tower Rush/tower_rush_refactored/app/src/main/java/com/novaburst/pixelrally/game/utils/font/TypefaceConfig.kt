/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.utils.font

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

class TypefaceConfig : FreeTypeFontParameter() {

    init {
        setLinear()
    }

    fun setLinear(): TypefaceConfig {
        minFilter = Texture.TextureFilter.Linear
        magFilter = Texture.TextureFilter.Linear
        return this
    }
    fun setSize(size: Int): TypefaceConfig {
        this.size = size
        return this
    }
    // Handler method
    fun setCharacters(characters: CharType): TypefaceConfig {
        this.characters = characters.chars
        return this
    }
    // Processing logic
    fun setCharacters(chars: String): TypefaceConfig {
        this.characters = chars
        return this
    }
    // Core functionality
    fun setBorder(width: Float, color: Color): TypefaceConfig {
        this.borderWidth = width
        this.borderColor = color
        return this
    }
    fun setShadow(offsetX: Int, offsetY: Int, color: Color): TypefaceConfig {
        this.shadowOffsetX = offsetX
        this.shadowOffsetY = offsetY
        this.shadowColor = color
        return this
    }

    enum class CharType(val chars: String) {
        SYMBOLS       ("\"!`?'•.,;:()[]{}<>|/@\\^\$€—%-+=#_&~*’…«»❤°\""                    ),
        NUMBERS       ("1234567890"                                                        ),
        LATIN         ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"              ),
        CYRILLIC      ("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЄЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэєюяІЇії"),

        LATIN_CYRILLIC(LATIN.chars.plus(CYRILLIC.chars)                                         ),
        ALL           (SYMBOLS.chars.plus(NUMBERS.chars).plus(LATIN.chars).plus(CYRILLIC.chars) ),
    }

}