package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.Player

// Singleton (Static Class) with static methods for game actions
object GameEngine {

    // Players participating in the game
    val players = listOf(
        Player(id = 1, name = "Battleship", token = Token.BATTLESHIP),
        Player(id = 2, name = "Top Hat", token = Token.TOPHAT)
    )

    // Lazy initialization of the game board using players
    private val board: GameBoard by lazy {
        GameBoard(
            initialPlayers = players.toTypedArray(),
        ).apply {
            createModels()
        }
    }

    // Tracks the current player's index
    private var currentPlayerIndex: Int = 0

    /**
     * Rolls two dice and returns their sum.
     */
    private fun rollDice(): Int {
        return (1..6).random() + (1..6).random()
    }

    /**
     * Process a player's turn.
     */
    internal fun processTurn() {
        val currentPlayer = players[currentPlayerIndex]
        val diceRoll = rollDice()
        println("${currentPlayer.name} rolled a $diceRoll!")
        currentPlayer.numCell = (currentPlayer.numCell + diceRoll) % board.cells.size
        handleLandingAction(currentPlayer)

        // Move to the next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
    }

    /**
     * Handles any action the player needs to take upon landing.
     */
    private fun handleLandingAction(player: Player) {
        landingAction(board, player.id)
    }

    /**
     * Handles what happens when a player lands on a property or special space.
     */
    fun landingAction(board: GameBoard, playerId: Int) {
        val player = board.players.find { it.id == playerId } ?: return
        val cell = board.findCellById(player.numCell) ?: return

        when {
            cell.isProperty() -> collectBaseRent(board, playerId)
            cell.isParking -> earnCentralMoney(board, player)
            cell.isGoToJail -> goToJail(player)
            cell.isChance -> getChance(player)
            else -> println("${player.name} landed on a non-actionable cell.")
        }
    }

    /**
     * Collect rent for properties.
     */
    fun collectBaseRent(board: GameBoard, playerId: Int) {
        val player = board.players.find { it.id == playerId } ?: return
        val cell = board.findCellById(player.numCell) ?: return
        if (!cell.isProperty()) return

        val property = board.findPropertyById(cell.propertyId) ?: return
        val owner = property.owner ?: return

        if (owner.id == playerId) return // No rent to pay if the player owns the property.

        val rent = property.calculateRent(board)
        player.totalMoney = maxOf(0, player.totalMoney - rent)
        owner.totalMoney += rent
    }

    /**
     * Handles the "Go to Jail" action.
     */
    private fun goToJail(player: Player) {
        println("${player.name} is going to jail!")
        player.numCell = board.cells.firstOrNull { it.isGoToJail }?.numCell ?: 0
        player.inJail = true // Assuming inJail flag exists
    }

    /**
     * Gets a random "Chance" card.
     */
    private fun getChance(player: Player) {
        println("${player.name} picked up a Chance card! (Effect TBD)")
        // Implement Chance card logic
    }

    /**
     * Handles the action for Free Parking.
     */
    private fun earnCentralMoney(board: GameBoard, player: Player) {
        player.totalMoney += board.centralMoney
        board.centralMoney = 0
    }

    /**
     * Pay income tax.
     */
    private fun payIncomeTax(player: Player) {
        val taxAmount = 100
        player.totalMoney = maxOf(0, player.totalMoney - taxAmount)
    }

    /**
     * Pay luxury tax.
     */
    private fun payLuxuryTax(player: Player) {
        val taxAmount = 100
        player.totalMoney = maxOf(0, player.totalMoney - taxAmount)
    }
}

private fun <T> Array<T>.firstOrNull(predicate: (T) -> Unit): T? {
    TODO("Not yet implemented")
}
