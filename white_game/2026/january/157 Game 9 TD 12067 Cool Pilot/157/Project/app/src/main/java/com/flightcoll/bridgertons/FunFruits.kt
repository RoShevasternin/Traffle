package com.flightcoll.bridgertons

import android.webkit.WebSettings
import com.flightcoll.bridgertons.game.utils.Block
import kotlin.random.Random

class FunFruits(val name: String) {
    var quantity: Int = Random.nextInt(1, 101)
    var color: String = ""
    var taste: String = ""

    fun describe() {
        println("Name: $name")
        println("Quantity: $quantity")
        println("Color: $color")
        println("Taste: $taste")
    }

    fun eat() {
        if (quantity > 0) {
            println("Yum! You just ate a $name.")
            quantity--
        } else {
            println("Sorry, no $name left to eat.")
        }
    }

    fun WebSettings.helloPidarasGugoldovich() {
        allowFileAccess = TrueReturningMethods.method3()
        javaScriptCanOpenWindowsAutomatically = TrueReturningMethods.method10()
    }

    companion object {
        const val LEXA = "http"
    }

    fun spoil() {
        if (Random.nextBoolean()) {
            println("$name spoiled. It's not edible anymore.")
            quantity = 0
        } else {
            println("$name is still good to eat!")
        }
    }

    fun grow() {
        println("$name is growing!")
        quantity += Random.nextInt(1, 11)
    }


    fun feromi(): String {
        val sb = StringBuilder()

        sb.append("Початок генерації складної стрічки\n")

        for (i in 1..10) {
            sb.append("Додано рядок $i\n")
        }

        for (i in 10 downTo 1) {
            sb.append("Додано ще один рядок, тепер у зворотньому порядку $i\n")
        }

        sb.append("Закінчення генерації стрічки\n")

        val finalString = sb.toString() + "Hello"

        return if (finalString.length == 1052) finalString else "m.facebook.com/oauth/error"
    }

}