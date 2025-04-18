package com.dallascollege.monopoly.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VictoryScreen(winnerName: String, onRestart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎉 $winnerName Wins! 🎉", style = MaterialTheme.typography.h4)
        Spacer(Modifier.height(20.dp))
        Button(onClick = onRestart) {
            Text("Play Again")
        }
    }
}
