package com.shoote.maniapink.game.manager

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


    enum class EnumAtlas(val data: AtlasData) {}

    enum class EnumTexture(val data: TextureData) {
        Loader_Background(TextureData("texture/loader/Background.png")),

        All_Background_1(TextureData("texture/all/Background_1.png")),
        All_Background_2(TextureData("texture/all/Background_2.png")),
        All_Orange      (TextureData("texture/all/Orange.png")),

        All_1           (TextureData("texture/all/1.png")),
        All_2           (TextureData("texture/all/2.png")),
        All_3           (TextureData("texture/all/3.png")),
        All_4           (TextureData("texture/all/4.png")),
        All_5           (TextureData("texture/all/5.png")),

        All_bomb        (TextureData("texture/all/bomb.png")),
        All_complete    (TextureData("texture/all/complete.png")),
        All_gun         (TextureData("texture/all/gun.png")),
        All_lose        (TextureData("texture/all/lose.png")),
        All_mask        (TextureData("texture/all/mask.png")),
        All_menu        (TextureData("texture/all/menu.png")),
        All_mode        (TextureData("texture/all/mode.png")),
        All_panel       (TextureData("texture/all/panel.png")),
        All_pause       (TextureData("texture/all/pause.png")),
        All_pause_star  (TextureData("texture/all/pause_star.png")),
        All_prog        (TextureData("texture/all/prog.png")),
        All_prog_back   (TextureData("texture/all/prog_back.png")),
        All_rainbow     (TextureData("texture/all/rainbow.png")),
        All_shop        (TextureData("texture/all/shop.png")),
        All_shop_star   (TextureData("texture/all/shop_star.png")),
        All_star        (TextureData("texture/all/star.png")),
        All_stars       (TextureData("texture/all/stars.png")),

        All_check    (TextureData("texture/all/check.png")),
        All_settings (TextureData("texture/all/settings.png")),

        All_shariks (TextureData("texture/all/shariks.png")),

    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}