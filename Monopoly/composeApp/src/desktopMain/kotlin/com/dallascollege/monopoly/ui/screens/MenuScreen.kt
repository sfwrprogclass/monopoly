package com.dallascollege.monopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(onPlayClicked: (freeParkingRule: Boolean) -> Unit) {
    var showRules by remember { mutableStateOf(false) }
    var freeParkingRule by remember { mutableStateOf(false) }

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
                onClick = { onPlayClicked(freeParkingRule) },
                modifier = Modifier.size(250.dp, 90.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Play", fontSize = 30.sp)
            }

//            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(250.dp, 35.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 2.dp, vertical = 2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = freeParkingRule,
                        onCheckedChange = {
                            freeParkingRule = it
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.DarkGray
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Free parking Rule", fontSize = 14.sp, color = Color.Black)
                }
            }


//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Checkbox(
//                    checked = freeParkingRule,
//                    onCheckedChange = {
//                        freeParkingRule = it
//                    }
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Free parking Rule")
//            }


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
                Text(" 🅿️ Free Parking Rule: ⬇️", fontSize = 20.sp)
                Text(" All collected taxes and fees go to the center of the board. " +
                        "The first player to land on Free Parking wins the pot", fontSize = 18.sp)
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

