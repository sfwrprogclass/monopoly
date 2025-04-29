package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.utils.PROPERTY_COLOR_VALUES

class Player(
    id: Int = 1,
    totalMoney: Int = 1500,
    turnNum: Int = 1,
    name: String,
    token: Token,
    propertyIds: MutableList<Int> = mutableListOf<Int>(),
    inJail: Boolean = false,
    hasOutJailCard: Boolean = false,
    games: MutableList<Game> = mutableListOf<Game>(),
    isCPU: Boolean = false,
    numCell: Int = 1,
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
        get() = field
        set(value) {
            field = value
        }

    var totalMoney: Int = totalMoney
        get() = field
        set(value) {
            field = value
        }

    var turnNum: Int = turnNum
        get() = field
        set(value) {
            field = value
        }

    var name: String = name
        get() = field
        set(value) {
            field = value
        }

    var token: Token = token
        get() = field
        set(value) {
            field = value
        }

    var propertyIds: MutableList<Int> = propertyIds
        get() = field
        set(value) {
            field = value
        }

    var inJail: Boolean = inJail
        get() = field
        set(value) {
            field = value
        }

    var hasOutJailCard: Boolean = hasOutJailCard
        get() = field
        set(value) {
            field = value
        }

    var games: MutableList<Game> = games
        get() = field
        set(value) {
            field = value
        }

    var isCPU: Boolean = isCPU
        get() = field
        set(value) {
            field = value
        }

    var numCell: Int = numCell
        get() = field
        set(value) {
            field = value
        }

    fun isEliminated(board: GameBoard): Boolean {
        //        var mortgageAllPropertiesMoney = 0
        //
        //        propertyIds.forEach { id ->
        //            val property = board.getPropertyById(id)
        //            if (property != null && !property.isMortgage) {
        //                mortgageAllPropertiesMoney += property.price / 2
        //                //TODO: calculate the money user can get selling houses and hotels
        //            }
        //        }
        //
        //        return totalMoney + mortgageAllPropertiesMoney <= 0
        return totalMoney <= 0
    }

    fun hasAllPropertiesByColor(board: GameBoard, color: PropertyColor): Boolean {
        val properties = getProperties(board)

        val numberOfProperties = properties.count { it.color == color }
        return numberOfProperties == PROPERTY_COLOR_VALUES[color]
    }
}
