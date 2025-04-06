package com.dallascollege.monopoly.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.dallascollege.monopoly.logic.GameBoard

@Composable
fun CentralAreaView(gameBoard: GameBoard) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                painter = painterResource("images/dashboard/central_area.png"),
                contentDescription = "Central Area",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Ensures the image scales to fill the Box
            )
        }
    }
}