package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.enums.Token

class Player(
    var id: Int = 1,
    var money: Int = 1500,
    var turnNum: Int = 0, // Defaulting turnNum to 0 for consistency with potential zero-based logic
    var name: String = "Player $id", // Providing a default name
    var inJail: Boolean = false,
    var hasOutJailCard: Boolean = false,
    var token: Token = Token.BATTLESHIP, // Ensuring a valid default token
    var games: Array<Game> = emptyArray(), // Assuming this array will be clarified for its actual intention
    var isCPU: Boolean = false,
    var numCell: Int = 0, // Default to 0 for consistency with 0-based indexing on the board
    private val _properties: MutableList<Property> = mutableListOf() // Internal mutable list for safety
) {
    // Publicly exposed immutable list of properties
    val properties: List<Property>
        get() = _properties

    // Function to add property with duplicate prevention
    fun addProperty(property: Property) {
        if (!_properties.contains(property)) {
            _properties.add(property)
        }
    }

    // Function to count the number of railroads
    fun getRailroadCount(): Int {
        return properties.count { it.isRailRoad }
    }

    // Function to return all properties as a List (redundant due to getter)
    fun getAllProperties(): List<Property> {
        return properties.toList()
    }

    // Function to check if the player can build a house on a property
    fun canBuildHouse(property: Property): Boolean {
        // Consider unmortgaged properties only
        return properties
            .filter { !it.isMortgaged }
            .count { it.color == property.color } == PropertyColor.getColorGroupSize(property.color)
    }

    // Function to move the player's position on the board
    fun move(steps: Int, gameBoard: GameBoard) {
        // Handle negative steps (backward moves) correctly
        numCell = (numCell + steps + gameBoard.cells.size) % gameBoard.cells.size
        println("$name moved to cell $numCell")
    }

    // Function to add money
    fun addMoney(amount: Int) {
        money += amount
    }

    // Function to deduct money with validation
    fun deductMoney(amount: Int) {
        if (money >= amount) {
            money -= amount
        } else {
            money = 0 // Enforcing non-negative balance
        }
    }

    // Function to count the number of utilities
    fun getUtilityCount(): Int {
        return properties.count { it.isUtility }
    }
}