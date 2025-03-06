package com.dallascollege.monopoly
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlin.random.Random

// Basic Automated Monopoly Game version 0.1

// Data class for a Property
data class Property(val name: String, val cost: Int, var owner: Player? = null, val rent: Int)

// Class representing a Player
class Player(val name: String, var money: Int = 1500) {
    var position: Int = 0
    var isBankrupt: Boolean = false

    fun move(steps: Int, boardSize: Int) {
        position = (position + steps) % boardSize
    }

    fun buyProperty(property: Property) {
        if (money >= property.cost && property.owner == null) {
            money -= property.cost
            property.owner = this
            println("$name bought ${property.name} for $${property.cost}")
        } else {
            println("$name cannot buy ${property.name}")
        }
    }

    fun payRent(property: Property) {
        if (property.owner != null && property.owner != this) {
            println("$name landed on ${property.name}, owned by ${property.owner!!.name}")
            if (money >= property.rent) {
                money -= property.rent
                property.owner!!.money += property.rent
                println("$name paid $${property.rent} in rent to ${property.owner!!.name}")
            } else {
                println("$name is bankrupt!")
                isBankrupt = true
            }
        }
    }
}

// Main game class
class MonopolyGame(val players: List<Player>, val properties: List<Property>) {
    var turn = 0

    fun playTurn() {
        val player = players[turn % players.size]
        if (player.isBankrupt) {
            turn++
            return
        }

        val roll = Random.nextInt(1, 7) + Random.nextInt(1, 7) // Rolling two dice
        println("${player.name} rolled a $roll")
        player.move(roll, properties.size)

        val landedProperty = properties[player.position]
        println("${player.name} landed on ${landedProperty.name}")

        if (landedProperty.owner == null) {
            player.buyProperty(landedProperty)
        } else {
            player.payRent(landedProperty)
        }

        turn++
    }

    fun isGameOver(): Boolean {
        return players.count { !it.isBankrupt } <= 1
    }
}

// Setting up a simple game
fun main() {
    val properties = listOf(
        Property("Park Place", 350, rent = 50),
        Property("Boardwalk", 400, rent = 60),
        Property("Baltic Avenue", 60, rent = 10),
        Property("Mediterranean Avenue", 50, rent = 5)
    )

    val players = listOf(Player("Alice"), Player("Bob"))
    val game = MonopolyGame(players, properties)

    while (!game.isGameOver()) {
        game.playTurn()
    }

    val winner = players.maxByOrNull { it.money }
    println("Game Over! Winner is ${winner?.name} with \$${winner?.money}")
}
