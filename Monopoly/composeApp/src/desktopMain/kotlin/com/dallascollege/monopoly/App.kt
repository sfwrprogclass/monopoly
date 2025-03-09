package com.dallascollege.monopoly

import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.ui.TokenSelectionScreen
import com.dallascollege.monopoly.ui.PlayerSelectionScreen
import com.dallascollege.monopoly.ui.TurnOrderScreen
import com.dallascollege.monopoly.ui.MenuScreen
import com.dallascollege.monopoly.ui.layout.Layout

@Composable
fun App() {
    var showMenu by remember { mutableStateOf(true) }
    var playerCount by remember { mutableStateOf<Int?>(null) }
    val players = remember { mutableStateListOf<Player>() }
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
                players.addAll(List(count) { Player(name = "Player ${it + 1}", token = Token.TOP_HAT) })
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

            val gameBoard = GameBoard(players.toTypedArray())
            gameBoard.createModels()

            Column(
                modifier = Modifier.fillMaxSize().padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Layout(gameBoard)
            }
        }
    }
}

