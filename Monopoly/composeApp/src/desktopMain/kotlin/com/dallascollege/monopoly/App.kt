package com.dallascollege.monopoly

// Add the following dependency to your Gradle file instead:
// implementation "androidx.compose.ui:ui-tooling:1.x.x"
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.ui.SnackbarManager
import com.dallascollege.monopoly.ui.layout.Layout
import com.dallascollege.monopoly.ui.screens.MenuScreen
import com.dallascollege.monopoly.ui.screens.PlayerSelectionScreen
import com.dallascollege.monopoly.ui.screens.TokenSelectionScreen
import com.dallascollege.monopoly.ui.screens.TurnOrderScreen
import kotlinx.coroutines.launch


@Composable
fun SnackbarHost(){
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        SnackbarManager.messages.collect { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = "OK",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}
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

    when {
        showMenu -> {
            MenuScreen { showMenu = false }
        }
        playerCount == null -> {
            PlayerSelectionScreen { count ->
                playerCount = count
                players.clear()
                players.addAll(List(count) {
                    val token = Token.entries.getOrNull(it) ?: Token.BOOT
                    Player(id = it + 1, name = "Player ${it + 1}", token = token)
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
                }
            )
        }
        else -> {
            players.forEach { player ->
                selectedTokens[player]?.let { player.token = it }
            }

            val sortedPlayers = players.sortedBy { player ->
                turnOrder.indexOf(player.token.toString())
            }

            players.clear()
            players.addAll(sortedPlayers)

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
