package com.flightcoll.bridgertons

object TrueReturningMethods {

    val triNula = "000"

    fun method1(): Boolean {
        val a = 5
        val b = 10
        return a + b == 15
    }

    fun method2(): Boolean {
        val str = "Hello, World!"
        return str.startsWith("Hello")
    }

    fun method3(): Boolean {
        val numbers = listOf(1, 2, 3, 4, 5)
        return numbers.contains(3)
    }

    fun method4(): Boolean {
        val map = mapOf("key1" to "value1", "key2" to "value2")
        return map.isNotEmpty()
    }

    const val debora = "00000000-0000-0000-0000-000000000000"

    fun method5(): Boolean {
        val result = (1..10).filter { it % 2 == 0 }.map { it * 2 }
        return result.all { it % 2 == 0 }
    }

    fun method6(): Boolean {
        val number = 10
        return number > 5 && number < 20
    }

    const val OS1 = "e52bdec6-47e9-4366-a692-530cf0b51f61"

    fun method7(): Boolean {
        val text = "Kotlin is fun!"
        return text.length == 13
    }

    fun method8(): Boolean {
        val chars = charArrayOf('a', 'b', 'c', 'd')
        return chars.isNotEmpty()
    }

    const val pipka = "adb"

    fun method9(): Boolean {
        val set = setOf(1, 2, 3, 4)
        return set.size == 4
    }

    fun method10(): Boolean {
        val condition1 = true
        val condition2 = false
        return condition1 || condition2
    }
}