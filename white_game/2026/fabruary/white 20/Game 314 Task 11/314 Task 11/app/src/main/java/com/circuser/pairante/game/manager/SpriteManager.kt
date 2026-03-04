package com.circuser.pairante.game.manager

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
        _7           (TextureData("textures/all/7.png")),
        _8           (TextureData("textures/all/8.png")),

        B_DEF      (TextureData("textures/all/b_def.png")),
        B_GAME     (TextureData("textures/all/b_game.png")),
        B_LOSE     (TextureData("textures/all/b_lose.png")),
        B_SELECTER (TextureData("textures/all/b_selecter.png")),
        B_WIN      (TextureData("textures/all/b_win.png")),
        GALCA      (TextureData("textures/all/galca.png")),
        GAME       (TextureData("textures/all/game.png")),
        LOSE       (TextureData("textures/all/lose.png")),
        MASK       (TextureData("textures/all/mask.png")),
        MENU       (TextureData("textures/all/menu.png")),
        PIP        (TextureData("textures/all/pip.png")),
        PROG       (TextureData("textures/all/prog.png")),
        RESULT     (TextureData("textures/all/result.png")),
        SCHOOSE    (TextureData("textures/all/schoose.png")),
        SETT       (TextureData("textures/all/sett.png")),
        SLON       (TextureData("textures/all/slon.png")),
        VIC        (TextureData("textures/all/vic.png")),
        BD         (TextureData("textures/all/bd.png")),
        BP         (TextureData("textures/all/bp.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}