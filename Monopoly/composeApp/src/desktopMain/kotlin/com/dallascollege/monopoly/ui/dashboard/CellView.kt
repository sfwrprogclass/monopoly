@file:Suppress("DEPRECATION")

package com.dallascollege.monopoly.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.enums.convertTokenToImageStr

@Composable
fun CellView(gameBoard: GameBoard, isCorner: Boolean, index: Int = 1, isTopOrBottom: Boolean = false, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
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
                painter = painterResource("images/dashboard/cell_${index}.png"),
                contentDescription = "Cell",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            val playersOnThisCell = gameBoard.players.filter { it.numCell == index }
            val tokenSize = 40.dp
            val spacing = 6.dp

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                for ((i, player) in playersOnThisCell.withIndex()) {
                    val offset = calculateOffset(i, playersOnThisCell.size, tokenSize, spacing)
                    Image(
                        painter = painterResource("images/${convertTokenToImageStr(player.token)}.png"),
                        contentDescription = "${player.name} Token",
                        modifier = Modifier
                            .size(tokenSize)
                            .offset(x = offset.x, y = offset.y)
                            .background(Color.White.copy(alpha = 0.85f), shape = CircleShape)
                            .padding(2.dp)
                            .shadow(4.dp, shape = CircleShape)
                    )
                }
            }
        }
    }
}

fun calculateOffset(index: Int, total: Int, size: Dp, spacing: Dp): DpOffset {
    return when (total) {
        1 -> DpOffset(0.dp, 0.dp)
        2 -> DpOffset(x = if (index == 0) -size / 2 else size / 2, y = 0.dp)
        3 -> listOf(
            DpOffset(0.dp, -size / 2),
            DpOffset(-size / 2, size / 2),
            DpOffset(size / 2, size / 2)
        )[index]
        4 -> listOf(
            DpOffset(-size / 2, -size / 2),
            DpOffset(size / 2, -size / 2),
            DpOffset(-size / 2, size / 2),
            DpOffset(size / 2, size / 2)
        )[index]
        5 -> listOf(
            DpOffset(-size, -size / 2),
            DpOffset(size, -size / 2),
            DpOffset(-size, size / 2),
            DpOffset(0.dp, size / 2),
            DpOffset(size, size / 2)
        )[index]
        6 -> listOf(
            DpOffset(-size, -size / 2),
            DpOffset(0.dp, -size / 2),
            DpOffset(size, -size / 2),
            DpOffset(-size, size / 2),
            DpOffset(0.dp, size / 2),
            DpOffset(size, size / 2)
        )[index]
        7 -> listOf(
            DpOffset(-size, -size / 2),
            DpOffset(0.dp, -size / 2),
            DpOffset(size, -size / 2),
            DpOffset(-size * 1.5f, size / 2),
            DpOffset(-size / 2, size / 2),
            DpOffset(size / 2, size / 2),
            DpOffset(size * 1.5f, size / 2)
        )[index]
        8 -> listOf(
            DpOffset(-size * 1.5f, -size / 2),
            DpOffset(-size / 2, -size / 2),
            DpOffset(size / 2, -size / 2),
            DpOffset(size * 1.5f, -size / 2),
            DpOffset(-size * 1.5f, size / 2),
            DpOffset(-size / 2, size / 2),
            DpOffset(size / 2, size / 2),
            DpOffset(size * 1.5f, size / 2)
        )[index]
        else -> DpOffset(0.dp, 0.dp)
    }
}
