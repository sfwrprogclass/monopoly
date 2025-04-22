package com.dallascollege.monopoly.ui.action

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallascollege.monopoly.enums.ActionType
import com.dallascollege.monopoly.logic.GameEngine
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Property
import com.dallascollege.monopoly.ui.property.PropertyDropDownMenu

@Composable
fun ActionView(
    board: GameBoard,
    playerId: Int,
    currentTurn: MutableState<Int>,
    selectedPlayerId: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    var selectedActionType by remember { mutableStateOf(ActionType.SKIP) }
    var selectedProperty: Property? by remember { mutableStateOf(null) }
    var quantity by remember { mutableStateOf("0") }
    var amount by remember { mutableStateOf("0") }
    var isQuantityEnable by remember { mutableStateOf(false) }
    var isAmountEnable by remember { mutableStateOf(false) }
    var isSelectedPropertyEnabled by remember { mutableStateOf(false) }
    var isReadOnly = playerId != selectedPlayerId.value

    val player = board.players.find { it.id == playerId }

    fun handleActionTypeChange(actionType: ActionType) {
        selectedActionType = actionType

        when (actionType) {
            ActionType.PAY_RENT, ActionType.PAY_BANK -> {
                isQuantityEnable = false
                isAmountEnable = false
                isSelectedPropertyEnabled = false
            }
            ActionType.BUY_HOUSE, ActionType.UPGRADE_TO_HOTEL, ActionType.DOWNGRADE_TO_HOUSES, ActionType.SELL_HOUSE -> {
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

    fun executeAction() {
        when (selectedActionType) {
            ActionType.UPGRADE_TO_HOTEL -> {}
            ActionType.BUY_HOUSE -> {}
            ActionType.DOWNGRADE_TO_HOUSES -> {}
            ActionType.SELL_HOUSE -> {}
            ActionType.PAY_RENT -> {}
            ActionType.PAY_BANK -> {}
            ActionType.GO_TO_JAIL -> {}
            ActionType.GET_OUT_OF_JAIL -> {}
            ActionType.MORTGAGE_PROPERTY -> selectedProperty?.let {
                GameEngine.mortgageProperty(board, playerId, it.id)
            }
            ActionType.PURCHASE_PROPERTY -> GameEngine.purchaseProperty(board, playerId)
            ActionType.SURRENDER -> {}
            ActionType.SKIP -> {}
            ActionType.FINISH_TURN -> GameEngine.finishTurn(board, currentTurn)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp, 0.dp, 5.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Select action")
            ActionTypeDropDownMenu(isReadOnly) { actionType -> handleActionTypeChange(actionType) }
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Quantity", modifier = Modifier.width(120.dp).padding(end = 8.dp))
            OutlinedTextField(
                value = quantity,
                enabled = isQuantityEnable && !isReadOnly,
                onValueChange = { quantity = it },
                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp, textAlign = TextAlign.Center),
                modifier = Modifier.weight(1f).height(48.dp),
                singleLine = true
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text("Money", modifier = Modifier.width(120.dp).padding(end = 9.dp))
            OutlinedTextField(
                value = amount,
                enabled = isAmountEnable && !isReadOnly,
                onValueChange = { amount = it },
                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp, textAlign = TextAlign.Center),
                modifier = Modifier.weight(1f).height(48.dp),
                singleLine = true
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Select property")
            if (player != null) {
                PropertyDropDownMenu(player, board, isSelectedPropertyEnabled && !isReadOnly) {
                        property -> handlePropertyChange(property)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { executeAction() },
                enabled = !isReadOnly
            ) {
                Text("Execute action")
            }
        }
    }
}
