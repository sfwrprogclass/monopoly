package com.dallascollege.monopoly.model

import androidx.compose.runtime.MutableState

class Card(
    val isChance: Boolean = false,
    val isPayment: Boolean = false,
    val amount: Int = 0,
    val goToJail: Boolean = false,
    val back3Cells: Boolean = false,
    val advancedToGo: Boolean = false,
    val getOutOfJail: Boolean = false,
    val message: String
) {

    fun performCardAction(player: Player, message: MutableState<String>) {

        message.value = this.message

        if (isPayment) {
            player.totalMoney -= amount
            return
        }

        if (goToJail) {
            player.inJail
            player.numCell = 23
            return
        }

        if (back3Cells) {
            player.numCell -= 3
            return
        }

        if (advancedToGo) {
            player.numCell = 1
            player.totalMoney += 200
            return
        }

        if (getOutOfJail) {
            player.hasOutJailCard = true
            return
        }

        player.totalMoney += amount
    }
}

