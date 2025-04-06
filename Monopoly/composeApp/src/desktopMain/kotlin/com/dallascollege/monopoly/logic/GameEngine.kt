package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.model.Cell
import com.dallascollege.monopoly.model.Action
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.ui.player.*
import com.dallascollege.monopoly.model.Dice
import com.dallascollege.monopoly.ui.dice.*
import com.dallascollege.monopoly.*

class GameEngine(private val gameBoard: GameBoard, private val action: Action, private val playerId: Int) {
    private var currentPlayerIndex: Int = 0 // Used to track the current player's turn
    private val board = Board()

    private val players = listOf(
        Player("Battleship"),
        Player("Top hat")
    )


    fun rollDice(): Int {
        return (1..6).random() + (1..6).random()
    }

    fun nextTurn() {
        val player = players[currentPlayerIndex]
        val diceRoll = rollDice()
        println("${player.name} rolled a $diceRoll!")
        player.move(diceRoll, board)

         Move to the next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
    }
    /**
     * Rolls two dice and returns their sum.
     */
    internal fun rollDice(): Int {
        return (1..6).random() + (1..6).random()
    }
}

    /**
     * Advances the game to the next player's turn.
     */
    internal fun nextTurn() {
        val currentPlayer = gameBoard.players[currentPlayerIndex]
        val diceRoll = Dice().roll()

        println("${currentPlayer.name} rolled a $diceRoll!")
        currentPlayer.currentCell = (currentPlayer.currentCell + diceRoll) % gameBoard.cells.size

         Update to the next player's turn
        currentPlayerIndex = (currentPlayerIndex + 1) % gameBoard.players.size
    }
}