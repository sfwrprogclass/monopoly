package com.dallascollege.monopoly.ui.action

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.enums.ActionType
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun ActionView(gameBoard: GameBoard, playerId: Int, modifier: Modifier = Modifier) {

    var status = remember { mutableStateOf<DropdownMenuState>(DropdownMenuState())}
    var selectedActionType = remember { mutableStateOf(ActionType.SKIP)}

    val player = gameBoard.players.find { it.id == playerId }

    fun executeAction(): Unit {

    }

    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(modifier = Modifier.weight(0.5f).fillMaxHeight()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (player != null) {
                    Text("Player ${player.name}", modifier = Modifier.padding(2.dp))
                }
                Text("Action type")
                Text("Drop-down action types")
                Text("Quantity")
                Text("Textbox for quantity")
                Text("Money")
                Text("TextBox for money")
            }
        }

        Box(modifier = Modifier.weight(0.5f).fillMaxHeight()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Select property")
                Text("Drop-down with available properties")
                Button(
                    onClick = {executeAction()}
                ) {
                    Text("Execute action")
                }
            }
        }
    }
}

