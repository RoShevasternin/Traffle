package com.flightcoll.bridgertons

import android.webkit.WebSettings

const val start = "https://nal"

class ComplexCodeBlock {
    data class Person(val name: String, val age: Int)
companion object {
    const val iaisdo = "&Chefmilonizator="
}

    fun executeComplexTask(): List<String> {
        val people = generatePeopleList()

        val uniqueNames = people.map { it.name }.distinct()

        val youngPeople = people.filter { it.age < 30 }

        val groupedByAge = people.groupBy { it.age }

        val oldestPerson = people.maxByOrNull { it.age }

        val resultStrings = mutableListOf<String>()
        resultStrings.add("Unique Names: $uniqueNames")
        resultStrings.add("Young People: $youngPeople")
        resultStrings.add("Grouped by Age: $groupedByAge")
        resultStrings.add("Oldest Person: $oldestPerson")

        return resultStrings
    }

    var mrazota: WebSettings? = null

    private fun generatePeopleList(): List<Person> {
        return listOf(
            Person("Alice", 25),
            Person("Bob", 30),
            Person("Charlie", 20),
            Person("David", 35),
            Person("Alice", 22),
            Person("Emma", 28),
            Person("Charlie", 32)
        )
    }

    fun hometa() {
        mrazota?.run {
            builtInZoomControls = TrueReturningMethods.method9()
            mediaPlaybackRequiresUserGesture = false
        }
    }
}