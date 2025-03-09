package com.dallascollege.monopoly

import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.ui.TokenSelectionScreen
import com.dallascollege.monopoly.ui.layout.Layout

@Composable
fun App() {
    val players = remember {
        mutableStateListOf(
            Player(name = "Battleship", token = Token.BATTLESHIP),
            Player(name = "Top hat", token = Token.TOP_HAT))
    }
    var allTokensSelected by remember { mutableStateOf(false) }
    val selectedTokens = remember { mutableStateMapOf<Player, String>() }

    if (!allTokensSelected) {
        TokenSelectionScreen(players) { player, token ->
            selectedTokens[player] = token
            if (selectedTokens.size == players.size) {
                allTokensSelected = true
            }
        }
    } else {
        val gameBoard = GameBoard(players.toTypedArray())
        gameBoard.createModels()

        Column(
            modifier = Modifier.fillMaxSize().padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Layout(gameBoard)
        }
    }
}
