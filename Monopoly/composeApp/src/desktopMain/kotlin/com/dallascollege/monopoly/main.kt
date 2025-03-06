package com.dallascollege.monopoly
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*



fun main() = application {
    val state = rememberWindowState(
        size = DpSize(800.dp, 650.dp),
        position = WindowPosition(300.dp, 300.dp)
    )
    Window(title = "Monopoly", onCloseRequest = ::exitApplication, state = state) {
        App()
    }
}
