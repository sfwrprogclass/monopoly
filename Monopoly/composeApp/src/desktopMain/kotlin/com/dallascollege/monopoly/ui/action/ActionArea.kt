package com.dallascollege.monopoly.ui.action

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import com.dallascollege.monopoly.ui.dashboard.CellView
import com.dallascollege.monopoly.ui.dashboard.CentralAreaView
import com.dallascollege.monopoly.ui.player.PlayerView

@Composable
fun ActionArea(gameBoard: GameBoard, playerId: Int) {

    Column(modifier = Modifier.fillMaxSize()) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(1.dp)
//                .weight(0.1f)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
//                    Text("ACTIONS", modifier = Modifier.padding(1.dp))
//                }
//            }
//        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp)
                .weight(1f)
        ) {
            ActionView(gameBoard, playerId)
        }
    }
}