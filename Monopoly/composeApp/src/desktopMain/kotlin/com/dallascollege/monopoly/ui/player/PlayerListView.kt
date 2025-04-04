package com.dallascollege.monopoly.ui.player

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun PlayersListView(gameBoard: GameBoard) {

    Column(modifier = Modifier.fillMaxSize()) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(2.dp)
//                .weight(0.2f)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
//                    Text("PLAYERS", modifier = Modifier.padding(5.dp))
//                }
//            }
//        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .weight(0.8f)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(modifier = Modifier.weight(0.5f).fillMaxHeight()) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (gameBoard.players.isNotEmpty()) {
                            PlayerView(gameBoard, 1, Modifier.weight(0.25f))
                        }
                        if (1 < gameBoard.players.size) {
                            PlayerView(gameBoard, 2, Modifier.weight(0.25f))
                        }
                        if (2 < gameBoard.players.size) {
                            PlayerView(gameBoard, 3, Modifier.weight(0.25f))
                        }
                        if (3 < gameBoard.players.size) {
                            PlayerView(gameBoard, 4, Modifier.weight(0.25f))
                        }
                    }
                }

                Box(modifier = Modifier.weight(0.5f)) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        if (4 < gameBoard.players.size) {
                            PlayerView(gameBoard, 5, Modifier.weight(0.25f))
                        }
                        if (5 < gameBoard.players.size) {
                            PlayerView(gameBoard, 6, Modifier.weight(0.25f))
                        }
                        if (6 < gameBoard.players.size) {
                            PlayerView(gameBoard, 7, Modifier.weight(0.25f))
                        }
                        if (7 < gameBoard.players.size) {
                            PlayerView(gameBoard, 8, Modifier.weight(0.25f))
                        }
                    }
                }
            }
        }
    }
}