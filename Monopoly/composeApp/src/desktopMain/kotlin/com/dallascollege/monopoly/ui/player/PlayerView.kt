package com.dallascollege.monopoly.ui.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.enums.convertTokenToImageStr
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun PlayerView(gameBoard: GameBoard, playerId: Int, selectedPlayerId: MutableState<Int>, modifier: Modifier = Modifier) {

    val player = gameBoard.players.find { it.id == playerId }

    LaunchedEffect(gameBoard.currentTurn) {
       selectedPlayerId.value = gameBoard.turnOrder[gameBoard.currentTurn]
    }

    val isSelected = playerId == selectedPlayerId.value
    val backgroundColor = if (isSelected) Color(0xFF90CAF9) else Color(0xFFFFC1E3)

    Box(modifier = modifier.padding(2.dp)) {
        Button(
            onClick = {
                selectedPlayerId.value = playerId
                gameBoard.selectedPlayerId = playerId
            },
            shape = RoundedCornerShape(2.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
        ) {
            player?.let {
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .weight(0.3f)
                            .fillMaxHeight()
                    ) {
                        Image(
                            painter = painterResource("images/${convertTokenToImageStr(player.token)}.png"),
                            contentDescription = "Player Token",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = buildString {
                                if (gameBoard.turnOrder[gameBoard.currentTurn] == playerId) append(" * ")
                                append(player.name)
                            }
                        )
                    }
                }
            }
        }
    }
}
