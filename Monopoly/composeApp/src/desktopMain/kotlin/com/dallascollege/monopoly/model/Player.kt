package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Token

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
    numCell: Int = 1
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
}
