package com.slotscity.official.game.manager

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
        ALL  (AtlasData("atlas/all.atlas")  ),
        ITEMS(AtlasData("atlas/items.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Loader
        logo     (TextureData("textures/loader/logo.png")),
        logo_mask(TextureData("textures/loader/logo_mask.png")),
        // All
        Gates_of_Olympus         (TextureData("textures/all/Gates_of_Olympus.png")),
        gates                    (TextureData("textures/all/gates.png")),
        slot_group               (TextureData("textures/all/slot_group.png")),
        slot_group_mask          (TextureData("textures/all/slot_group_mask.png")),
        svechenie                (TextureData("textures/all/svechenie.png")),
        user_icon_mask           (TextureData("textures/all/user_icon_mask.png")),
        volume_mask              (TextureData("textures/all/volume_mask.png")),
        launcher_svecheni        (TextureData("textures/all/launcher_svecheni.png")),
        launchers                (TextureData("textures/all/launchers.png")),
        // Info
        info_0(TextureData("textures/info/info-0.png")),
        info_1(TextureData("textures/info/info-1.png")),
        info_2(TextureData("textures/info/info-2.png")),
        info_3(TextureData("textures/info/info-3.png")),
        info_4(TextureData("textures/info/info-4.png")),
        info_5(TextureData("textures/info/info-5.png")),
        // Bonus
        bonus_def  (TextureData("textures/bonus/bonus_def.png")),
        bonus_dis  (TextureData("textures/bonus/bonus_dis.png")),
        bonus_glow (TextureData("textures/bonus/bonus_glow.png")),
        sunduk     (TextureData("textures/bonus/sunduk.png")),
        svetka     (TextureData("textures/bonus/svetka.png")),
    }

    enum class CarnavalCatAtlas(val data: AtlasData) {
        ALL  (AtlasData("atlas/Сarnival Сat/all.atlas")  ),
        ITEMS(AtlasData("atlas/Сarnival Сat/items.atlas")),
    }

    enum class CarnavalCatTexture(val data: TextureData) {
        BACKGROUND(TextureData("textures/Carnaval Cat/background.png")),
        STATIC_FIELD(TextureData("textures/Carnaval Cat/static_field.png")),
    }

    enum class TreasureSnipesAtlas(val data: AtlasData) {
        ALL  (AtlasData("atlas/Treasure Snipes/all.atlas")  ),
        ITEMS(AtlasData("atlas/Treasure Snipes/items.atlas")),
    }

    enum class TreasureSnipesTexture(val data: TextureData) {
        BACKGROUND(TextureData("textures/Treasure Snipes/background.png")),
        SLOT_MASK (TextureData("textures/Treasure Snipes/slot_mask.png")),
        PANEL     (TextureData("textures/Treasure Snipes/panel.png")),
    }

    enum class SweetBonanzaAtlas(val data: AtlasData) {
        ALL  (AtlasData("atlas/Sweet Bonanza/all.atlas")  ),
        ITEMS(AtlasData("atlas/Sweet Bonanza/items.atlas")),
    }

    enum class SweetBonanzaTexture(val data: TextureData) {
        BACKGROUND(TextureData("textures/Sweet Bonanza/background.png")),
        SLOT_GROUP(TextureData("textures/Sweet Bonanza/slot_group.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}