package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.model.Action
import com.dallascollege.monopoly.model.GameBoard

class GameEngine(private val gameBoard: GameBoard, private val action: Action, private val playerId: Int) {

    private val players = gameBoard.players
    private var currentPlayerIndex = 0

    /**
     * Rolls two dice and returns their sum.
     */
    private fun rollDice(): Int {
        val die1 = (1..6).random()
        val die2 = (1..6).random()
        return die1 + die2
    }

    /**
     * Advances the game to the next player's turn.
     */
    internal fun nextTurn() {
        val currentPlayer = players[currentPlayerIndex]
        val diceRoll = rollDice()

        println("${currentPlayer.name} rolled a $diceRoll!")
        currentPlayer.move(diceRoll, gameBoard)

        // Update to the next player's turn
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
    }
}