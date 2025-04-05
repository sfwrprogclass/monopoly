package com.dallascollege.monopoly.ui.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallascollege.monopoly.model.GameBoard

// Extract text style for reuse
private val playerInfoTextStyle = TextStyle(
    fontSize = 22.sp,
    fontWeight = FontWeight.Bold,
    color = Color.Black
)

@Composable
fun PlayerMoneyView(gameBoard: GameBoard) {
    val currentPlayer = gameBoard.players.getOrNull(gameBoard.currentTurn)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        currentPlayer?.let { player ->
            Text(
                text = "${player.name}: \$${player.money}",
                style = playerInfoTextStyle
            )
        }
    }
}