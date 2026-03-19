package com.quenloria615.beton.game.manager

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


    enum class EnumAtlas(val data: AtlasData) {}

    enum class EnumTexture(val data: TextureData) {
        LOADER(TextureData("textures/loader/loader.png")),

        BACKGROUND_DEF     (TextureData("textures/all/background_def.png")),
        BACKGROUND_SHOP    (TextureData("textures/all/background_shop.png")),
        BACKGROUND_SETTINGS(TextureData("textures/all/background_settings.png")),

        COIN_LEFT          (TextureData("textures/all/coin_left.png")),
        COIN_RIGHT         (TextureData("textures/all/coin_right.png")),
        DAILY_BONUS        (TextureData("textures/all/daily_bonus.png")),
        DAILY_BONUS_600    (TextureData("textures/all/daily_bonus_600.png")),
        LOBBY_DEF          (TextureData("textures/all/lobby_def.png")),
        LOBBY_PRESS        (TextureData("textures/all/lobby_press.png")),
        MENU_1             (TextureData("textures/all/menu_1.png")),
        MENU_2             (TextureData("textures/all/menu_2.png")),
        MENU_3             (TextureData("textures/all/menu_3.png")),
        NOTIFICATION_CHECK (TextureData("textures/all/notification_check.png")),
        NOTIFICATION_DEF   (TextureData("textures/all/notification_def.png")),
        OFF                (TextureData("textures/all/off.png")),
        ON                 (TextureData("textures/all/on.png")),
        PANEL_COIN         (TextureData("textures/all/panel_coin.png")),
        PANEL_SETTINGS     (TextureData("textures/all/panel_settings.png")),
        PLAY_DEF           (TextureData("textures/all/play_def.png")),
        PLAY_PRESS         (TextureData("textures/all/play_press.png")),
        PROGRESS           (TextureData("textures/all/progress.png")),
        PROGRESS_CIRCLE    (TextureData("textures/all/progress_circle.png")),
        PROGRESS_MASK      (TextureData("textures/all/progress_mask.png")),
        SETT_DEF           (TextureData("textures/all/sett_def.png")),
        SETT_PRESS         (TextureData("textures/all/sett_press.png")),
        SETTINGS_GROUP     (TextureData("textures/all/settings_group.png")),
        SHOP_1K            (TextureData("textures/all/shop_1k.png")),
        SHOP_5K            (TextureData("textures/all/shop_5k.png")),
        SHOP_1M            (TextureData("textures/all/shop_1m.png")),
        SHOP_DEF           (TextureData("textures/all/shop_def.png")),
        SHOP_PRESS         (TextureData("textures/all/shop_press.png")),
        TRY_YOUR_LUCK      (TextureData("textures/all/try_your_luck.png")),
        GRAY_COIN_LEFT     (TextureData("textures/all/gray_coin_left.png")),
        GRAY_COIN_RIGHT    (TextureData("textures/all/gray_coin_right.png")),
        PANEL_ROUETTE      (TextureData("textures/all/panel_rouette.png")),
        PANEL_ROULETTE_WIN (TextureData("textures/all/panel_roulette_win.png")),
        PANEL_TRY_AGAIN    (TextureData("textures/all/panel_try_again.png")),
        ROULETTE           (TextureData("textures/all/roulette.png")),
        MASK               (TextureData("textures/all/mask.png")),
        BACKGROUND_ADVENTURES (TextureData("textures/all/background_adventures.png")),
        BACKGROUND_CHAMPIONS  (TextureData("textures/all/background_champions.png")),
        PANEL_SLOT_GROUP      (TextureData("textures/all/panel_slot_group.png")),
        PANEL_STAKE           (TextureData("textures/all/panel_stake.png")),
        SPIN_DEF              (TextureData("textures/all/spin_def.png")),
        SPIN_PRESS            (TextureData("textures/all/spin_press.png")),
        MASK_SLOT_GROUP       (TextureData("textures/all/mask_slot_group.png")),
        COINS                 (TextureData("textures/all/coins.png")),
        GAIN_DEF              (TextureData("textures/all/gain_def.png")),
        GAIN_PRESS            (TextureData("textures/all/gain_press.png")),
        SHIELD                (TextureData("textures/all/shield.png")),
        BIG_WIN               (TextureData("textures/all/big_win.png")),

        _1 (TextureData("textures/all/1.png")),
        _2 (TextureData("textures/all/2.png")),
        _3 (TextureData("textures/all/3.png")),
        _4 (TextureData("textures/all/4.png")),
        _5 (TextureData("textures/all/5.png")),
        _6 (TextureData("textures/all/6.png")),
        _7 (TextureData("textures/all/7.png")),
        _8 (TextureData("textures/all/8.png")),
        _9 (TextureData("textures/all/9.png")),
        _10(TextureData("textures/all/10.png")),
        _11(TextureData("textures/all/11.png")),
        _12(TextureData("textures/all/12.png")),

        ADV_1 (TextureData("textures/all/adv_1.png")),
        ADV_2 (TextureData("textures/all/adv_2.png")),
        ADV_3 (TextureData("textures/all/adv_3.png")),
        ADV_4 (TextureData("textures/all/adv_4.png")),
        ADV_5 (TextureData("textures/all/adv_5.png")),
        ADV_6 (TextureData("textures/all/adv_6.png")),
        ADV_7 (TextureData("textures/all/adv_7.png")),
        ADV_8 (TextureData("textures/all/adv_8.png")),
        ADV_9 (TextureData("textures/all/adv_9.png")),
        ADV_10(TextureData("textures/all/adv_10.png")),
        ADV_11(TextureData("textures/all/adv_11.png")),
        ADV_12(TextureData("textures/all/adv_12.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}