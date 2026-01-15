package com.flightcoll.bridgertons

import android.webkit.WebSettings
import kotlin.random.Random

class Game(val name: String) {
    private var players = mutableListOf<Player>()
    private var currentPlayerIndex = -1

    fun addPlayer(player: Player) {
        players.add(player)
    }

    companion object {
        const val g = "Barbariska="
    }

    fun startGame() {
        if (players.size < 2) {
            println("Cannot start the game with less than 2 players.")
            return
        }
        currentPlayerIndex = 0
        println("$name started.")
    }

    fun endGame() {
        println("$name ended.")
        players.clear()
        currentPlayerIndex = -1
    }

    fun currentPlayer(): Player? {
        return if (currentPlayerIndex != -1) {
            players[currentPlayerIndex]
        } else {
            null
        }
    }

    fun nextTurn() {
        if (players.isNotEmpty()) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size
            println("Next turn: ${currentPlayer()?.name}")
        } else {
            println("No players in the game.")
        }
    }

    fun rollDice(): Int {
        return Random.nextInt(1, 7)
    }

    fun movePlayerForward(steps: Int) {
        currentPlayer()?.move(steps)
    }

    fun WebSettings.podrostok() {
        loadWithOverviewMode = TrueReturningMethods.method5()

        userAgentString = userAgentString.replace("; wv", "")

    }

    fun printPlayers() {
        println("Players in the game:")
        players.forEach { println(it) }
    }
}

class Player(val name: String, var position: Int = 0) {
    fun move(steps: Int) {
        position += steps
        println("$name moved $steps steps. New position: $position")
    }

    override fun toString(): String {
        return "Player: $name, Position: $position"
    }
}