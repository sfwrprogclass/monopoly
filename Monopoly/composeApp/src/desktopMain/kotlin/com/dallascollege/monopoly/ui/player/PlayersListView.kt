package com.dallascollege.monopoly.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player

@Composable
fun PlayersListView(gameBoard: GameBoard) {

    Text("PLAYERS")

//    var i = 0
//    repeat(8) {
//        Box(
//            modifier = Modifier
//                .size(80.dp)
//                .border(1.dp, Color.Green)
//                .background(Color.White, shape =
//                    RoundedCornerShape(12.dp)
//                ),
//            contentAlignment = Alignment.Center
//        ) {
//            i
//        }
//        i++
//    }
}