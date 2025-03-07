package com.dallascollege.monopoly

import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Tile
import com.dallascollege.monopoly.ui.TokenSelectionScreen
import com.dallascollege.monopoly.ui.dice.DiceRoller
import com.dallascollege.monopoly.ui.PlayerSelectionScreen
import com.dallascollege.monopoly.ui.TurnOrderScreen
import com.dallascollege.monopoly.ui.MenuScreen

@Composable
fun App() {
    var showMenu by remember { mutableStateOf(true) }
    var playerCount by remember { mutableStateOf<Int?>(null) }
    val players = remember { mutableStateListOf<Player>() }
    val tiles = remember { mutableStateListOf<Tile>() }
    var allTokensSelected by remember { mutableStateOf(false) }
    val selectedTokens = remember { mutableStateMapOf<Player, String>() }
    var turnOrderConfirmed by remember { mutableStateOf(false) }
    var turnOrder by remember { mutableStateOf<List<String>>(emptyList()) }

    when {
        showMenu -> {
            MenuScreen { showMenu = false }
        }
        playerCount == null -> {
            PlayerSelectionScreen { count ->
                playerCount = count
                players.clear()
                players.addAll(List(count) { Player("Player ${it + 1}") })
            }
        }
        !allTokensSelected -> {
            TokenSelectionScreen(players) { player, token ->
                selectedTokens[player] = token
                if (selectedTokens.size == players.size) {
                    allTokensSelected = true
                }
            }
        }
        !turnOrderConfirmed -> {
            TurnOrderScreen(
                playerTokens = selectedTokens.values.toList(),
                onNextClicked = { finalOrder ->
                    turnOrder = finalOrder
                    turnOrderConfirmed = true
                }
            )
        }
        else -> {
            val currentPlayer = turnOrder.firstOrNull() ?: "Player"

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("$currentPlayer, roll the dice!", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* dice roll */ }) {
                    DiceRoller()
                }
            }
        }
    }
}


