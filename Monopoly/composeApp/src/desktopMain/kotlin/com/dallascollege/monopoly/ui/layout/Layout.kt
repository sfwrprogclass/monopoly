package com.dallascollege.monopoly.ui.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.ui.dashboard.GameBoardView

@Composable
fun Layout() {
    Row(modifier = Modifier.fillMaxSize()) {
        // Left Side: Board with Cells
        Card(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .padding(10.dp),
            shape = RoundedCornerShape(0.dp),
            elevation = 4.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                GameBoardView()
            }
        }

        // Right Side: Empty area for menus and more
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(10.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 4.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEEEEEE)) // Light gray background for menu area
            )
        }
    }
}