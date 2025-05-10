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
import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.ui.property.PropertyDropDownMenu
import kotlinx.coroutines.launch // <-- added for coroutine launching
import java.lang.Integer.parseInt

@Composable
fun ActionView(
    board: GameBoard,
    playerId: Int,
    currentTurn: MutableState<Int>,
    selectedPlayerId: MutableState<Int>,
    message: MutableState<String>,
    modifier: Modifier = Modifier
) {
    var selectedActionType by remember { mutableStateOf(ActionType.FINISH_TURN) }
    var selectedPropertyId: Int? by remember { mutableStateOf(null) } // <-- now tracking ID only
    var quantity by remember { mutableStateOf("0") }
    var amount by remember { mutableStateOf("0") }
    var isQuantityEnable by remember { mutableStateOf(false) }
    var isAmountEnable by remember { mutableStateOf(false) }
    var isSelectedPropertyEnabled by remember { mutableStateOf(false) }
    var isReadOnly = playerId != selectedPlayerId.value

    val coroutineScope = rememberCoroutineScope()

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
            ActionType.MORTGAGE_PROPERTY, ActionType.UNMORTGAGE_PROPERTY -> { // <-- also enables for unmortgage
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
        selectedPropertyId = property.id // <-- only saving ID now
    }

    suspend fun executeAction() {
        println("Selected action: $selectedActionType, Selected property id: $selectedPropertyId")

        when (selectedActionType) {
            ActionType.UPGRADE_TO_HOTEL -> {}
            ActionType.BUY_HOUSE -> {
                GameEngine.buyHouse(board, playerId, selectedPropertyId, parseInt(quantity), message)
            }
            ActionType.DOWNGRADE_TO_HOUSES -> {
                GameEngine.downGradeToHouses(board, playerId, selectedPropertyId, parseInt(quantity), message)
            }
            ActionType.SELL_HOUSE -> {// Need to get the Property object to access its color
                selectedPropertyId?.let { propertyId ->
                    board.getPropertyById(propertyId)?.let { property ->
                        GameEngine.sellHouse(board, playerId, property.color, parseInt(quantity), message)

                    }
                }
            }
            ActionType.GET_OUT_OF_JAIL -> { GameEngine.getOutOfJailUsingCard(board, playerId, message)}
            ActionType.MORTGAGE_PROPERTY -> selectedPropertyId?.let { propertyId ->
                board.getPropertyById(propertyId)?.let { liveProperty ->
                    GameEngine.mortgageProperty(board, playerId, liveProperty.id)
                }
            } //TESTING
            ActionType.UNMORTGAGE_PROPERTY -> selectedPropertyId?.let { propertyId ->
                board.getPropertyById(propertyId)?.let { liveProperty ->
                    GameEngine.unmortgageProperty(board, playerId, liveProperty.id, message)
                }
            }
            ActionType.PURCHASE_PROPERTY -> GameEngine.purchaseProperty(board, playerId, message)
            ActionType.AUCTION_PROPERTY -> {
                GameEngine.auctionProperty(board, playerId, message)
            }
            ActionType.SURRENDER -> {}
            ActionType.FINISH_TURN -> GameEngine.finishTurn(board, currentTurn, message)
            else -> {}
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
            ActionTypeDropDownMenu(board, selectedPlayerId, isReadOnly) { actionType ->
                handleActionTypeChange(
                    actionType
                )
            }
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
                PropertyDropDownMenu(player, board, isSelectedPropertyEnabled && !isReadOnly) { property ->
                    handlePropertyChange(property)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        executeAction()
                    }
                },
                enabled = !isReadOnly && GameEngine.canPerformAction(board, selectedPlayerId, selectedActionType)
            ) {
                Text("Execute action")
            }
        }
    }
}