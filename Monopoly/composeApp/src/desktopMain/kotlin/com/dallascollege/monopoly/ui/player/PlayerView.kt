package com.dallascollege.monopoly.ui.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.enums.convertTokenToImageStr
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun PlayerView(gameBoard: GameBoard, playerId: Int, modifier: Modifier = Modifier) {

    val player = gameBoard.players.find { it.id == playerId }

    fun selectPlayer(): Unit {
        gameBoard.selectedPlayerId = playerId
    }

    Box(
        modifier = modifier // Use external modifier
            .padding(2.dp)
        ) {
        Button(
            modifier = Modifier
                .padding(5.dp, 0.dp, 5.dp, 0.dp),
            shape = RoundedCornerShape(2.dp),
            onClick = { selectPlayer() }
        ) {
            if (player != null) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                    ) {
                    Box(
                        modifier = Modifier.weight(0.3f).fillMaxHeight()
                    ) {
                        Image(
                            painter = painterResource("images/${convertTokenToImageStr(player.token)}.png"),
                            contentDescription = "Cell",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit // Ensures the image scales to fill the Box
                        )
                    }
                    Box(
                        modifier = Modifier.weight(0.7f).fillMaxHeight()
                    ) {
                        //UNCOMMENT ONCE THE turnOrder and currentTurn are set so we display * in the player turn
//                        if (gameBoard.turnOrder[gameBoard.currentTurn] == playerId) {
//                            Text(
//                                text = "*",
//                                modifier = Modifier.padding(0.dp,0.dp,2.dp, 0.dp)
//                            )
//                        }

                        Text(player.name)
                    }
                }
            }
        }
    }
}