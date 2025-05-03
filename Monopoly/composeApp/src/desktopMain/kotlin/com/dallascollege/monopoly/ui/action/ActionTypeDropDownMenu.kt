package com.dallascollege.monopoly.ui.action

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallascollege.monopoly.enums.ActionType
import com.dallascollege.monopoly.logic.GameEngine
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun ActionTypeDropDownMenu(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>, isReadOnly: Boolean, handleActionTypeChange: (ActionType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedActionType by remember { mutableStateOf(ActionType.SKIP)}
    val excludedActionTypes = listOf( ActionType.PAY_RENT, ActionType.PAY_BANK, ActionType.GO_TO_JAIL)

    fun handleClick(actionType: ActionType) {
        selectedActionType = actionType
        expanded = false
        handleActionTypeChange(selectedActionType)
    }

    Box {
        Button(
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 5.dp),
            shape = RoundedCornerShape(2.dp),
            onClick = { expanded = true },
            enabled = !isReadOnly
        ) {
            Text(
                text = selectedActionType.text,
                style = LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                ),
            )
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "More")
        }
        val selectedColor = Color(0xFF90CAF9)
        val unselectedColor = Color(0xFFFFC1E3)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ActionType.entries
                .filter {it !in excludedActionTypes}
                .forEach({
                    DropdownMenuItem(
                        enabled = GameEngine.canPerformAction(gameBoard, selectedPlayerId, it), //
                        content = { Text(it.text) },
                        modifier = Modifier.background(if (it == selectedActionType) selectedColor else unselectedColor),
                        onClick = { handleClick(it) }
                    )
                })
        }
    }
}