package com.dallascollege.monopoly.model

import java.util.Date

class Game(
    position: Int,
    owedBank: Boolean,
    owedPlayer: Boolean,
    date: Date
) {
    var position: Int = position
        get() = field
        set(value) {
            field = value
        }

    var owedBank: Boolean = owedBank
        get() = field
        set(value) {
            field = value
        }

    var owedPlayer: Boolean = owedPlayer
        get() = field
        set(value) {
            field = value
        }

    var date: Date = date
        get() = field
        set(value) {
            field = value
        }
}
