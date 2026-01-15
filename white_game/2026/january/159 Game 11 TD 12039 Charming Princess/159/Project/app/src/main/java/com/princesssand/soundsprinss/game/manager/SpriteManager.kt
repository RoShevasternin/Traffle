package com.princesssand.soundsprinss.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTexturesList   = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    // Texture
    fun loadTexture() {
        loadableTexturesList.onEach { assetManager.load(it.path, Texture::class.java) }
    }

    fun initTexture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        all(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        loader(TextureData("textures/loader/loader.png")),

        panel   (TextureData("textures/all/panel.png")),
        princess(TextureData("textures/all/princess.png")),
        progress(TextureData("textures/all/progress.png")),
        rules   (TextureData("textures/all/rules.png")),
        blures  (TextureData("textures/all/blures.png")),
        borders (TextureData("textures/all/borders.png")),
        game    (TextureData("textures/all/game.png")),
        b_music (TextureData("textures/all/b_music.png")),
        b_sound (TextureData("textures/all/b_sound.png")),
        b_back  (TextureData("textures/all/b_back.png")),
        bb_def  (TextureData("textures/all/bb_def.png")),
        bb_press  (TextureData("textures/all/bb_press.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}