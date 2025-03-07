package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Token

class Player(
    id: Number,
    totalMoney: Int,
    turnNum: Int,
    name: String,
    token: Token,
    propertyIds: Array<Int>,
    inJail: Boolean,
    hasOutJailCard: Boolean,
    games: Array<Game>,
    isCPU: Boolean,
    numCell: Int
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
