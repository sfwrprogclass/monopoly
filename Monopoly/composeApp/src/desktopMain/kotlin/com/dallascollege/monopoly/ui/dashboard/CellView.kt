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

@Composable
fun CellView(isCorner: Boolean, imageIndex: Int = 1, isTopOrBottom: Boolean = false, modifier: Modifier = Modifier) {
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
                painter = painterResource("images/dashboard/cell_${imageIndex}.png"),
                contentDescription = "Cell",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Ensures the image scales to fill the Box
            )
        }
    }
}
