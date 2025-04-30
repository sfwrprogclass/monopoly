package com.dallascollege.monopoly

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.ui.layout.Layout
import com.dallascollege.monopoly.ui.screens.MenuScreen
import com.dallascollege.monopoly.ui.screens.PlayerSelectionScreen
import com.dallascollege.monopoly.ui.screens.TokenSelectionScreen
import com.dallascollege.monopoly.ui.screens.TurnOrderScreen
import com.dallascollege.monopoly.ui.screens.StartingMoneyScreen

@Composable
fun App() {
    var showMenu by remember { mutableStateOf(true) }
    var playerCount by remember { mutableStateOf<Int?>(null) }
    val players = remember { mutableStateListOf<Player>() }
    var allTokensSelected by remember { mutableStateOf(false) }
    val selectedTokens = remember { mutableStateMapOf<Player, Token>() }
    var turnOrderConfirmed by remember { mutableStateOf(false) }
    var turnOrder by remember { mutableStateOf<List<String>>(emptyList()) }
    val currentTurn = remember { mutableStateOf(0) }
    var showStartingMoneyScreen by remember { mutableStateOf(false) }
    var automatedPlayersEnabled by remember { mutableStateOf(false) }

    when {
        showMenu -> {
            MenuScreen { showMenu = false }
        }
        playerCount == null -> {
            PlayerSelectionScreen { count, automatedEnabled ->
                playerCount = count
                automatedPlayersEnabled = automatedEnabled
                players.clear()
                players.addAll(List(count) { index ->
                    val token = Token.entries.getOrNull(index) ?: Token.BOOT
                    Player(
                        id = index + 1,
                        name = "Player ${index + 1}",
                        token = token,
                        isAI = if (automatedPlayersEnabled) (index != 0) else false
                    )
                })
            }
        }
        !allTokensSelected -> {
            TokenSelectionScreen(players) { player, token ->
                val fixedToken = token.uppercase().replace(" ", "")
                selectedTokens[player] = Token.valueOf(fixedToken)
                if (selectedTokens.size == players.size) {
                    allTokensSelected = true
                }
            }
        }
        !turnOrderConfirmed -> {
            TurnOrderScreen(
                playerTokens = selectedTokens.values.map { it.toString() },
                onNextClicked = { finalOrder ->
                    turnOrder = finalOrder
                    turnOrderConfirmed = true
                    showStartingMoneyScreen = true
                }
            )
        }
        showStartingMoneyScreen -> {
            StartingMoneyScreen(players) {
                showStartingMoneyScreen = false
            }
        }
        else -> {
            players.forEach { player ->
                selectedTokens[player]?.let { player.token = it }
            }

            val sortedPlayers = players.sortedBy { player ->
                turnOrder.indexOf(player.token.toString())
            }

                // If AI is enabled, force human first
            val finalPlayers = if (automatedPlayersEnabled) {
                val humanPlayer = sortedPlayers.find { !it.isAI }
                val aiPlayers = sortedPlayers.filter { it.isAI }

                val finalList = mutableListOf<Player>()
                humanPlayer?.let { finalList.add(it) }
                finalList.addAll(aiPlayers)
                finalList
            } else {
                sortedPlayers
            }

            players.clear()
            players.addAll(finalPlayers)

            players.forEachIndexed { index, player ->
                player.id = index + 1
                player.name = "Player ${index + 1}"
            }

            val gameBoard = GameBoard(players.toTypedArray())
            gameBoard.createModels()
            gameBoard.turnOrder = players.map { it.id }.toTypedArray()
            gameBoard.currentTurn = currentTurn.value

            Column(
                modifier = Modifier.fillMaxSize().padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Layout(gameBoard, currentTurn)
            }
        }
    }
}
