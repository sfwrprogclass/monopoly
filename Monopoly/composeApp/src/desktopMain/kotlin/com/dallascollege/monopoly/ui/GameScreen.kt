//
//package com.dallascollege.monopoly.ui
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import com.dallascollege.monopoly.model.Tile
//import com.dallascollege.monopoly.model.Player
//
//@Composable
//fun GameScreen(players: List<Player>, tiles: List<Tile>, onRollDice: () -> Unit) {
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Monopoly Game", style = MaterialTheme.typography.headlineMedium)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 📌 Game Board Representation
//        Board(tiles, players)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 📌 Roll Dice Button
//        Button(onClick = onRollDice) {
//            Text("Roll Dice")
//        }
//    }
//}
//
//@Composable
//fun Board(tiles: List<Tile>, players: List<Player>) {
//    Box(
//        modifier = Modifier
//            .size(300.dp)
//            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
//            .background(Color.LightGray)
//    ) {
//        Canvas(modifier = Modifier.fillMaxSize()) {
//            val tileSize = size.width / 10  // Assuming 10 tiles per side (basic representation)
//
//            tiles.forEachIndexed { index, tile ->
//                val x = (index % 10) * tileSize
//                val y = (index / 10) * tileSize
//
//                drawRect(
//                    color = Color.White,
//                    topLeft = androidx.compose.ui.geometry.Offset(x, y),
//                    size = androidx.compose.ui.geometry.Size(tileSize, tileSize)
//                )
//            }
//        }
//    }
//}


package com.dallascollege.monopoly.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.dallascollege.monopoly.model.Player

@Composable
fun GameScreen(players: List<Player>, tiles: List<Tile>, onRollDice: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Monopoly Game", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(16.dp))

        //Game Board Representation
        Board(tiles, players)

        Spacer(modifier = Modifier.height(16.dp))

        // Roll Dice Button
        Button(onClick = onRollDice) {
            Text("Roll Dice")
        }
    }
}

@Composable
fun Board(tiles: List<Tile>, players: List<Player>) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        if (tiles.isEmpty()) {
            Text("No tiles available", color = Color.Red)
        } else {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val tileSize = size.width / 10  // 10 tiles per row (simple board)

                tiles.forEachIndexed { index, tile ->
                    val x = (index % 10) * tileSize
                    val y = (index / 10) * tileSize

                    drawRect(
                        color = Color.White,
                        topLeft = Offset(x, y),
                        size = Size(tileSize, tileSize)
                    )
                }
            }
        }
    }
}
