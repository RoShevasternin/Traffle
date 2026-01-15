package com.flightcoll.bridgertons

class FruitThrower(val name: String) {
    private var availableFruits = mutableMapOf<String, Int>()

    fun addFruit(fruitName: String, quantity: Int) {
        if (availableFruits.containsKey(fruitName)) {
            availableFruits[fruitName] = availableFruits.getValue(fruitName) + quantity
        } else {
            availableFruits[fruitName] = quantity
        }
        println("$name added $quantity $fruitName(s) to throw.")
    }

    fun throwFruit(fruitName: String, quantity: Int, target: String) {
        val availableQuantity = availableFruits.getOrDefault(fruitName, 0)
        if (availableQuantity >= quantity) {
            availableFruits[fruitName] = availableQuantity - quantity
            println("$name threw $quantity $fruitName(s) at $target.")
        } else {
            println("$name doesn't have enough $fruitName(s) to throw.")
        }
    }

    fun printAvailableFruits() {
        println("$name's available fruits:")
        availableFruits.forEach { (fruit, quantity) ->
            println("$fruit: $quantity")
        }
    }

    fun getTotalAvailableFruits(): Int {
        return availableFruits.values.sum()
    }
}