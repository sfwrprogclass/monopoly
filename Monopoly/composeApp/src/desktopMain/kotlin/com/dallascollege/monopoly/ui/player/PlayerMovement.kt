package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.model.GameBoard

fun moveCurrentPlayer(gameBoard: GameBoard, steps: Int) {
    val player = gameBoard.players[gameBoard.currentTurn]
    val totalCells = gameBoard.cells.size

    player.numCell = (player.numCell + steps) % totalCells
    println("${player.name} moved to cell ${player.numCell}")

    gameBoard.currentTurn = (gameBoard.currentTurn + 1) % gameBoard.players.size
}
