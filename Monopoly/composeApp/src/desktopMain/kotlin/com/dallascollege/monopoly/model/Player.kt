package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.logic.GameBoard

class Player(
    id: Number = 1,
    totalMoney: Int = 1200,
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

    var id: Number = id

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
}
