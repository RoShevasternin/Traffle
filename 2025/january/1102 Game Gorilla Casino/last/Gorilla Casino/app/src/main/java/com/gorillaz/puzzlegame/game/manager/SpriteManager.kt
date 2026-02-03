package com.gorillaz.puzzlegame.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList    = mutableListOf<AtlasData>()
    var loadableTexturesList = mutableListOf<TextureData>()

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
        LOADER(AtlasData("atlas/loader.atlas")),

        ALL    (AtlasData("atlas/all.atlas")),
        AVATAR (AtlasData("atlas/avatar.atlas")),
        ITEM_1 (AtlasData("atlas/item_1.atlas")),
        ITEM_2 (AtlasData("atlas/item_2.atlas")),
        ITEM_3 (AtlasData("atlas/item_3.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        L_BACKGROUND_0(TextureData("textures/loader/background_0.png")),

        BACKGROUND_1(TextureData("textures/all/background/background_1.png")),
        BACKGROUND_2(TextureData("textures/all/background/background_2.png")),
        BACKGROUND_3(TextureData("textures/all/background/background_3.png")),
        BACKGROUND_4(TextureData("textures/all/background/background_4.png")),
        BACKGROUND_5(TextureData("textures/all/background/background_5.png")),
        BACKGROUND_6(TextureData("textures/all/background/background_6.png")),
        BACKGROUND_7(TextureData("textures/all/background/background_7.png")),
        BACKGROUND_8(TextureData("textures/all/background/background_8.png")),

        MASK_LVL_PROGRESS        (TextureData("textures/all/mask_lvl_progress.png")),
        SHEEN                    (TextureData("textures/all/sheen.png")),
        ROULETTE_CURSOR          (TextureData("textures/all/roulette_cursor.png")),
        ROULETTE                 (TextureData("textures/all/roulette.png")),
        LOCATION_IS_BLOCKED      (TextureData("textures/all/location_is_blocked.png")),

        PANEL_MAIN               (TextureData("textures/all/panel/panel_main.png")),
        PANEL_ROULETTE           (TextureData("textures/all/panel/panel_roulette.png")),
        PANEL_SEVENS             (TextureData("textures/all/panel/panel_sevens.png")),
        PANEL_MENU               (TextureData("textures/all/panel/panel_menu.png")),
        PANEL_ROULETTE_WATCH_ADD (TextureData("textures/all/panel/panel_roulette_watch_add.png")),
        PANEL_ROULETTE_SPIN_PRICE(TextureData("textures/all/panel/panel_roulette_spin_price.png")),
        PANEL_SELECT_AVATAR      (TextureData("textures/all/panel/panel_select_avatar.png")),
        PANEL_NICKNAME           (TextureData("textures/all/panel/panel_nickname.png")),
        PANEL_AVATAR             (TextureData("textures/all/panel/panel_avatar.png")),
        PANEL_ACHIEVEMENT        (TextureData("textures/all/panel/panel_achievement.png")),
        PANEL_ONE_PUZZLE         (TextureData("textures/all/panel/panel_one_puzzle.png")),
        PANEL_GALLERY            (TextureData("textures/all/panel/panel_gallery.png")),
        PANEL_SETTINGS_BOTTOM    (TextureData("textures/all/panel/panel_settings_bottom.png")),
        PANEL_SETTINGS           (TextureData("textures/all/panel/panel_settings.png")),
        PANEL_SELECT_LOCATION    (TextureData("textures/all/panel/panel_select_location.png")),
        PANEL_MAX_JACKPOT        (TextureData("textures/all/panel/panel_max_jackpot.png")),
        PANEL_LOCATION           (TextureData("textures/all/panel/panel_location.png")),
        PANEL_INCREASE_JACKPOT   (TextureData("textures/all/panel/panel_increase_jackpot.png")),
        PANEL_MAX_BET            (TextureData("textures/all/panel/panel_max_bet.png")),
        PANEL_SLOTS              (TextureData("textures/all/panel/panel_slots.png")),
        PANEL_BET                (TextureData("textures/all/panel/panel_bet.png")),

        INAPP_1K_GEMS            (TextureData("textures/all/inapp/inapp_1k_gems.png")),
        INAPP_10K_GOLD           (TextureData("textures/all/inapp/inapp_10k_gold.png")),
        INAPP_100_GEMS           (TextureData("textures/all/inapp/inapp_100_gems.png")),
        STORE                    (TextureData("textures/all/inapp/store.png")),
        PANEL_SHOP_SELECTOR      (TextureData("textures/all/inapp/panel_shop_selector.png")),

        PUZZLE_1  (TextureData("textures/all/puzzle/puzzle_1.png")),
        PUZZLE_2  (TextureData("textures/all/puzzle/puzzle_2.png")),
        PUZZLE_3  (TextureData("textures/all/puzzle/puzzle_3.png")),
        PUZZLE_4  (TextureData("textures/all/puzzle/puzzle_4.png")),
        PUZZLE_5  (TextureData("textures/all/puzzle/puzzle_5.png")),
        PUZZLE_6  (TextureData("textures/all/puzzle/puzzle_6.png")),
        PUZZLE_7  (TextureData("textures/all/puzzle/puzzle_7.png")),
        PUZZLE_8  (TextureData("textures/all/puzzle/puzzle_8.png")),
        PUZZLE_9  (TextureData("textures/all/puzzle/puzzle_9.png")),
        PUZZLE_10 (TextureData("textures/all/puzzle/puzzle_10.png")),

        PERSONAGE_1(TextureData("textures/all/personage/personage 1.png")),
        PERSONAGE_2(TextureData("textures/all/personage/personage 2.png")),
        PERSONAGE_3(TextureData("textures/all/personage/personage 3.png")),
        PERSONAGE_4(TextureData("textures/all/personage/personage 4.png")),
        PERSONAGE_5(TextureData("textures/all/personage/personage 5.png")),
        PERSONAGE_6(TextureData("textures/all/personage/personage 6.png")),
        PERSONAGE_7(TextureData("textures/all/personage/personage 7.png")),

        SHAPE_1(TextureData("textures/all/win_shape/shape_1.png")),
        SHAPE_2(TextureData("textures/all/win_shape/shape_2.png")),
        SHAPE_3(TextureData("textures/all/win_shape/shape_3.png")),
        SHAPE_4(TextureData("textures/all/win_shape/shape_4.png")),
        SHAPE_5(TextureData("textures/all/win_shape/shape_5.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}