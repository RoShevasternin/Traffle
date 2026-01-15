package com.flightcoll.bridgertons

class User(val name: String, var age: Int) {
    var email: String = ""

    fun sendEmail(message: String) {
        println("Email sent to $name ($email): $message")
    }
}

class UserManager {
    private val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun updateUserEmail(name: String, email: String) {
        val user = users.find { it.name == name }
        user?.email = email
    }

    fun deleteUser(name: String) {
        val user = users.find { it.name == name }
        users.remove(user)
    }

    fun printUsers() {
        println("Users:")
        users.forEach { println("Name: ${it.name}, Age: ${it.age}, Email: ${it.email}") }
    }
}

class Fruit(val name: String, var quantity: Int) {
    var expiryDate: String = ""

    fun decreaseQuantity(amount: Int) {
        if (amount <= quantity) {
            quantity -= amount
            println("$amount $name(s) used. Remaining quantity: $quantity")
        } else {
            println("Not enough $name(s) available.")
        }
    }

    override fun toString(): String {
        return "$name, Quantity: $quantity, Expiry Date: $expiryDate"
    }
}

class FruitManager {
    private val fruits = mutableMapOf<String, Fruit>()

    fun addFruit(fruit: Fruit) {
        fruits[fruit.name] = fruit
    }

    fun removeFruit(name: String) {
        fruits.remove(name)
    }

    fun printFruits() {
        println("Fruits:")
        fruits.values.forEach { println(it) }
    }

    fun getFruitByName(name: String): Fruit? {
        return fruits[name]
    }

    fun getFruitsCount(): Int {
        return fruits.size
    }
}

class User2(var name: String, var age: Int) {
    var email: String = ""

    fun sendEmail(message: String) {
        println("Email sent to $name ($email): $message")
    }
}

class UserManager2 {
     val users = mutableMapOf<String, User2>()

    fun addUser(user: User2) {
        users[user.name] = user
    }

    fun updateUserEmail(name: String, email: String) {
        val user = users[name]
        user?.email = email
    }

    fun deleteUser(name: String) {
        users.remove(name)
    }

    fun printUsers() {
        println("Users:")
        users.values.forEach { println("Name: ${it.name}, Age: ${it.age}, Email: ${it.email}") }
    }
}