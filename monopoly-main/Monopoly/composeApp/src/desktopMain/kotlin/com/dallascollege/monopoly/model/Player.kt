package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Token

class Player(
    id: Number = 1,
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
    var id: Number = id
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

    var propertyIds: Array<Int> = propertyIds
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

    var games: Array<Game> = games
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

fun reorderPlayersByTurnOrder(players: MutableList<Player>, turnOrder: List<String>) {
    players.sortBy { player -> turnOrder.indexOf(player.token.name) }
}
