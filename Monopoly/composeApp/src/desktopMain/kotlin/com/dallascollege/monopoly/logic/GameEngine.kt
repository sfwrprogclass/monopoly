package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Property

class GameEngine(private val gameBoard: GameBoard) {
    private var currentPlayerIndex = 0

    /**
     * Rolls two six-sided dice and returns the total.
     */
    fun rollDice(): Int {
        return (1..6).random() + (1..6).random()
    }

    /**
     * Processes the current player's turn, including dice roll, movement,
     * and handling actions specific to the cell they land on.
     */
    internal fun nextTurn() {
        val player = gameBoard.players[currentPlayerIndex]
        val diceRoll = rollDice()
        println("${player.name} rolled a $diceRoll!")

        // Move player on the game board
        player.move(diceRoll, gameBoard)

        // Determine what happens on the cell the player landed on
        val currentCell = gameBoard.cells[player.numCell]

        when {
            currentCell.isGoToJail -> {
                println("${player.name} is sent to jail!")
                player.inJail = true
                player.numCell = gameBoard.cells.firstOrNull { it.isVisitingJail }?.numCell ?: 10 // Default jail cell
            }

            currentCell.propertyId > 0 -> {
                val property = gameBoard.properties.firstOrNull { it.id == currentCell.propertyId }

                if (property != null) {
                    handleProperty(player, property)
                }
            }

            currentCell.isIncomeTax -> {
                val tax = (player.money * 0.1).toInt()
                player.deductMoney(tax)
                gameBoard.centralMoney += tax
                println("${player.name} paid $tax in income tax!")
            }

            currentCell.isLuxuryTax -> {
                player.deductMoney(75) // Default luxury tax amount
                gameBoard.centralMoney += 75
                println("${player.name} paid 75 in luxury tax!")
            }

            currentCell.isCollectSalary -> {
                player.addMoney(200) // Collect $200 for passing 'Go'
                println("${player.name} collected $200 for passing Go!")
            }

            else -> println("${player.name} landed on a regular cell.")
        }

        // Proceed to the next player
        currentPlayerIndex = (currentPlayerIndex + 1) % gameBoard.players.size
    }

    /**
     * Handles actions when a player lands on a property cell.
     *
     * @param player The player landing on the property.
     * @param property The property landed on.
     */
    private fun handleProperty(player: Player, property: Property) {
        when {
            !property.isPurchased -> {
                println("${player.name} landed on ${property.name}. This property is available for purchase at \$${property.price}.")
                // Simulate player decision to buy the property (for simplicity)
                if (player.money >= property.price) {
                    property.purchase(player)
                    println("${player.name} purchased ${property.name}!")
                } else {
                    println("${player.name} cannot afford ${property.name}.")
                }
            }

            property.owner == player -> {
                println("${player.name} landed on their own property: ${property.name}.")
            }

            property.owner != null -> {
                val rent = property.chargeRent(player)
                println("${player.name} paid \$${rent} in rent to ${property.owner?.name}.")
            }
        }
    }
}