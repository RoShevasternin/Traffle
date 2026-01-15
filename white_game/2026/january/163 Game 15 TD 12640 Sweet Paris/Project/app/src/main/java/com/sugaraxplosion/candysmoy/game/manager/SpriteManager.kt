package com.sugaraxplosion.candysmoy.game.manager

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

    fun initTeture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }


    enum class EnumAtlas(val data: AtlasData) {
        SPLASH(AtlasData("assets/splash.atlas")),
        ALL   (AtlasData("assets/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        LOAD(TextureData("textures/splash/Load.png")),

        BLUE   (TextureData("textures/Blue.png")),
        BLURES (TextureData("textures/blures.png")),
        GIRL   (TextureData("textures/girl.png")),
        RECORDS(TextureData("textures/records.png")),
        RULES  (TextureData("textures/rules.png")),
        VAFLA  (TextureData("textures/Vafla.png")),
        PITANIE(TextureData("textures/Pitanie.png")),
        agree(TextureData("textures/agree.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}