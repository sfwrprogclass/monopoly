package com.dallascollege.monopoly

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.logic.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.ui.layout.Layout
import com.dallascollege.monopoly.ui.screens.MenuScreen
import com.dallascollege.monopoly.ui.screens.PlayerSelectionScreen
import com.dallascollege.monopoly.ui.screens.TokenSelectionScreen
import com.dallascollege.monopoly.ui.screens.TurnOrderScreen

@Composable
fun App() {
    // App state variables
    var showMenu by remember { mutableStateOf(true) }
    var playerCount by remember { mutableStateOf<Int?>(null) }
    var players by remember { mutableStateOf<List<Player>>(emptyList()) }

    // Tokens and turn order state
    val selectedTokens = remember { mutableStateMapOf<Player, Token>() }
    var turnOrder by remember { mutableStateOf<List<String>>(emptyList()) }
    val allTokensSelected by remember {
        derivedStateOf { selectedTokens.size == players.size && players.isNotEmpty() }
    }
    var turnOrderConfirmed by remember { mutableStateOf(false) }

    when {
        // Step 1: Show menu
        showMenu -> {
            MenuScreen { showMenu = false }
        }

        // Step 2: Player selection screen
        playerCount == null -> {
            PlayerSelectionScreen { count ->
                playerCount = count
                players = List(count) { index ->
                    val token = Token.values().getOrNull(index) ?: Token.BOOT
                    Player(id = index + 1, name = "Player ${index + 1}", token = token)
                }
            }
        }

        // Step 3: Token selection screen
        !allTokensSelected -> {
            TokenSelectionScreen(players) { player, token ->
                try {
                    // Ensure token validity
                    val fixedToken = token.uppercase().replace(" ", "")
                    selectedTokens[player] = Token.valueOf(fixedToken)
                } catch (e: IllegalArgumentException) {
                    // Optional: Provide feedback for invalid token input
                    println("Invalid token selected: $token")
                }
            }
        }

        // Step 4: Turn order selection screen
        !turnOrderConfirmed -> {
            TurnOrderScreen(
                playerTokens = selectedTokens.values.map { it.toString() },
                onNextClicked = { finalOrder ->
                    turnOrder = finalOrder
                    turnOrderConfirmed = true
                }
            )
        }

        // Step 5: Main game setup and UI rendering
        else -> {
            // Update player list with selected tokens and sort by turn order
            players = players.map { player ->
                // Assign selected tokens to players, fallback to defaults
                val token = selectedTokens[player] ?: player.token
                player.copy(token = token)
            }.sortedBy { player ->
                // Sort based on turn order
                turnOrder.indexOf(player.token.toString())
            }.mapIndexed { index, player ->
                // Update player IDs
                player.copy(id = index + 1, name = "Player ${index + 1}")
            }

            // Initialize the game board
            val gameBoard = GameBoard(players.toTypedArray())
            gameBoard.createModels()

            // Render the game layout
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Layout(gameBoard)
            }
        }
    }
}