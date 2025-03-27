package com.dallascollege.monopoly.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallascollege.monopoly.model.Player

@Composable
fun TokenSelectionScreen(players: List<Player>, onTokenSelected: (Player, String) -> Unit) {

    val allTokenImages = listOf(
        "Battleship",
        "Thimble",
        "Boot",
        "Iron",
        "TopHat",
        "Wheelbarrow",
        "Car",
        "Dog"
    )

    var currentPlayerIndex by remember { mutableStateOf(0) }
    var currentTokenIndex by remember { mutableStateOf(0) }
    val selectedTokens = remember { mutableStateMapOf<Player, String>() }
    val availableTokens = remember { mutableStateListOf(*allTokenImages.toTypedArray()) }
    val currentPlayer = players[currentPlayerIndex]

    val fadeAlpha = animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C0474)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Player ${currentPlayerIndex + 1}, Choose Your Token",
                    fontSize = 28.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            currentTokenIndex = (currentTokenIndex - 1 + availableTokens.size) % availableTokens.size
                        },
                        modifier = Modifier.size(80.dp, 80.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
                    ) {
                        Text("<", color = Color.Black, fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.width(24.dp))

                    Image(
                        painter = painterResource("images/${availableTokens[currentTokenIndex]}.png"),
                        contentDescription = "Token Image",
                        modifier = Modifier.size(150.dp).alpha(fadeAlpha.value)
                    )

                    Spacer(modifier = Modifier.width(24.dp))
                    Button(
                        onClick = {
                            currentTokenIndex = (currentTokenIndex + 1) % availableTokens.size
                        },
                        modifier = Modifier.size(80.dp, 80.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
                    ) {
                        Text(">", color = Color.Black, fontSize = 28.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        val selectedToken = availableTokens[currentTokenIndex]
                        selectedTokens[currentPlayer] = selectedToken
                        onTokenSelected(currentPlayer, selectedToken)

                        availableTokens.remove(selectedToken)

                        if (currentPlayerIndex < players.size - 1) {
                            currentPlayerIndex++
                            currentTokenIndex = 0
                        }
                    },
                    modifier = Modifier.size(200.dp, 80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
                ) {
                    Text("Select Token", color = Color.Black, fontSize = 24.sp)
                }
            }
        }
    }
}
