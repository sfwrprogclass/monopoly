package com.dallascollege.monopoly
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*


fun main() = application {

    val state = rememberWindowState(
        size = DpSize(1150.dp, 750.dp),
        position = WindowPosition(100.dp, 100.dp)
    )
    Window(title = "Monopoly", onCloseRequest = ::exitApplication, state = state, resizable = true) {
        App()
    }
}
