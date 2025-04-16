package com.dallascollege.monopoly.ui.property

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun PropertyListView(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>) {

    val player = gameBoard.getPlayerById(selectedPlayerId.value)
    val properties = player?.getProperties(gameBoard) ?: emptyArray()

    Column(modifier = Modifier.fillMaxSize()) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(2.dp)
//                .weight(0.2f)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
//                    Text("PROPERTIES", modifier = Modifier.padding(5.dp))
//                }
//            }
//        }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .weight(0.8f)
            ) {
                if (properties.isNotEmpty()) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(properties) { property ->
                            PropertyView(property)
                        }
                    }
                } else {
                    Text("No properties")
                }
            }
        //}
    }
}