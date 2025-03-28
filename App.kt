package com.dallascollege.monopoly

import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Tile
import com.dallascollege.monopoly.ui.TokenSelectionScreen
import com.dallascollege.monopoly.ui.dice.DiceRoller

@Composable
fun App() {
    val players = remember { mutableStateListOf(Player("Battleship"), Player("Top hat")) }
    val tiles = remember { mutableStateListOf<Tile>() }
    var allTokensSelected by remember { mutableStateOf(false) }
    val selectedTokens = remember { mutableStateMapOf<Player, String>() }

    var showMoneyScreen by remember { mutableStateOf(false) }

    if (!allTokensSelected) {
        TokenSelectionScreen(players) { player, token ->
            selectedTokens[player] = token
            if (selectedTokens.size == players.size) {
                allTokensSelected = true
                showMoneyScreen = true
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showMoneyScreen) {
                Text("All players have selected their tokens!", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))

                PlayerMoneyScreen(players)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    // Hide money and show dice roll
                    showMoneyScreen = false
                }) {
                    Text("Proceed to Dice Roll")
                }
            } else {
                // Button to roll dice once players see their money
                Button(onClick = { /* dice roll logic */ }) {
                    DiceRoller()
                }
            }
        }
    }
}
@Composable
fun PlayerMoneyScreen(players: List<Player>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        players.forEach { player ->
            Card(
                modifier = Modifier.padding(8.dp),
                backgroundColor = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "${player.name}'s Money",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "$${player.money}",
                        style = MaterialTheme.typography.h5,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
// Player model with name and money property
data class Player(val name: String, var money: Int = 1500)

// Tile model for board tiles (not used in this example, but kept for consistency)
data class Tile(val name: String)