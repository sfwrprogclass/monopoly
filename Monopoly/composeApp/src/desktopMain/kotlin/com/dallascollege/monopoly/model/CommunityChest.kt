package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.logic.GameBoard
import kotlin.random.Random

// A class representing an individual Community Chest card
data class CommunityChestCard(
    val description: String,
    val action: (player: Player, gameBoard: GameBoard) -> Unit // Action to execute
)

class CommunityChest {

    private val cards: MutableList<CommunityChestCard> = mutableListOf()

    init {
        // Initialize all Community Chest cards
        cards.addAll(
            listOf(
                CommunityChestCard(
                    description = "You have won second prize in a beauty contest. Collect $50.",
                    action = { player, _ -> player.addMoney(50) }
                ),
                CommunityChestCard(
                    description = "Bank error in your favor. Collect $200.",
                    action = { player, _ -> player.addMoney(200) }
                ),
                CommunityChestCard(
                    description = "Doctor's fees. Pay $50.",
                    action = { player, _ -> player.deductMoney(50) }
                ),
                CommunityChestCard(
                    description = "Go to Jail. Do not pass Go. Do not collect $200.",
                    action = { player, gameBoard ->
                        val jailCell = gameBoard.cells.find { it.isGoToJail }
                            ?: error("Jail cell not found in the board configuration")
                        player.numCell = jailCell.numCell
                    }
                ),
                CommunityChestCard(
                    description = "Advance to Go. Collect $200.",
                    action = { player, gameBoard ->
                        val goCell = gameBoard.cells.find { it.isCollectSalary }
                            ?: error("Go cell not found in the board configuration")
                        player.numCell = goCell.numCell
                        player.addMoney(200)
                    }
                ),
                CommunityChestCard(
                    description = "Get out of jail free. This card may be kept until needed or sold.",
                    action = { player, _ ->
                        if (!player.hasOutJailCard) {
                            player.hasOutJailCard = true
                            println("${player.name} received a Get Out of Jail Free card.")
                        } else {
                            println("${player.name} already has a Get Out of Jail Free card.")
                        }
                    }
                ),
                CommunityChestCard(
                    description = "Holiday Fund matures. Receive $100.",
                    action = { player, _ -> player.addMoney(100) }
                ),
                CommunityChestCard(
                    description = "Life insurance matures. Collect $100.",
                    action = { player, _ -> player.addMoney(100) }
                ),
                CommunityChestCard(
                    description = "Pay hospital fees of $100.",
                    action = { player, _ -> player.deductMoney(100) }
                ),
                CommunityChestCard(
                    description = "Pay school fees of $150.",
                    action = { player, _ -> player.deductMoney(150) }
                ),
                CommunityChestCard(
                    description = "Receive $25 consultancy fee.",
                    action = { player, _ -> player.addMoney(25) }
                ),
                CommunityChestCard(
                    description = "You inherit $100.",
                    action = { player, _ -> player.addMoney(100) }
                ),
                CommunityChestCard(
                    description = "From sale of stock, you get $50.",
                    action = { player, _ -> player.addMoney(50) }
                ),
                CommunityChestCard(
                    description = "It is your birthday. Collect $10 from every player.",
                    action = { player, gameBoard ->
                        var total = 0
                        gameBoard.players.forEach {
                            if (it != player) {
                                it.deductMoney(10)
                                total += 10
                            }
                        }
                        player.addMoney(total) // Add collected amount to the birthday player
                        println("It's ${player.name}'s birthday! They collected $10 from each player.")
                    }
                ),
                CommunityChestCard(
                    description = "You are assessed for street repair. Pay $40 per house and $115 per hotel.",
                    action = { player, _ ->
                        val houseFee = 40
                        val hotelFee = 115
                        val totalFee = player.properties.sumOf {
                            (it.numHouses ?: 0) * houseFee + if ((it.numHotels ?: 0) > 0) hotelFee else 0
                        }

                        if (player.money >= totalFee) {
                            player.deductMoney(totalFee)
                        } else {
                            // Handle insufficient funds
                            val remainingMoney = player.money
                            player.deductMoney(remainingMoney)
                            println("${player.name} couldn't fully pay the fees and is now bankrupt!")
                        }
                    }
                )
            )
        )
    }

    // Draw and execute a card for a player
    fun drawCard(player: Player, gameBoard: GameBoard) {
        if (cards.isEmpty()) {
            println("No Community Chest cards available.")
            return
        }

        // Randomly select a card
        val card = cards[Random.nextInt(cards.size)]

        // Print the card description
        println("Community Chest: ${card.description}")

        // Execute the card's action
        card.action(player, gameBoard)
    }

    // Shuffle the deck (optional functionality for better randomness)
    fun shuffleDeck() {
        cards.shuffle()
    }
}