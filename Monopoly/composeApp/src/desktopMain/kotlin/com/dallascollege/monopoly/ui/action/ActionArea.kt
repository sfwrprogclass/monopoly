package com.dallascollege.monopoly.ui.action

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.logic.GameBoard

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