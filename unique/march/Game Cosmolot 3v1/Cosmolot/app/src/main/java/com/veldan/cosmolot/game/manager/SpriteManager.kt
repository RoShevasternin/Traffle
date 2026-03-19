package com.veldan.cosmolot.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.veldan.cosmolot.game.util.region

object SpriteManager {

    var loadableAtlasList   = mutableListOf<IAtlas>()
    var loadableTextureList = mutableListOf<ITexture>()



    fun loadAtlas(assetManager: AssetManager) {
        loadableAtlasList.onEach { assetManager.load(it.data.path, TextureAtlas::class.java) }
    }

    fun loadTexture(assetManager: AssetManager) {
        loadableTextureList.onEach {
            assetManager.load(it.data.path, Texture::class.java, TextureLoader.TextureParameter().apply {
                minFilter = Texture.TextureFilter.Linear
                magFilter = Texture.TextureFilter.Linear
                genMipMaps = true
            })
        }
    }

    fun initAtlas(assetManager: AssetManager) {
        loadableAtlasList.onEach { it.data.atlas = assetManager[it.data.path, TextureAtlas::class.java] }
    }

    fun initTexture(assetManager: AssetManager) {
        loadableTextureList.onEach { it.data.texture = assetManager[it.data.path, Texture::class.java] }
    }



    enum class EnumAtlas(override val data: TextureAtlasData): IAtlas {
        MENU( TextureAtlasData("atlas/menu.atlas")  ),
        GAME1(TextureAtlasData("atlas/game_1.atlas")),
        GAME2(TextureAtlasData("atlas/game_2.atlas")),
        GAME3(TextureAtlasData("atlas/game_3.atlas")),
    }

    enum class EnumTexture(override val data: TextureData): ITexture {
        BACKGROUND(       TextureData("textures/background.png")       ),
        BACKGROUND_1(     TextureData("textures/background-1.png")     ),
        BACKGROUND_2(     TextureData("textures/background-2.png")     ),
        BACKGROUND_3(     TextureData("textures/background-3.png")     ),
        BACKGROUND_BACK_2(TextureData("textures/background_back-2.png")),
    }

    enum class SplashRegion(override val region: TextureRegion): IRegion {
        BACKGROUND(EnumTexture.BACKGROUND.data.texture.region)
    }

    enum class MenuRegion(override val region: TextureRegion): IRegion {
        COINS(   EnumAtlas.MENU.data.atlas.findRegion("coins")  ),
        ICON_1(  EnumAtlas.MENU.data.atlas.findRegion("icon-1") ),
        ICON_2(  EnumAtlas.MENU.data.atlas.findRegion("icon-2") ),
        ICON_3(  EnumAtlas.MENU.data.atlas.findRegion("icon-3") ),
        PHOTO(   EnumAtlas.MENU.data.atlas.findRegion("photo")  ),
        MASK(    EnumAtlas.MENU.data.atlas.findRegion("mask")   ),
    }

    enum class GameRegion_1(override val region: TextureRegion): IRegion {
        BACKGROUND(EnumTexture.BACKGROUND_1.data.texture.region),
        GLOW(          EnumAtlas.GAME1.data.atlas.findRegion("glow")          ),
        MASK(          EnumAtlas.GAME1.data.atlas.findRegion("mask")          ),
        MAX_BET_DEF(   EnumAtlas.GAME1.data.atlas.findRegion("max_bet_def")   ),
        MAX_BET_PRESS( EnumAtlas.GAME1.data.atlas.findRegion("max_bet_press") ),
        MENU_DEF(      EnumAtlas.GAME1.data.atlas.findRegion("menu_def")      ),
        MENU_PRESS(    EnumAtlas.GAME1.data.atlas.findRegion("menu_press")    ),
        MINUS(         EnumAtlas.GAME1.data.atlas.findRegion("minus")         ),
        MUSIC_NO(      EnumAtlas.GAME1.data.atlas.findRegion("music_no")      ),
        MUSIC_YES(     EnumAtlas.GAME1.data.atlas.findRegion("music_yes")     ),
        PLUS(          EnumAtlas.GAME1.data.atlas.findRegion("plus")          ),
        SPIN_DEF(      EnumAtlas.GAME1.data.atlas.findRegion("spin_def")      ),
        SPIN_PRESS(    EnumAtlas.GAME1.data.atlas.findRegion("spin_press")    ),
        WILD(          EnumAtlas.GAME1.data.atlas.findRegion("wild")          ),
    }

    enum class GameRegion_2(override val region: TextureRegion): IRegion {
        BACKGROUND(     EnumTexture.BACKGROUND_2.data.texture.region),
        BACKGROUND_BACK(EnumTexture.BACKGROUND_BACK_2.data.texture.region),
        AUTO_DEF(   EnumAtlas.GAME2.data.atlas.findRegion("auto_def")   ),
        AUTO_PRESS( EnumAtlas.GAME2.data.atlas.findRegion("auto_press") ),
        AUTO_DIS(   EnumAtlas.GAME2.data.atlas.findRegion("auto_dis")   ),
        BET_CHECK(  EnumAtlas.GAME2.data.atlas.findRegion("bet_check")  ),
        BET_DEF(    EnumAtlas.GAME2.data.atlas.findRegion("bet_def")    ),
        GLOW(       EnumAtlas.GAME2.data.atlas.findRegion("glow")       ),
        MENU_DEF(   EnumAtlas.GAME2.data.atlas.findRegion("menu_def")   ),
        MENU_PRESS( EnumAtlas.GAME2.data.atlas.findRegion("menu_press") ),
        MUSIC_NO(   EnumAtlas.GAME2.data.atlas.findRegion("music_no")   ),
        MUSIC_YES(  EnumAtlas.GAME2.data.atlas.findRegion("music_yes")  ),
        SPIN_DEF(   EnumAtlas.GAME2.data.atlas.findRegion("spin_def")   ),
        SPIN_PRESS( EnumAtlas.GAME2.data.atlas.findRegion("spin_press") ),
        WILD(       EnumAtlas.GAME2.data.atlas.findRegion("wild")       ),
    }

    enum class GameRegion_3(override val region: TextureRegion): IRegion {
        BACKGROUND(EnumTexture.BACKGROUND_3.data.texture.region),
        AUTO_DIS(   EnumAtlas.GAME3.data.atlas.findRegion("auto_dis")   ),
        AUTO_PRESS( EnumAtlas.GAME3.data.atlas.findRegion("auto_press") ),
        GLOW(       EnumAtlas.GAME3.data.atlas.findRegion("glow")       ),
        MASK(       EnumAtlas.GAME3.data.atlas.findRegion("mask")       ),
        MENU_DEF(   EnumAtlas.GAME3.data.atlas.findRegion("menu_def")   ),
        MENU_PRESS( EnumAtlas.GAME3.data.atlas.findRegion("menu_press") ),
        MUSIC_NO(   EnumAtlas.GAME3.data.atlas.findRegion("music_no")   ),
        MUSIC_YES(  EnumAtlas.GAME3.data.atlas.findRegion("music_yes")  ),
        SPIN_PRESS( EnumAtlas.GAME3.data.atlas.findRegion("spin_press") ),
        WILD(       EnumAtlas.GAME3.data.atlas.findRegion("wild")       ),
    }



    enum class ListRegion(override val regionList: List<TextureRegion>): IRegionList {
        GAME_1_ITEMS(List(9) { EnumAtlas.GAME1.data.atlas.findRegion("${it.inc()}") }),
        GAME_2_ITEMS(List(8) { EnumAtlas.GAME2.data.atlas.findRegion("${it.inc()}") }),
        GAME_3_ITEMS(List(8) { EnumAtlas.GAME3.data.atlas.findRegion("${it.inc()}") }),
    }



    interface IAtlas {
        val data: TextureAtlasData
    }

    interface ITexture {
        val data: TextureData
    }

    interface IRegion {
        val region: TextureRegion
    }

    interface IRegionList {
        val regionList: List<TextureRegion>
    }

    data class TextureAtlasData(
        val path: String,
    ) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(
        val path: String,
    ) {
        lateinit var texture: Texture
    }

}