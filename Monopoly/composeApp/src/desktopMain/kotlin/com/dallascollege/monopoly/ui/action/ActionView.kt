package com.dallascollege.monopoly.ui.action

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallascollege.monopoly.enums.ActionType
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Property
import com.dallascollege.monopoly.ui.property.PropertyDropDownMenu

@Composable
fun ActionView(board: GameBoard, playerId: Int, modifier: Modifier = Modifier) {

    var selectedActionType by remember { mutableStateOf(ActionType.SKIP)}
    var selectedProperty: Property? by remember { mutableStateOf(null) }
    var quantity by remember { mutableStateOf("0") }
    var amount by remember { mutableStateOf("0") }
    var isQuantityEnable by remember { mutableStateOf(false)}
    var isAmountEnable by remember { mutableStateOf(false)}
    var isSelectedPropertyEnabled by remember { mutableStateOf(false)}

    val player = board.players.find { it.id == playerId }

    fun handleActionTypeChange(actionType: ActionType){
        selectedActionType = actionType

        when (actionType) {
            ActionType.PAY_RENT, ActionType.PAY_BANK -> {
                isQuantityEnable = false
                isAmountEnable = false
                isSelectedPropertyEnabled = false
            }
            ActionType.BUY_HOUSE, ActionType.BUY_HOTEL, ActionType.SELL_HOTEL, ActionType.SELL_HOUSE -> {
                isQuantityEnable = true
                isAmountEnable = false
                isSelectedPropertyEnabled = true
            }
            ActionType.MORTGAGE_PROPERTY -> {
                isQuantityEnable = false
                isAmountEnable = false
                isSelectedPropertyEnabled = true
            }
            else -> {
                isQuantityEnable = false
                isAmountEnable = false
                isSelectedPropertyEnabled = false
            }
        }
    }

    fun handlePropertyChange(property: Property) {
        selectedProperty = property
    }

    fun executeAction(): Unit {
        when (selectedActionType) {
            ActionType.BUY_HOTEL -> {
                //TODO
            }
            ActionType.BUY_HOUSE -> {
                //TODO
            }
            ActionType.SELL_HOTEL -> {
                //TODO
            }
            ActionType.SELL_HOUSE -> {
                //TODO
            }
            ActionType.PAY_RENT -> {
                //TODO
            }
            ActionType.PAY_BANK -> {
                //TODO
            }
            ActionType.GO_TO_JAIL -> {
                //TODO
            }
            ActionType.GET_OUT_OF_JAIL -> {
                //TODO
            }
            ActionType.MORTGAGE_PROPERTY -> {
                //TODO
            }
            ActionType.PURCHASE_PROPERTY -> {
                //TODO
            }
            ActionType.SURRENDER -> {
                //TODO
            }
            ActionType.SKIP -> {
                //TODO
            }
            ActionType.FINISH_TURN -> {
                //TODO
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp, 0.dp, 5.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp) // Adds spacing between rows
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Select action")
            ActionTypeDropDownMenu() {
                actionType -> handleActionTypeChange(actionType)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Quantity",
                modifier = Modifier
                    .width(120.dp)
                    .padding(end = 8.dp)
            )
            OutlinedTextField(
                value = quantity,
                enabled = isQuantityEnable,
                onValueChange = { quantity = it },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                singleLine = true,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                "Money",
                modifier = Modifier
                    .width(120.dp)
                    .padding(end = 9.dp)
            )
            OutlinedTextField(
                value = amount,
                enabled = isAmountEnable,
                onValueChange = { amount = it },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                ),
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
            if (player != null) {
                PropertyDropDownMenu(player, board, isSelectedPropertyEnabled) {
                    property -> handlePropertyChange(property)
                }
            }
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

