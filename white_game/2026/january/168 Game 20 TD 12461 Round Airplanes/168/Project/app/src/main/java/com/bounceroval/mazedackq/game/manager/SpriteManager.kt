package com.bounceroval.mazedackq.game.manager

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
        ALL(AtlasData("assets/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        bg1(TextureData("textures/backgrounds/bg1.png")),
        bg2(TextureData("textures/backgrounds/bg2.png")),
        bg3(TextureData("textures/backgrounds/bg3.png")),
        loading(TextureData("textures/backgrounds/loading.png")),

        big_sett  (TextureData("textures/big_sett.png")),
        mask      (TextureData("textures/mask.png")),
        text_rules(TextureData("textures/text_rules.png")),
        rules_pan (TextureData("textures/rules_pan.png")),
        t_rules   (TextureData("textures/t_rules.png")),
        t_settings(TextureData("textures/t_settings.png")),

        info1(TextureData("textures/info1.png")),
        info2(TextureData("textures/info2.png")),
        info3(TextureData("textures/info3.png")),

        DAgr  (TextureData("textures/dialog/DAgr.png")),
        DDis  (TextureData("textures/dialog/DDis.png")),
        DPanel(TextureData("textures/dialog/DPanel.png")),
        DText (TextureData("textures/dialog/DText.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}