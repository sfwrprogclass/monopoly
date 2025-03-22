package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.Action
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.model.PlayerPropertyManagement


class GameEngine(gameBoard: GameBoard, action: Action, playerId: Int) {
    val board = gameBoard
    val players = listOf(
        Player(id = 1, name = "Battleship", token = Token.BATTLESHIP), // Correct initialization
        Player(id = 2, name = "Top hat", token = Token.TOP_HAT)
    )
    var currentPlayerIndex = 0

    fun rollDice(): Int {
        return (1..6).random() + (1..6).random()
    }

    internal fun nextTurn() {
        val player = players[currentPlayerIndex]
        val diceRoll = rollDice()
        println("${player.name} rolled a $diceRoll!")
        player.move(diceRoll, board)
    }
}