package com.pyramidconnect.sorting.game.manager

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
        _9           (TextureData("textures/all/9.png")),
        _10          (TextureData("textures/all/10.png")),
        _11          (TextureData("textures/all/11.png")),
        back         (TextureData("textures/all/back.png")),
        btn_def      (TextureData("textures/all/btn_def.png")),
        btn_press    (TextureData("textures/all/btn_press.png")),
        cursor       (TextureData("textures/all/cursor.png")),
        game_grid    (TextureData("textures/all/game_grid.png")),
        it1          (TextureData("textures/all/it1.png")),
        it2          (TextureData("textures/all/it2.png")),
        it3          (TextureData("textures/all/it3.png")),
        it4          (TextureData("textures/all/it4.png")),
        it5          (TextureData("textures/all/it5.png")),
        it6          (TextureData("textures/all/it6.png")),
        it7          (TextureData("textures/all/it7.png")),
        it8          (TextureData("textures/all/it8.png")),
        it9          (TextureData("textures/all/it9.png")),
        it10         (TextureData("textures/all/it10.png")),
        item         (TextureData("textures/all/item.png")),
        lose_pan     (TextureData("textures/all/lose_pan.png")),
        panel        (TextureData("textures/all/panel.png")),
        play_def     (TextureData("textures/all/play_def.png")),
        play_press   (TextureData("textures/all/play_press.png")),
        record       (TextureData("textures/all/record.png")),
        sett         (TextureData("textures/all/sett.png")),
        sett_pan     (TextureData("textures/all/sett_pan.png")),
        vibro_check  (TextureData("textures/all/vibro_check.png")),
        vibro_def    (TextureData("textures/all/vibro_def.png")),
        welcome_pan  (TextureData("textures/all/welcome_pan.png")),
        win_pan      (TextureData("textures/all/win_pan.png")),
        back_game      (TextureData("textures/all/back_game.png")),
        back_game_panel(TextureData("textures/all/back_game_panel.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}