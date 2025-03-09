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
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.ui.dashboard.GameBoardView
import com.dallascollege.monopoly.ui.dice.DiceRoller
import com.dallascollege.monopoly.ui.player.PlayersListView

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

        // Right Side: Empty area for menus and more
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
                        .weight(0.15f)
                        .fillMaxWidth()
                        .background(Color(0xFFFFF9C4)) // Light gray background for menu area
                ) {
                    PlayersListView(gameBoard)
                }
                Box(
                    modifier = Modifier
                        .weight(0.42f)
                        .fillMaxWidth()
                        .background(Color(0xFFFFF9C4)) // Light gray background for menu area
                ) {
                    Text("ACTIONS")
                }
                Box(
                    modifier = Modifier
                        .weight(0.32f)
                        .fillMaxWidth()
                        .background(Color(0xFFFFF9C4)) // Light gray background for menu area
                ) {
                    Text("PROPERTY LIST")
                }
                Box(
                    modifier = Modifier
                        .weight(0.11f)
                        .fillMaxWidth()
                        .background(Color(0XFF98FF98)) // Light gray background for menu area
                ) {
                    DiceRoller(gameBoard)
                }
            }
        }
    }
}