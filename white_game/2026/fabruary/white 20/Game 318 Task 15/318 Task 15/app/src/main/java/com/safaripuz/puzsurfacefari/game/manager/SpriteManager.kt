package com.safaripuz.puzsurfacefari.game.manager

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

        B_DEF       (TextureData("textures/all/b_def.png")),
        B_LOSE      (TextureData("textures/all/b_lose.png")),
        B_WIN       (TextureData("textures/all/b_win.png")),
        BACK_DEF    (TextureData("textures/all/back_def.png")),
        BACK_PRESS  (TextureData("textures/all/back_press.png")),
        GAME_PAN    (TextureData("textures/all/game_pan.png")),
        LION        (TextureData("textures/all/lion.png")),
        LOSE_PAN    (TextureData("textures/all/lose_pan.png")),
        MD          (TextureData("textures/all/md.png")),
        MENU_PAN    (TextureData("textures/all/menu_pan.png")),
        MP          (TextureData("textures/all/mp.png")),
        RULES_PAN   (TextureData("textures/all/rules_pan.png")),
        SD          (TextureData("textures/all/sd.png")),
        SP          (TextureData("textures/all/sp.png")),
        WIN_PAN     (TextureData("textures/all/win_pan.png")),
        B_BLUR     (TextureData("textures/all/b_blur.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}