package com.keltrivaapp.casibom.game.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.keltrivaapp.casibom.appContext
import com.keltrivaapp.casibom.game.actors.label.ALabelStyle
import com.keltrivaapp.casibom.game.actors.label.spinning.SpinningLabel
import com.keltrivaapp.casibom.game.actors.masks.normal.Mask
import com.keltrivaapp.casibom.game.game
import com.keltrivaapp.casibom.game.manager.GameDataStoreManager
import com.keltrivaapp.casibom.game.manager.NavigationManager
import com.keltrivaapp.casibom.game.manager.SpriteManager
import com.keltrivaapp.casibom.game.util.Balance
import com.keltrivaapp.casibom.game.util.FUN
import com.keltrivaapp.casibom.game.util.advanced.AdvancedScreen
import com.keltrivaapp.casibom.game.util.advanced.AdvancedStage
import com.keltrivaapp.casibom.game.util.bitmap
import com.keltrivaapp.casibom.game.util.listeners.toClickable
import com.keltrivaapp.casibom.game.util.runGDX
import com.keltrivaapp.casibom.game.util.setBounds
import com.keltrivaapp.casibom.game.util.texture
import com.keltrivaapp.casibom.game.util.transformToBalanceFormat
import com.keltrivaapp.casibom.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import com.keltrivaapp.casibom.game.util.Layout.Menu as LM

class MenuScreen: AdvancedScreen(1280f, 727f) {

    companion object {
        const val PHOTO_NAME = "photo"
    }

    private val fileExtensionFlow = MutableStateFlow<String?>(null)

    private val photoMask     = Mask(SpriteManager.MenuRegion.MASK.region)
    private val photoImage    = Image(SpriteManager.MenuRegion.PHOTO.region)
    private val nicknameLabel = SpinningLabel("NICK_NAME", ALabelStyle.bowler_25_white, alignment = Align.right)
    private val balanceImage  = Image(SpriteManager.MenuRegion.COINS.region)
    private val balanceLabel  = SpinningLabel("$1 000 000", ALabelStyle.bowler_30_white, alignment = Align.left)
    private val icon1         = Image(SpriteManager.MenuRegion.ICON_1.region)
    private val icon2         = Image(SpriteManager.MenuRegion.ICON_2.region)
    private val icon3         = Image(SpriteManager.MenuRegion.ICON_3.region)
    private val logoImage     = Image(SpriteManager.MenuRegion.LOGO.region)



    override fun show() {
        super.show()
        setBackBackground(SpriteManager.SplashRegion.LOADER_BACKGROUND.region)
        Balance.init(10_000L)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine.launch {
            runGDX {
                addActor(logoImage)
                logoImage.setBounds(73f, 471f, 782f, 256f)
            }
            addPhoto()
            addCoin()
            addNickName()

            runGDX { addIcons() }
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private suspend fun AdvancedStage.addPhoto() {
        runGDX { addActor(photoMask) }
        photoMask.apply {
            runGDX {
                setBounds(LM.photo)
                addAndFillActor(photoImage)
            }

            var bitmap: Bitmap? = null
            GameDataStoreManager.FileExtension.get()?.let { fileExtension ->
                bitmap = loadPhotoFromInternal(appContext, PHOTO_NAME, fileExtension)
                bitmap?.let { runGDX { photoImage.drawable = TextureRegionDrawable(it.texture()) } }
            }

            runGDX {
                photoImage.toClickable().setOnClickListener {
                    log("Вибір фото")
                    // Дозволи для галереї на нових Android НЕ ПОТРІБНІ
                    game.activity.selectImageFromGallery { uri ->
                        uri?.let { selectedUri ->
                            coroutine.launch(Dispatchers.IO) {
                                val type = game.activity.contentResolver.getType(selectedUri) ?: "image/png"
                                fileExtensionFlow.value = type

                                val bitmap = selectedUri.bitmap() // Твій розширювач

                                savePhotoToInternal(appContext, bitmap, PHOTO_NAME, type)

                                runGDX {
                                    photoImage.drawable = TextureRegionDrawable(bitmap.texture())
                                }
                            }
                        }
                    }
                }
            }

            collectFileExtensions()
        }
    }

    private suspend fun AdvancedStage.addNickName() {
        GameDataStoreManager.NickName.get()?.let { runGDX { nicknameLabel.setText(it) } }

        runGDX {
            addActor(nicknameLabel)
            nicknameLabel.apply {
                setBounds(LM.nick)
                toClickable().setOnClickListener {
                    Gdx.input.getTextInput(object : Input.TextInputListener {
                        override fun input(text: String?) {
                            nicknameLabel.setText(text ?: "NICK_NAME")
                            coroutine.launch(Dispatchers.IO) {
                                GameDataStoreManager.NickName.update { text ?: "NICK_NAME" }
                            }
                        }

                        override fun canceled() {
                            coroutine.launch(Dispatchers.IO) {
                                GameDataStoreManager.NickName.get()?.let { runGDX { nicknameLabel.setText(it) } }
                            }
                        }

                    }, "Enter your nickname", "", "Nick_Name")
                }
            }
        }
    }

    private fun AdvancedStage.addCoin() {
        balanceLabel.setText("$" + Balance.balanceFlow.value.transformToBalanceFormat())

        runGDX {
            addActors(balanceImage, balanceLabel)
            balanceImage.setBounds(LM.coinImage)
            balanceLabel.setBounds(LM.coinLabel)
        }
    }

    private fun AdvancedStage.addIcons() {
        addActors(icon1, icon2, icon3)
        icon1.apply {
            setBounds(LM.icon1)
            toClickable().setOnClickListener { NavigationManager.navigate(GameScreen1(), MenuScreen()) }
        }
        icon2.apply {
            setBounds(LM.icon2)
            toClickable().setOnClickListener { NavigationManager.navigate(GameScreen2(), MenuScreen()) }
        }
        icon3.apply {
            setBounds(LM.icon3)
            toClickable().setOnClickListener { NavigationManager.navigate(GameScreen3(), MenuScreen()) }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------
    private fun collectFileExtensions() {
        coroutine.launch(Dispatchers.IO) {
            fileExtensionFlow.collect { it?.let { fileExtension -> GameDataStoreManager.FileExtension.update { fileExtension } } }
        }
    }
}

fun savePhotoToInternal(context: Context, bitmap: Bitmap, fileName: String, extension: String) {
    // Очищаємо розширення: якщо прийшло "image/jpeg", робимо "jpeg"
    val cleanExtension = extension.replace("image/", "")

    val directory = File(context.filesDir, "photos")
    if (!directory.exists()) directory.mkdirs()

    val file = File(directory, "$fileName.$cleanExtension")

    FileOutputStream(file).use { out ->
        // Перевіряємо формат для стиснення
        val format = if (cleanExtension.contains("png")) Bitmap.CompressFormat.PNG else Bitmap.CompressFormat.JPEG
        bitmap.compress(format, 100, out)
    }
}

fun loadPhotoFromInternal(context: Context, fileName: String, extension: String): Bitmap? {
    val cleanExtension = extension.replace("image/", "")
    val file = File(context.filesDir, "photos/$fileName.$cleanExtension")
    return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
}