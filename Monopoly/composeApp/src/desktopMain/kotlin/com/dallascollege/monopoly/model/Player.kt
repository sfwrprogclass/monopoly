package com.dallascollege.monopoly.model

class Player(val name: String) {
    var position: Int = 0
    var balance: Int = 1500

    fun move(steps: Int, board: Board) {
        position = (position + steps) % board.tiles.size
        println("$name landed on ${board.getTile(position).name}")
    }
}