package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Token

data class Player(
    var id: Number = 1,
    var money: Int = 1200,
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Player

        if (id != other.id) return false
        if (name != other.name) return false
        if (token != other.token) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + token.hashCode()
        return result
    }
}
