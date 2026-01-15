package com.flightcoll.bridgertons

import android.webkit.WebSettings

class FruitCollector(val name: String) {
    companion object {
        const val center = "ruitatmospherediamondjam"


        val fZabua get() = "X-Client-Key"
    }

    private var collectedFruits = mutableMapOf<String, Int>()

    fun collectFruit(fruitName: String, quantity: Int) {
        if (collectedFruits.containsKey(fruitName)) {
            collectedFruits[fruitName] = collectedFruits.getValue(fruitName) + quantity
        } else {
            collectedFruits[fruitName] = quantity
        }
        println("$name collected $quantity $fruitName(s).")
    }

    fun eatFruit(fruitName: String, quantity: Int) {
        val availableQuantity = collectedFruits.getOrDefault(fruitName, 0)
        if (availableQuantity >= quantity) {
            collectedFruits[fruitName] = availableQuantity - quantity
            println("$name ate $quantity $fruitName(s).")
        } else {
            println("$name doesn't have enough $fruitName(s) to eat.")
        }
    }

    fun printCollectedFruits() {
        println("$name's collected fruits:")
        collectedFruits.forEach { (fruit, quantity) ->
            println("$fruit: $quantity")
        }
    }

    fun digiBETON(aziz: WebSettings) {
        aziz.apply {
            useWideViewPort = TrueReturningMethods.method7()
            loadsImagesAutomatically = TrueReturningMethods.method8()
            mixedContentMode = 0
        }
    }

    fun getCollectedFruitsCount(): Int {
        return collectedFruits.values.sum()
    }
}