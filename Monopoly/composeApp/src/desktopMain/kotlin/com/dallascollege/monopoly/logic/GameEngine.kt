package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.Action
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.model.PlayerPropertyManagement


class GameEngine(gameBoard: GameBoard, action: Action, playerId: Int) {

//    private val board = Board()
//    private val players = listOf(
//        Player("Battleship"),
//        Player("Top hat")
//    )
//    private var currentPlayerIndex = 0
//
//    fun rollDice(): Int {
//        return (1..6).random() + (1..6).random()
//    }
//
//    fun nextTurn() {
//        val player = players[currentPlayerIndex]
//        val diceRoll = rollDice()
//        println("${player.name} rolled a $diceRoll!")
//        player.move(diceRoll, board)
//
//        // Move to the next player
//        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
//    }
}

    internal fun nextTurn() {
        val player = players[currentPlayerIndex]
        val diceRoll = rollDice()
        println("${player.name} rolled a $diceRoll!")
        player.move(diceRoll, board)
    }
}