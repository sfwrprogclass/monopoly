package com.dallascollege.monopoly.model

import java.util.Date

class Game(
    position: Int,
    owedBank: Boolean,
    owedPlayer: Boolean,
    date: Date
) {
    var position: Int = position

    var owedBank: Boolean = owedBank

    var owedPlayer: Boolean = owedPlayer

    var date: Date = date
}
