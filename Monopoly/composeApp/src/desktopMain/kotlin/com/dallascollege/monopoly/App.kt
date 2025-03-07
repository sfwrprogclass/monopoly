package com.dallascollege.monopoly

import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.ui.TokenSelectionScreen
import com.dallascollege.monopoly.ui.dice.DiceRoller

@Composable
fun App() {
    val players = remember { mutableStateListOf(Player("Battleship"), Player("Top hat")) }
    val tiles = remember { mutableStateListOf<Tile>() }
    var allTokensSelected by remember { mutableStateOf(false) }
    val selectedTokens = remember { mutableStateMapOf<Player, String>() }

    if (!allTokensSelected) {
        TokenSelectionScreen(players) { player, token ->
            selectedTokens[player] = token
            if (selectedTokens.size == players.size) {
                allTokensSelected = true
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("All players have selected their tokens!", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* dice roll */ }) {
                DiceRoller()
            }
        }
    }
}
