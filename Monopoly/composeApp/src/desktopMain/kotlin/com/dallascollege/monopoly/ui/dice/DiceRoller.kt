package com.dallascollege.monopoly.ui.dice

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.dallascollege.monopoly.logic.GameEngine
import com.dallascollege.monopoly.model.Dice

@Composable
fun DiceRoller(gameBoard: GameBoard) {
    val dice = remember { Dice() }
    var dice1 by remember { mutableStateOf(1) }
    var dice2 by remember { mutableStateOf(1) }
    var message by remember { mutableStateOf("") } // Message to display game state

    // Function to roll dice and move the player's token
    fun rollDice() {
        dice1 = dice.roll()
        dice2 = dice.roll()
        val totalRoll = dice1 + dice2

        val currentPlayer = gameBoard.players.find { it.id == gameBoard.currentTurn }
        if (currentPlayer != null) {
            currentPlayer.numCell = (currentPlayer.numCell + totalRoll) % gameBoard.cells.size // Update player's position
            message = "${currentPlayer.name} rolled $totalRoll and moved to position ${currentPlayer.numCell}."
        }
    }

    // Function to pass to the next player's turn
    fun nextTurn() {
        gameBoard.currentTurn = (gameBoard.currentTurn + 1) % gameBoard.players.size
        val nextPlayer = gameBoard.players.find { it.id == gameBoard.currentTurn }
        message = "It's now ${nextPlayer?.name}'s turn!"
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Dice roll section
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DiceView(dice1)
            DiceView(dice2)
            Button(
                onClick = {
                    rollDice()
                    val total = dice1 + dice2
                    GameEngine.movePlayer(gameBoard, gameBoard.currentTurn, total)
                }
            ) {
                Text("Roll the dice")
            }
            // "Next Player" button
            Button(
                onClick = { nextTurn() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Next Player")
            }

            // Display message for the current game state
            Text(
                text = message,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}