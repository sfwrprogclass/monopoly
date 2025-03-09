package com.dallascollege.monopoly.ui.property

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.GameBoard

@Composable
fun PropertyListView(gameBoard: GameBoard) {

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .weight(0.2f)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                    Text("PROPERTIES", modifier = Modifier.padding(5.dp))
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .weight(0.8f)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(modifier = Modifier.weight(0.5f).fillMaxHeight().padding(2.dp)) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        //TO FILL WITH PropertyViews
                        Text("TO fill with PropertyViews of player's properties")
                    }
                }

                Box(modifier = Modifier.weight(0.5f).fillMaxHeight().padding(2.dp)) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        //TO FILL WITH PropertyViews
                        Text("TO fill with PropertyViews of player's properties")
                    }
                }
            }
        }
    }
}