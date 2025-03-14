package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.enums.PropertyColor

data class Player(
    var id: Number = 1,
    var money: Int = 1500, // Starting money
    var turnNum: Int = 1,
    var name: String,
    var token: Token,
    var propertyIds: Array<Int> = emptyArray(),
    var inJail: Boolean = false,
    var hasOutJailCard: Boolean = false,
    var games: Array<Game> = emptyArray(),
    var isCPU: Boolean = false,
    var numCell: Int = 1
) {
    private val properties: MutableList<Property> = mutableListOf()

    fun addProperty(property: Property) {
        properties.add(property)
    }

    fun getUtilityCount(): Int {
        return properties.count { it.isUtility }
    }

    fun getRailroadCount(): Int {
        return properties.count { it.isRailRoad }
    }

    fun getProperties(): List<Property> {
        return properties.toList()
    }

    fun canBuildHouse(property: Property): Boolean {
        return properties.count { it.color == property.color } == getColorGroupSize(property.color)
    }

    private fun getColorGroupSize(color: PropertyColor): Int {
        return when (color) {
            PropertyColor.BROWN, PropertyColor.LIGHT_BLUE, PropertyColor.PINK, PropertyColor.ORANGE,
            PropertyColor.RED, PropertyColor.YELLOW, PropertyColor.GREEN, PropertyColor.BLUE -> 3
            else -> 0
        }
    }
}
