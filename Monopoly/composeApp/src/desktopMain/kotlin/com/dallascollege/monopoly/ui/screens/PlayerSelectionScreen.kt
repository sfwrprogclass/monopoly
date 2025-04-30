package com.dallascollege.monopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlayerSelectionScreen(onPlayerSelected: (Int, Boolean) -> Unit) {
    var selectedPlayers by remember { mutableStateOf<Int?>(null) }
    var automatedPlayersEnabled by remember { mutableStateOf(false) } // <-- added this


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
            Text("How Many Players?", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))


            Column(verticalArrangement = Arrangement.spacedBy(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                (2..6 step 2).forEach { num ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Button(
                            onClick = { selectedPlayers = num },
                            modifier = Modifier.size(120.dp, 70.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedPlayers == num) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                            )
                        ) {
                            Text(num.toString(), color = MaterialTheme.colors.onPrimary, fontSize = 20.sp)
                        }

                        Button(
                            onClick = { selectedPlayers = num + 1 },
                            modifier = Modifier.size(120.dp, 70.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedPlayers == num + 1) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                            )
                        ) {
                            Text((num + 1).toString(), color = MaterialTheme.colors.onPrimary, fontSize = 20.sp)
                        }
                    }
                }


                Button(
                    onClick = { selectedPlayers = 8 },
                    modifier = Modifier.size(120.dp, 70.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedPlayers == 8) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                    )
                ) {
                    Text("8", color = MaterialTheme.colors.onPrimary, fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ai toggle button
            Button(
                onClick = { automatedPlayersEnabled = !automatedPlayersEnabled },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    if (automatedPlayersEnabled) "Automated Opponents: ON" else "Automated Opponents: OFF",
                    fontSize = 18.sp
                )
            }

            Button(
                onClick = { selectedPlayers?.let { onPlayerSelected(it, automatedPlayersEnabled) } },
                enabled = selectedPlayers != null
            ) {
                Text("Next", fontSize = 20.sp)
            }
        }
    }
}

