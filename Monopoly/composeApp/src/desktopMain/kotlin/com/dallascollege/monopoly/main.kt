package com.dallascollege.monopoly
import com.dallascollege.monopoly.ui.GameScreen
import androidx.compose.ui.window.application


//fun main()  {
//    val game = GameEngine()
//    repeat(5) {  // Simulate 5 turns
//        game.nextTurn()
//    }
//}


fun main() {
    androidx.compose.ui.window.application {
        GameScreen(players = listOf(), tiles = listOf()) {
            println("Dice Rolled!")
        }
    }
}