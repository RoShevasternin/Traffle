package com.neonticiton.toetacker.game.manager

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

        AGAIN      (TextureData("textures/all/again.png")),
        B_DEF      (TextureData("textures/all/b_def.png")),
        GREEN      (TextureData("textures/all/green.png")),
        GRID       (TextureData("textures/all/grid.png")),
        MD         (TextureData("textures/all/md.png")),
        MENU_DEF   (TextureData("textures/all/menu_def.png")),
        MENU_PRESS (TextureData("textures/all/menu_press.png")),
        MP         (TextureData("textures/all/mp.png")),
        O          (TextureData("textures/all/o.png")),
        OPPONENT   (TextureData("textures/all/opponent.png")),
        PLAY_DEF   (TextureData("textures/all/play_def.png")),
        PLAY_PRESS (TextureData("textures/all/play_press.png")),
        RULE_DEF   (TextureData("textures/all/rule_def.png")),
        RULE_PRESS (TextureData("textures/all/rule_press.png")),
        RULES      (TextureData("textures/all/rules.png")),
        SD         (TextureData("textures/all/sd.png")),
        SP         (TextureData("textures/all/sp.png")),
        WIN        (TextureData("textures/all/win.png")),
        X          (TextureData("textures/all/x.png")),
        YOU        (TextureData("textures/all/you.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}