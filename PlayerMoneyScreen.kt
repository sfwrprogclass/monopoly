package com.dallascollege.monopoly.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.Player

@Composable
fun PlayerMoneyScreen(players: List<Player>) {
    var currentPlayerIndex by remember { mutableStateOf(0) }
    val currentPlayer = players[currentPlayerIndex]

    // Define the images corresponding to different money amounts
    val moneyImages = mapOf(
        1 to "one_dollar.png",
        5 to "five_dollar.png",
        10 to "ten_dollar.png",
        20 to "twenty_dollar.png",
        50 to "fifty_dollar.png",
        100 to "onehundred_dollar.png",
        500 to "five_hundred_dollar.png"
    )

    // Function to find the closest money image based on player's money
    fun getMoneyImage(money: Int): String {
        return moneyImages.entries
            .filter { it.key <= money }
            .maxByOrNull { it.key }?.value ?: "one_dollar.png" // Default to "one_dollar.png"
    }

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C0474)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Card to display money info
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display current player information
                Text(
                    "Player ${currentPlayerIndex + 1}'s Money",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Display the money image for the player
                Image(
                    painter = painterResource("images/${getMoneyImage(currentPlayer.money)}"),
                    contentDescription = "Player Money",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display the player's current money value (optional)
                Text(
                    "$${currentPlayer.money}",
                    style = MaterialTheme.typography.h4,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Button to move to the next player
                Button(
                    onClick = {
                        if (currentPlayerIndex < players.size - 1) {
                            currentPlayerIndex++
                        } else {
                            currentPlayerIndex = 0 // Reset to the first player if at the end
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
                ) {
                    Text("Next Player", color = Color.Black)
                }
            }
        }
    }
}
