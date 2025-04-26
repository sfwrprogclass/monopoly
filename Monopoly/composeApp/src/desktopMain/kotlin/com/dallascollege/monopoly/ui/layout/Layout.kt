package com.dallascollege.monopoly.ui.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.ui.action.ActionArea
import com.dallascollege.monopoly.ui.dashboard.GameBoardView
import com.dallascollege.monopoly.ui.dice.DiceRoller
import com.dallascollege.monopoly.ui.player.PlayersListView
import com.dallascollege.monopoly.ui.property.PropertyListView
import com.dallascollege.monopoly.ui.player.playerMoney
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Layout(gameBoard: GameBoard, currentTurn: MutableState<Int>) {
    val currentPlayerId = gameBoard.turnOrder.getOrNull(currentTurn.value) ?: return
    val selectedPlayerId = remember { mutableStateOf(gameBoard.selectedPlayerId) }

    //variable to use for notifications
    val message = remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(message.value) {
        val msg = message.value
        if (msg.isNotBlank()) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(msg)
            }
            delay(2000)
            message.value = ""
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    ) {

        Row(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .padding(10.dp),
                shape = RoundedCornerShape(0.dp),
                elevation = 4.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.9f),
                    contentAlignment = Alignment.Center
                ) {
                    GameBoardView(gameBoard)
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .weight(0.25f)
                            .fillMaxWidth()
                            .background(Color(0xFFFFF9C4))
                    ) {
                        PlayersListView(gameBoard, selectedPlayerId)
                    }

                    Box(
                        modifier = Modifier
                            .weight(0.10f)
                            .fillMaxWidth()
                            .background(Color(0xFF98FF98)),
                        contentAlignment = Alignment.Center
                    ) {
                        playerMoney(gameBoard, selectedPlayerId)
                    }

                    Box(
                        modifier = Modifier
                            .weight(0.40f)
                            .fillMaxWidth()
                            .background(Color(0xFFFFF9C4))
                    ) {
                        ActionArea(gameBoard, currentPlayerId, currentTurn, selectedPlayerId, message)
                    }
                    Box(
                        modifier = Modifier
                            .weight(0.24f)
                            .fillMaxWidth()
                            .background(Color(0xFFFFF9C4))
                    ) {
                        PropertyListView(gameBoard, selectedPlayerId)
                    }
                    Box(
                        modifier = Modifier
                            .weight(0.11f)
                            .fillMaxWidth()
                            .background(Color(0XFF98FF98))
                    ) {
                        DiceRoller(gameBoard, currentTurn, message)
                    }
                }
            }
        }
    }
}
