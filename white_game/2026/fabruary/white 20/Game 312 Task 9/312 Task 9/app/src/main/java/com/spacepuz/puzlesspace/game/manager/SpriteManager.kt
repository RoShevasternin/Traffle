package com.spacepuz.puzlesspace.game.manager

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
        //LOADER   (AtlasData("atlas/loader.atlas")   ),
        //ALL      (AtlasData("atlas/all.atlas")      ),
        //ITEMS    (AtlasData("atlas/items.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        LOADER(TextureData("textures/loader/loader.png")),

        _1           (TextureData("textures/all/1.png")),
        _2           (TextureData("textures/all/2.png")),
        _3           (TextureData("textures/all/3.png")),
        _4           (TextureData("textures/all/4.png")),
        _5           (TextureData("textures/all/5.png")),
        _6           (TextureData("textures/all/6.png")),
        BACK_DEF     (TextureData("textures/all/back_def.png")),
        BACK_PRESS   (TextureData("textures/all/back_press.png")),
        DEF          (TextureData("textures/all/def.png")),
        GAME_PANEL   (TextureData("textures/all/game_panel.png")),
        LOSE         (TextureData("textures/all/lose.png")),
        MD           (TextureData("textures/all/md.png")),
        MENU         (TextureData("textures/all/menu.png")),
        MP           (TextureData("textures/all/mp.png")),
        RESTART_HOME (TextureData("textures/all/restart_home.png")),
        RULES        (TextureData("textures/all/rules.png")),
        SD           (TextureData("textures/all/sd.png")),
        SP           (TextureData("textures/all/sp.png")),
        WIN          (TextureData("textures/all/win.png")),
        PANEL_RULES          (TextureData("textures/all/panel_rules.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}