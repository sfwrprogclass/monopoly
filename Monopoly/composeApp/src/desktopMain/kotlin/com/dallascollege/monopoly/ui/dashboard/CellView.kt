package com.dallascollege.monopoly.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun CellView(gameBoard: GameBoard, isCorner: Boolean, index: Int = 1, isTopOrBottom: Boolean = false, modifier: Modifier = Modifier) {

    //val cell = gameBoard.cells[index]
    // we can use the list of players in gameboard and see if a player is in this cell
    // that way we can display the tokens in each cell

    Card(
        modifier = modifier // Use external modifier
            .padding(0.dp),
        shape = RoundedCornerShape(0.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            Image(
                painter = painterResource("images/dashboard/cell_${index}.png"),
                contentDescription = "Cell",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Ensures the image scales to fill the Box
            )
        }
    }
}
