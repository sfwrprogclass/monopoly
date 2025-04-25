package com.dallascollege.monopoly.ui.dice


import androidx.compose.foundation.layout.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.logic.GameEngine
import com.dallascollege.monopoly.model.Dice
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.ui.SnackbarManager
import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
@Preview
fun DiceRoller(gameBoard: GameBoard, currentTurn: MutableState<Int>) {
    val dice = remember { Dice() }
    var dice1 by remember { mutableStateOf(1) }
    var dice2 by remember { mutableStateOf(1) }
    var hasRolled by remember { mutableStateOf(false) }


    // Snackbar host state to manage snackbars
    val snackbarHostState = remember { SnackbarHostState() }


    // Reset roll status on turn change
    LaunchedEffect(currentTurn.value) {
        hasRolled = false
    }


    // Listening for the emitted Snackbar message
    LaunchedEffect(key1 = true) {
        SnackbarManager.messages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }


    fun rollDice() {
        dice1 = dice.roll()
        dice2 = dice.roll()


        val total = dice1 + dice2
        val currentPlayerId = gameBoard.turnOrder[currentTurn.value]
        val player = gameBoard.getPlayerById(currentPlayerId) ?: return


        // Doubles tracking
        if (dice1 == dice2) {
            player.consecutiveDoubles += 1
        } else {
            player.consecutiveDoubles = 0
        }


        // Check for 3 consecutive doubles
        if (player.consecutiveDoubles == 3) {
            CoroutineScope(Dispatchers.Main).launch {
                SnackbarManager.showMessage(
                    message = "${player.name} rolled three doubles! Go to jail.",
                    duration = SnackbarDuration.Short
                )
            }
            GameEngine.goToJail(player) // Send player to jail
            player.consecutiveDoubles = 0
            hasRolled = true
            GameEngine.finishTurn(gameBoard, currentTurn) // End turn after going to jail
            return
        }


        GameEngine.movePlayer(gameBoard, currentPlayerId, total)
        GameEngine.landingAction(gameBoard, currentPlayerId)


        gameBoard.selectedPlayerId = currentPlayerId


        // Only end the turn if it's not a double
        hasRolled = dice1 != dice2


        // If it's a double, show the "roll again" message
        if (dice1 == dice2) {
            CoroutineScope(Dispatchers.Main).launch {
                SnackbarManager.showMessage(
                    message = "${player.name} rolled doubles! Roll again.",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DiceView(dice1)
            DiceView(dice2)
            Button(onClick = { rollDice() }, enabled = !hasRolled) {
                Text("Roll the dice")
            }
        }


        // Display the Snackbar using SnackbarHostState
        SnackbarHost(hostState = snackbarHostState)
    }
}
