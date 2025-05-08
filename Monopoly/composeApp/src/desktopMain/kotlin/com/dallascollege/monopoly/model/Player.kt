package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.utils.PROPERTY_COLOR_VALUES

class Player(
    var id: Int = 1,
    var totalMoney: Int = 1500,
    var turnNum: Int = 1,
    var name: String,
    var token: Token,
    var propertyIds: MutableList<Int> = mutableListOf<Int>(),
    var inJail: Boolean = false,
    var hasOutJailCard: Boolean = false,
    var games: MutableList<Game> = mutableListOf<Game>(),
    var numCell: Int = 1,
    var isAI: Boolean = false // ai players
) {

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

    fun getPropertiesByColor(board: GameBoard, color: PropertyColor): Array<Property> {
        val playerProperties = getProperties(board).filter { it.color == color }
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

    var consecutiveDoubles: Int = 0 // track doubles for jail

    // New properties for jail rules
    var jailTurnCount: Int = 0 // how many turns player has been in jail
    var wantsToPayJailFee: Boolean = false // set to true if player chooses to pay $50
    var attemptedToRollDoubles: Boolean = false // used to track if they attempted roll this turn

    // simple bankruptcy flag
    var isBankrupt: Boolean = false

    fun isEliminated(board: GameBoard): Boolean {
        return isBankrupt
    }

    fun hasAllPropertiesByColor(board: GameBoard, color: PropertyColor): Boolean {
        val properties = getProperties(board)

        val numberOfProperties = properties.count { it.color == color }
        return numberOfProperties == PROPERTY_COLOR_VALUES[color]
    }
}
