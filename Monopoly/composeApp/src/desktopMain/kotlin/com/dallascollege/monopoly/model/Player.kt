package com.dallascollege.monopoly.model


import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.enums.Token

class Player(
    var id: Int = 1,
    var money: Int = 1500,
    var turnNum: Int = 1,
    var name: String,
    var inJail: Boolean = false,
    var hasOutJailCard: Boolean = false,
   

    var token: Token = token
        get() = field
        set(value) {
            field = value
        }, 
    var games: Array<Game> = emptyArray(),
    var isCPU: Boolean = false,
    var numCell: Int = 1,
    val properties: MutableList<Property> = mutableListOf()
) {

    fun addProperty(property: Property) {
        properties.add(property)
    }

    fun getRailroadCount(): Int {
        return properties.count { it.isRailRoad }
    }

    fun getAllProperties(): List<Property> {
        return properties.toList()
    }

    fun canBuildHouse(property: Property): Boolean {
        return properties.count { it.color == property.color } == getColorGroupSize(property.color)
    }

    fun move(steps: Int, gameBoard: GameBoard) {
        numCell = (numCell + steps) % gameBoard.cells.size
        println("$name moved to cell $numCell")
    }

    fun getColorGroupSize(color: PropertyColor): Int {
        return when (color) {
            PropertyColor.BROWN, PropertyColor.LIGHT_BLUE, PropertyColor.PINK, PropertyColor.ORANGE,
            PropertyColor.RED, PropertyColor.YELLOW, PropertyColor.GREEN, PropertyColor.BLUE -> 3
            else -> 0
        }
    }

    fun addMoney(amount: Int) {
        money += amount
    }

    fun deductMoney(amount: Int) {
        money -= amount
    }

    fun getUtilityCount(): Int {
        return properties.count { it.isUtility }
    }

    companion object {
        fun getUtilityCount(player: Player): Int {
            return player.properties.count { it.isUtility }
        }
    }
}
