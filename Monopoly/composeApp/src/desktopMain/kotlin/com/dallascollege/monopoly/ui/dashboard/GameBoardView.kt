package com.dallascollege.monopoly.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.enums.convertTokenToImageStr
import com.dallascollege.monopoly.logic.GameBoard
import com.dallascollege.monopoly.model.Player


@Composable
fun GameBoardView(gameBoard: GameBoard) {
    val cellSize = 60.dp
    BoxWithConstraints {
        val cells = gameBoard.cells
        var i: Int = 0 // Declare variable in a scope that spans the required usages
        cells.forEach { cell ->
            val playersOnCell = gameBoard.players.filter { it.numCell == cell.numCell }

            Box(
                modifier = Modifier
                    .size(cellSize)
                    .background(
                        color = if (playersOnCell.isNotEmpty()) Color.Cyan
                        else Color.LightGray
                    )
            ) {
                playersOnCell.forEach { player ->
                    Image(
                        painter = painterResource(
                            "images/${convertTokenToImageStr(player.token)}.png"
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            // First row (15%)
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
                        CellView(gameBoard, isCorner = true, index = 21)
                    }

                    repeat(9) { j ->
                        val tempIndex = i + j + 1
                        Box(modifier = Modifier.weight(0.08f)) {
                            CellView(gameBoard, isCorner = false, isTopOrBottom = true, index = tempIndex)
                        }
                    }
                    i += 9
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

            @Composable
            fun RowWithCells(
                startIndex: Int,
                cellCount: Int,
                gameBoard: GameBoard,
                isHorizontal: Boolean,
                cellSize: androidx.compose.ui.unit.Dp,
                playersByCell: Map<Int, List<Player>>,
                reverse: Boolean = false
            ) {
                val indices =
                    if (reverse) (startIndex downTo (startIndex - cellCount + 1)) else (startIndex..(startIndex + cellCount - 1))

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

        @Composable
        fun RenderPlayers(players: List<Player>, cellSize: androidx.compose.ui.unit.Dp = 24.dp) {
            players.forEach { player ->
                // Ensure player.token converts to a valid image resource path
                val imageRes = "images/${convertTokenToImageStr(player.token)}.png"

                // Dynamically place the player using offsets (default to (0,0) if offsets are not defined)
                val offsetX = player.xOffset.dp  // Ensure xOffset is defined; use default 0 if not
                val offsetY = player.yOffset.dp  // Ensure yOffset is defined; use default 0 if not

                // Render the image for each player
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = "Player Token: ${player.name}",
                    modifier = Modifier
                        .size(cellSize) // Set the token size
                        .offset(x = offsetX, y = offsetY) // Position the token
                )
            }
        }
    }
}