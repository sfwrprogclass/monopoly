package com.dallascollege.monopoly.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.dallascollege.monopoly.model.Dice
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player

@Composable
fun GameBoardView(gameBoard: GameBoard) {

    var i = 21

    Column(modifier = Modifier.fillMaxSize()) {
        // First row (15%) for Action Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.15f)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.weight(0.14f)) {
                    CellView(gameBoard, isCorner = true, index = i)
                }

                repeat(9) {
                    i++
                    Box(modifier = Modifier.weight(0.08f)) {
                        CellView(gameBoard, isCorner = false, isTopOrBottom = true, index = i)
                    }
                }
                i++

                Box(modifier = Modifier.weight(0.14f)) {  // Top-right corner
                    CellView(gameBoard, isCorner = true, index = i)
                }
            }
        }

        // Second row (70%)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.70f)
        ) {
            // Content for the second row
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                // First Column (Left Side)
                Box(
                    modifier = Modifier
                        .weight(0.142f) // Controls the height of this section in the parent layout
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize() // Fills the Box's entire size
                    ) {
                        i = 21
                        repeat(9) {
                            i--
                            CellView(
                                gameBoard,
                                isCorner = false,
                                modifier = Modifier.weight(0.11f), // Makes all cells have equal height
                                index = i
                            )
                        }
                    }
                }

                // Central Area (Middle)
                Box(
                    modifier = Modifier
                        .weight(0.72f)
                ) {
                    CentralAreaView(gameBoard)
                }

                // Second Column (Right Side)
                Box(
                    modifier = Modifier
                        .weight(0.14f) // Controls the height of this section in the parent layout
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize() // Fills the Box's entire size
                    ) {
                        i = 31
                        repeat(9) {
                            i++
                            CellView(
                                gameBoard,
                                isCorner = false,
                                modifier = Modifier.weight(0.11f), // Makes all cells have equal height
                                index = i
                            )
                        }
                    }
                }
            }
        }

        // Third row (15%)
        i = 11

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.15f)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.weight(0.14f)) {
                    CellView(gameBoard, isCorner = true, index = i)
                }

                repeat(9) {
                    i--
                    Box(modifier = Modifier.weight(0.08f)) {
                        CellView(gameBoard, isCorner = false, isTopOrBottom = true, index = i)
                    }
                }
                i--

                Box(modifier = Modifier.weight(0.14f)) {  // Top-right corner
                    CellView(gameBoard, isCorner = true, index = i)
                }
            }
        }
    }
}
