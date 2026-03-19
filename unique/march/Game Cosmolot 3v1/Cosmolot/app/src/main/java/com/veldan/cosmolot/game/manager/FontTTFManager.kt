package com.veldan.cosmolot.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader

object FontTTFManager {

    private const val pathBOWLER = "TTF/BOWLER.ttf"

    private val resolverInternal = InternalFileHandleResolver()

    var loadableListFont = mutableListOf<FontTTFData>()

//    val fontText: IFont get() = when(Language.locale.language) {
//        "ru", "uk" -> NotoSansFont
//        else -> MerriweatherSansFont
//    }



    private fun AssetManager.setLoaderTTF() {
        setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolverInternal))
        setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolverInternal))
    }

    private fun getLoaderParameter(
        path: String,
        parameters: FreeTypeFontGenerator.FreeTypeFontParameter.() -> Unit = { }
    ) = FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
        fontFileName = path
        fontParameters.apply {
            minFilter = TextureFilter.Linear
            magFilter = TextureFilter.Linear
            incremental = true
            parameters()
        }
    }



    fun load(assetManager: AssetManager) {
        with(assetManager) {
            setLoaderTTF()
            loadableListFont.onEach { load(it.name  + ".ttf", BitmapFont::class.java, it.parameters) }
        }
    }

    fun init(assetManager: AssetManager) {
        loadableListFont.onEach { it.font = assetManager[it.name + ".ttf", BitmapFont::class.java] }
    }



    object BOWLERFont: IFont {
        override val font_15 = FontTTFData("BOWLER_15", getLoaderParameter(pathBOWLER) { size = 15 })
        override val font_20 = FontTTFData("BOWLER_20", getLoaderParameter(pathBOWLER) { size = 20 })
        override val font_25 = FontTTFData("BOWLER_25", getLoaderParameter(pathBOWLER) { size = 25 })
        override val font_30 = FontTTFData("BOWLER_30", getLoaderParameter(pathBOWLER) { size = 30 })
        override val font_40 = FontTTFData("BOWLER_40", getLoaderParameter(pathBOWLER) { size = 40 })
    }



    interface IFont {
        val font_15: FontTTFData
        val font_20: FontTTFData
        val font_25: FontTTFData
        val font_30: FontTTFData
        val font_40: FontTTFData

        val values get() = listOf<FontTTFData>(font_15, font_20, font_25, font_30, font_40)
    }

    data class FontTTFData(
        val name: String,
        val parameters: FreetypeFontLoader.FreeTypeFontLoaderParameter,
    ) {
        lateinit var font: BitmapFont
    }
}