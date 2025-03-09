package com.dallascollege.monopoly.ui.dice

import androidx.compose.foundation.layout.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.dallascollege.monopoly.model.Dice
import com.dallascollege.monopoly.model.GameBoard


@Composable
@Preview
fun DiceRoller(gameBoard: GameBoard) {
    val dice = remember { Dice() }
    var dice1 by remember { mutableStateOf(1) }
    var dice2 by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DiceView(dice1)
            DiceView(dice2)
            Button(
                onClick = {
                    dice1 = dice.roll()
                    dice2 = dice.roll()
                    gameBoard.dice1 = dice1
                    gameBoard.dice2 = dice2
                }) {
                Text("Roll the dice")
            }
        }
    }
}