package com.bunnypanny.eggcatch.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTextureList = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun loadTexture() {
        loadableTextureList.onEach {
            assetManager.load(it.path, Texture::class.java, TextureLoader.TextureParameter().apply {
                minFilter = Texture.TextureFilter.Linear
                magFilter = Texture.TextureFilter.Linear
                genMipMaps = true
            })
        }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    fun initTexture() {
        loadableTextureList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTextureList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        //GAME(AtlasData("atlas/game.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        LOADER(TextureData("textures/loader/loader.png")),

        _1(TextureData("textures/all/1.png")),
        _2(TextureData("textures/all/2.png")),
        _3(TextureData("textures/all/3.png")),
        _4(TextureData("textures/all/4.png")),
        _5(TextureData("textures/all/5.png")),
        _6(TextureData("textures/all/6.png")),

        AGAIN       (TextureData("textures/all/again.png")),
        BACK_DEF    (TextureData("textures/all/back_def.png")),
        BACK_PRESS  (TextureData("textures/all/back_press.png")),
        BACKGROUND  (TextureData("textures/all/background.png")),
        BAG         (TextureData("textures/all/bag.png")),
        BUNNY       (TextureData("textures/all/bunny.png")),
        DONE        (TextureData("textures/all/done.png")),
        LOSE        (TextureData("textures/all/lose.png")),
        MASK        (TextureData("textures/all/mask.png")),
        MENU_PAN    (TextureData("textures/all/menu_pan.png")),
        PANEL       (TextureData("textures/all/panel.png")),
        PIP         (TextureData("textures/all/pip.png")),
        PROGRESS    (TextureData("textures/all/progress.png")),
        RULES_PAN   (TextureData("textures/all/rules_pan.png")),
        SETTINGS_PAN(TextureData("textures/all/settings_pan.png")),
        WIN         (TextureData("textures/all/win.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}