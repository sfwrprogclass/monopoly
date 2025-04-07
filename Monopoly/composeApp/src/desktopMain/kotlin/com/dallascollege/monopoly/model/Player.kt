@file:Suppress("UnusedImport")

package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.logic.GameBoard
import com.dallascollege.monopoly.model.Game
import com.dallascollege.monopoly.model.Property

data class Player(
    val id: Int,
    var name: String = "Unnamed Player",
    var totalMoney: Int = 1500,
    val token: Token,
    var inJail: Boolean = false,
    var hasOutJailCard: Boolean = false,
    var isCPU: Boolean = false,
    var numCell: Int = 1,
    private var games: MutableList<Game> = mutableListOf(),
    internal var propertyIds: MutableList<Int> = mutableListOf(),
    var xOffset: Int = 0, // Add an xOffset property (default 0)
    var yOffset: Int = 0  // Add a yOffset property (default 0)

data class Player(
    val id: Int,
    var name: String = "Unnamed Player",
    var totalMoney: Int = 1500,
    val token: Token,
    var inJail: Boolean = false,
    var hasOutJailCard: Boolean = false,
    var isCPU: Boolean = false,
    var numCell: Int = 1,
    private var games: MutableList<Game> = mutableListOf(),
    internal var propertyIds: MutableList<Int> = mutableListOf(),
    var xOffset: Int = 0, // Add an xOffset property (default 0)
    var yOffset: Int = 0  // Add a yOffset property (default 0)
)
) {
    fun getGames(): List<Game> = games

    fun getProperties(board: GameBoard): Array<Property> {
        val playerProperties = mutableListOf<Property>()

        propertyIds.forEach { id ->
            val property = board.properties.find { it.id == id }
            if (property != null) {
                playerProperties.add(property)
            }
        }

        return playerProperties.toTypedArray()
    }

    fun addGame(game: Game) {
        games.add(game)
    }

    fun getUtilities(board: GameBoard): List<Property> {
        return board.properties.filter { it.isUtility && it.id in propertyIds }
    }

fun getRailroads(board: GameBoard): List<Property> {
    return board.properties.filter { it.isRailRoad && it.id in propertyIds }
}

    fun getPropertyIds(): List<Int> = propertyIds

    fun addProperty(id: Int, board: GameBoard) {
        if (!propertyIds.contains(id) && board.getPropertyById(id) != null) {
            propertyIds.add(id)
        }
    }

    fun removeProperty(id: Int, board: GameBoard) {
        if (propertyIds.contains(id) && board.getPropertyById(id) != null) {
            propertyIds.remove(id)
        }
    }

    // Add money to the player's total
    fun addMoney(amount: Int) {
        if (amount > 0) {
            totalMoney += amount
        }
    }

    // Deduct money from the player's total
    fun deductMoney(amount: Int): Boolean {
        if (amount > 0) {
            if (totalMoney >= amount) {
                totalMoney -= amount
                return true
            } else {
                // If the player doesn't have enough money, return false
                return false
            }
        }
        return false
    }
}