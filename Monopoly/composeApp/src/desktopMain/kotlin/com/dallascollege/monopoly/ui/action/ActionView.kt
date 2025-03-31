package com.dallascollege.monopoly.ui.action

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallascollege.monopoly.enums.ActionType
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun ActionView(gameBoard: GameBoard, playerId: Int, modifier: Modifier = Modifier) {

    var selectedActionType by remember { mutableStateOf(ActionType.SKIP)}
    var quantity by remember { mutableStateOf("0") }
    var amount by remember { mutableStateOf("0") }

    val player = gameBoard.players.find { it.id == playerId }

    fun executeAction(): Unit {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp) // Adds spacing between rows
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Select action")
            ActionTypeDropDownMenu()
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Quantity", modifier =  Modifier.padding(horizontal = 5.dp))
            OutlinedTextField(
                value = quantity,
                enabled = false,
                onValueChange = { quantity = it },
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                singleLine = true,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Money", modifier =  Modifier.padding(horizontal = 5.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                singleLine = true,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Select property")
            ActionTypeDropDownMenu()
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { executeAction() }) {
                Text("Execute action")
            }
        }
    }
}

