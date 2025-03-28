package com.dallascollege.monopoly.ui.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.ui.action.ActionArea
import com.dallascollege.monopoly.ui.dashboard.GameBoardView
import com.dallascollege.monopoly.ui.dice.DiceRoller
import com.dallascollege.monopoly.ui.player.PlayersListView
import com.dallascollege.monopoly.ui.property.PropertyListView
import com.dallascollege.monopoly.ui.player.playerMoney

@Composable
fun Layout(gameBoard: GameBoard) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Left Side: Board with Cells
        Card(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .padding(10.dp),
            shape = RoundedCornerShape(0.dp),
            elevation = 4.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.9f),
                contentAlignment = Alignment.Center
            ) {
                GameBoardView(gameBoard)
            }
        }

        // Right Side: Sidebar for Player Info
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(10.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxWidth()
                        .background(Color(0xFFFFF9C4))
                ) {
                    PlayersListView(gameBoard)
                }

                // Money Display Section
                Box(
                    modifier = Modifier
                        .weight(0.15f)
                        .fillMaxWidth()
                        .background(Color(0xFF98FF98)),
                    contentAlignment = Alignment.Center
                ) {
                    playerMoney(gameBoard)
                }

                Box(
                    modifier = Modifier
                        .weight(0.37f)
                        .fillMaxWidth()
                        .background(Color(0xFFFFF9C4))
                ) {
                    ActionArea(gameBoard, gameBoard.selectedPlayerId)
                }
                Box(
                    modifier = Modifier
                        .weight(0.27f)
                        .fillMaxWidth()
                        .background(Color(0xFFFFF9C4))
                ) {
                    PropertyListView(gameBoard)
                }
                Box(
                    modifier = Modifier
                        .weight(0.11f)
                        .fillMaxWidth()
                        .background(Color(0XFF98FF98))
                ) {
                    DiceRoller(gameBoard)
                }
            }
        }
    }
}
