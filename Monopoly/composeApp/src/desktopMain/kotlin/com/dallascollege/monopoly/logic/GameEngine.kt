package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.*

class GameEngine(gameBoard: GameBoard, action: Action, playerId: Int) {
    private val board = gameBoard
    private val players = listOf(
        Player(id = 1, name = "Battleship", token = Token.BATTLESHIP), // Correct initialization
        Player(id = 2, name = "Top hat", token = Token.TOP_HAT)
    )
    private var currentPlayerIndex = 0

    fun rollDice(): Int {
        return (1..6).random() + (1..6).random()
    }

    fun nextTurn() {
        val player = players[currentPlayerIndex]
        val diceRoll = rollDice()
        println("${player.name} rolled a $diceRoll!")
        player.move(diceRoll, board)
    }
}