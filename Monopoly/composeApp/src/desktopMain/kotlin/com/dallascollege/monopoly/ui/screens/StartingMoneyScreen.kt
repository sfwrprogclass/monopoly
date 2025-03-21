package com.dallascollege.monopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.Player

@Composable
fun StartingMoneyScreen(players: List<Player>, onNextClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD1A7E5))
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 90.dp)
        ) {

            val topPlayers = players.take(4)
            val bottomPlayers = players.drop(4)


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                topPlayers.forEach { player ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .border(2.dp, Color(0xFF4B0082), RoundedCornerShape(8.dp))
                            .background(Color(0xFF4B0082), RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val moneyIcon = painterResource("images/MoneyStack.png")

                            Image(
                                painter = moneyIcon,
                                contentDescription = "Money Stack Icon",
                                modifier = Modifier.size(200.dp) // Adjust image size if needed
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${player.name}: $${player.totalMoney}",
                                color = Color.White
                            )
                        }
                    }
                }
            }


            if (bottomPlayers.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    bottomPlayers.forEach { player ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .border(2.dp, Color(0xFF4B0082), RoundedCornerShape(8.dp))
                                .background(Color(0xFF4B0082), RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val moneyIcon = painterResource("images/MoneyStack.png")

                                Image(
                                    painter = moneyIcon,
                                    contentDescription = "Money Stack Icon",
                                    modifier = Modifier.size(200.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "${player.name}: $${player.totalMoney}",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }


        Button(
            onClick = { onNextClick() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4B0082))
        ) {
            Text("Next", color = Color.White)
        }
    }
}
