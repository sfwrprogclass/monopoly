package com.dallascollege.monopoly.ui.action

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.enums.ActionType

@Composable
fun ActionTypeDropDownMenu(handleActionTypeChange: (ActionType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedActionType by remember { mutableStateOf(ActionType.SKIP)}

    fun handleClick(actionType: ActionType) {
        selectedActionType = actionType
        expanded = false
        handleActionTypeChange(selectedActionType)
    }

    Box {
        Button(
            modifier = Modifier
                .padding(5.dp, 0.dp, 5.dp, 0.dp),
            shape = RoundedCornerShape(2.dp),
            onClick = { expanded = true }
        ) {
            Text(selectedActionType.text)
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "More")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ActionType.entries.forEach({
                DropdownMenuItem(
                    content = { Text(it.text) },
                    modifier = Modifier.background(if (it == selectedActionType) Color.Blue else Color.Yellow),
                    onClick = { handleClick(it) })
            })
        }
    }
}