package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.Dice
import com.dallascollege.monopoly.model.Player

private var currentPlayerIndex: Int = 0 // Used to track the current player's turn

private val players = listOf(
    Player(id = 1, name = "Battleship", token = Token.BATTLESHIP),
    Player(id = 2, name = "Top hat", token = Token.TOPHAT)
)

private val board: GameBoard by lazy {
    GameBoard(
        initialPlayers = players.toTypedArray(),


    ).apply {
        createModels()
    }
}


private fun rollDice(): Int {
    return (1..6).random() + (1..6).random()
}

private fun processTurn() {
    val player = players[currentPlayerIndex]
    val diceRoll = rollDice()
    println("${player.name} rolled a $diceRoll!")
    player.numCell = (player.numCell + diceRoll) % board.cells.size

    // Move to the next player
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size
}
/**
 * Rolls two dice and returns their sum.
 */

    /**
     * Advances the game to the next player's turn.
     */
    internal fun nextTurn() {
        val currentPlayer = board.players[currentPlayerIndex]
        val diceRoll = Dice().roll()

        println("${currentPlayer.name} rolled a $diceRoll!")
        currentPlayer.numCell = (currentPlayer.numCell + diceRoll) % board.cells.size

        // Update to the next player's turn
        currentPlayerIndex = (currentPlayerIndex + 1) % board.players.size
    }
