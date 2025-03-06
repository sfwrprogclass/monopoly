package com.dallascollege.monopoly.ui

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
import com.dallascollege.monopoly.model.Player

@Composable
fun TokenSelectionScreen(players: List<Player>, onTokenSelected: (Player, String) -> Unit) {
    val allTokenImages = listOf(
        "battleship.png",
        "thimble.png",
        "token_boot.png",
        "token_iron.png",
        "token_tap_hat.png",
        "wheelbarrow.png"
    )

    var currentPlayerIndex by remember { mutableStateOf(0) }
    var currentTokenIndex by remember { mutableStateOf(0) }
    val selectedTokens = remember { mutableStateMapOf<Player, String>() }
    val availableTokens = remember { mutableStateListOf(*allTokenImages.toTypedArray()) }
    val currentPlayer = players[currentPlayerIndex]

    // animation for transition
    val fadeAlpha = animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF2C0474)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Player ${currentPlayerIndex + 1}, choose your token",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            currentTokenIndex = (currentTokenIndex - 1 + availableTokens.size) % availableTokens.size
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
                    ) {
                        Text("<", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    Image(
                        painter = painterResource("images/${availableTokens[currentTokenIndex]}"),
                        contentDescription = "Token Image",
                        modifier = Modifier.size(100.dp).alpha(fadeAlpha.value)
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            currentTokenIndex = (currentTokenIndex + 1) % availableTokens.size
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
                    ) {
                        Text(">", color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
                ) {
                    Text("Select Token", color = Color.Black)
                }
            }
        }
    }
}
