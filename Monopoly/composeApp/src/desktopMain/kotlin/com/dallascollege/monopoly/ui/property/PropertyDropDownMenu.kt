package com.dallascollege.monopoly.ui.property

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
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Property

@Composable
fun PropertyDropDownMenu(player: Player, board: GameBoard, isSelectedPropertyEnable: Boolean, handlePropertyChange: (Property) -> Unit) {
    val properties = player.getProperties(board)
    var expanded by remember { mutableStateOf(false) }
    var selectedProperty by remember { mutableStateOf(properties?.getOrNull(0)) }

    fun handleClick(property: Property) {
        selectedProperty = property
        expanded = false
        handlePropertyChange.invoke(selectedProperty!!)
    }

    Box {
        Button(
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 5.dp),
            shape = RoundedCornerShape(2.dp),
            onClick = { expanded = true },
            enabled = isSelectedPropertyEnable
        ) {
            Text(
                text = selectedProperty?.name ?: "",
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
            properties
                .forEach({
                    DropdownMenuItem(
                        content = { Text(it.name) },
                        modifier = Modifier.background(if (it == selectedProperty) selectedColor else unselectedColor),
                        onClick = { handleClick(it) }
                    )
                })
        }
    }
}