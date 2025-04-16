package com.dallascollege.monopoly.ui.player

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun playerMoney(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>) {
    //val currentPlayer = gameBoard.players.getOrNull(gameBoard.currentTurn)
    val currentPlayer = gameBoard.getPlayerById(selectedPlayerId.value)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        currentPlayer?.let { player ->
            val startingMoney = 1500
            Text(
                text = "${player.name}: \$${player.totalMoney}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        } ?: Text(
            text = "Waiting for players...",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
