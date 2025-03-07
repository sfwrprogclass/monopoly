package com.dallascollege.monopoly.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TurnOrderScreen(playerTokens: List<String>, onNextClicked: (List<String>) -> Unit) {
    var shuffledOrder by remember { mutableStateOf(playerTokens.shuffled()) }

    fun getNewShuffledOrder(): List<String> {
        var newOrder: List<String>
        do {
            newOrder = playerTokens.shuffled()
        } while (newOrder == shuffledOrder)
        return newOrder
    }

    val background: Painter = painterResource("images/MonopolyBG.png")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = background,
            contentDescription = "Monopoly Background",
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Players will take their turns in the following order.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))


            shuffledOrder.forEachIndexed { index, token ->
                Text(
                    text = "${index + 1}. $token",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { shuffledOrder = getNewShuffledOrder() },
                modifier = Modifier.size(250.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Re-Randomize", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onNextClicked(shuffledOrder) },
                modifier = Modifier.size(250.dp, 70.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Next", fontSize = 22.sp)
            }
        }
    }
}
