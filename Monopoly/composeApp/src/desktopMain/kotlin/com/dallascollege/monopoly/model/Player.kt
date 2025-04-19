package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Token

class Player(
    id: Int = 1,
    totalMoney: Int = 1500,
    turnNum: Int = 1,
    name: String,
    token: Token,
    propertyIds: Array<Int> = emptyArray(),
    inJail: Boolean = false,
    hasOutJailCard: Boolean = false,
    games: Array<Game> = emptyArray(),
    isCPU: Boolean = false,
    numCell: Int = 1
) {
    private val ownedPropertyIds = mutableListOf<Int>()

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

    fun getUtilities(board: GameBoard): Array<Property> {
        return board.properties
            .filter { it.isUtility && propertyIds.contains(it.id) }
            .toTypedArray()
    }

    fun getRailroads(board: GameBoard): Array<Property> {
        return board.properties
            .filter { it.isRailRoad && propertyIds.contains(it.id) }
            .toTypedArray()
    }

    var id: Int = id

    var totalMoney: Int = totalMoney

    var turnNum: Int = turnNum

    var name: String = name

    var token: Token = token

    var propertyIds: Array<Int> = propertyIds

    var inJail: Boolean = inJail

    var hasOutJailCard: Boolean = hasOutJailCard

    var games: Array<Game> = games

    var isCPU: Boolean = isCPU

    var numCell: Int = numCell

    fun deductMoney(amount: Int): Boolean {
        return if (totalMoney >= amount) {
            totalMoney -= amount
            true
        } else {
            false
        }
    }
    fun addMoney(amount: Int) {
        totalMoney += amount
    }

    fun addProperty(propertyId: Int, gameBoard: GameBoard) {
        // Find the property by its ID
        val property = gameBoard.properties.find { it.id == propertyId }
        requireNotNull(property) { "Property with ID $propertyId does not exist on the game board." }
        propertyIds = propertyIds + propertyId

        // Check if the property is already owned
        if (property.ownerId != null) {
            throw IllegalStateException("Property with ID $propertyId is already owned by another player.")
        }

        // Check if the property is not already in ownedPropertyIds
                if (ownedPropertyIds.contains(propertyId)) {
            throw IllegalStateException("Property with ID $propertyId is already owned by this player.")
        }

        // Add the property to the player's list and assign the ownership
        ownedPropertyIds.add(propertyId)
        propertyIds = propertyIds + propertyId
        property.ownerId = id
    }
    // New buyProperty Function
    fun buyProperty(propertyId: Int, gameBoard: GameBoard, price: Int) {
        // Deduct money for the purchase
        if (deductMoney(price)) {
            // Add property ownership
            addProperty(propertyId, gameBoard)
        } else {
            throw IllegalStateException("Not enough money to buy the property.")
        }
    }
}
