package com.dallascollege.monopoly.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(onPlayClicked: () -> Unit) {
    var showRules by remember { mutableStateOf(false) }

    val background: Painter = painterResource("images/MonopolyBG.png")
    val titleImage: Painter = painterResource("images/TitleText.png")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = background,
            contentDescription = "Monopoly Background",
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = titleImage,
                contentDescription = "Game Title",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(3.5f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onPlayClicked,
                modifier = Modifier.size(250.dp, 90.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Play", fontSize = 30.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { showRules = true },
                modifier = Modifier.size(250.dp, 70.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("How to Play", fontSize = 22.sp)
            }
        }

        if (showRules) {
            MonopolyRulesDialog(onDismiss = { showRules = false })
        }
    }
}

@Composable
fun MonopolyRulesDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("How to Play Monopoly", fontSize = 24.sp) },
        text = {
            Column {
                Text(" Roll the dice and move around the board.", fontSize = 18.sp)
                Text(" Buy properties when you land on them.", fontSize = 18.sp)
                Text(" Pay rent when you land on owned properties.", fontSize = 18.sp)
                Text(" Collect $200 when you pass GO.", fontSize = 18.sp)
                Text(" The last player with money wins!", fontSize = 18.sp)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Got it!", fontSize = 18.sp)
            }
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}
