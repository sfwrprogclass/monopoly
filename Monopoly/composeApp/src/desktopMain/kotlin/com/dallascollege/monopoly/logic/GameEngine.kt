package com.dallascollege.monopoly.logic

import androidx.compose.runtime.MutableState
import com.dallascollege.monopoly.enums.ActionType
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import kotlin.random.Random
import kotlinx.coroutines.delay // <-- added for delays

// Singleton (Static Class) with static methods for the different actions to be executed
object GameEngine {

    fun movePlayer(board: GameBoard, playerId: Int, steps: Int, message: MutableState<String>? = null) {
        val player = board.getPlayerById(playerId) ?: return
        val totalCells = board.cells.size

        val oldCell = player.numCell
        player.numCell = ((player.numCell - 1 + steps) % totalCells) + 1

        if (player.numCell < oldCell) {
            collectSalary(player, message)
        }
    }

    // As a player, I can collect the base rent when someone lands on my property (unless property is mortgaged).
    fun collectRent(board: GameBoard, playerId: Int, message: MutableState<String>): String {
        val player = board.getPlayerById(playerId) ?: return ""
        val cell = board.getCellById(player.numCell) ?: return ""
        if (!cell.isProperty()) return ""

        val property = board.getPropertyById(cell.propertyId) ?: return ""
        if (property.isMortgaged) return ""
        val owner = board.getPropertyOwner(property) ?: return ""
        if (owner == player) return ""

        val rent = if (owner.hasAllPropertiesByColor(board, property.color)) property.baseRent * 2 else property.baseRent

        player.totalMoney -= rent
        owner.totalMoney += rent

        message.value = "${player.name} paid rent of $$rent to ${owner.name}"
        return "paid rent"
    }

    // As a player, I can collect the appropriate rent for utilities based on how many in the set I own.
    fun collectUtilities(board: GameBoard, playerId: Int, message: MutableState<String>): String {
        val player = board.getPlayerById(playerId) ?: return ""
        val cell = board.getCellById(player.numCell) ?: return ""
        if (!cell.isProperty()) return ""

        val property = board.getPropertyById(cell.propertyId) ?: return ""
        if (!property.isUtility) return ""
        val owner = board.getPropertyOwner(property) ?: return ""

        if (owner == player) return ""

        val numberOfUtilities = owner.getUtilities(board).size
        val rentToPay = property.baseRent * numberOfUtilities

        player.totalMoney -= rentToPay
        owner.totalMoney += rentToPay

        message.value = "${player.name} paid $$rentToPay to ${owner.name} for utilities"
        return "paid utility rent"
    }

    // As a player, I can collect the appropriate rent for railroads based on how many in the set I own.
    fun collectRailroads(board: GameBoard, playerId: Int, message: MutableState<String>): String {
        val player = board.getPlayerById(playerId) ?: return ""
        val cell = board.getCellById(player.numCell) ?: return ""
        if (!cell.isProperty()) return ""

        val property = board.getPropertyById(cell.propertyId) ?: return ""
        if (!property.isRailRoad) return ""
        val owner = board.getPropertyOwner(property) ?: return ""

        if (owner == player) return ""

        val numberOfRailroads = owner.getRailroads(board).size
        val rentToPay = property.baseRent * numberOfRailroads

        player.totalMoney -= rentToPay
        owner.totalMoney += rentToPay

        message.value = "${player.name} paid $$rentToPay to ${owner.name} for railroads"
        return "paid railroad rent"
    }

    // As a player, I can take appropriate action when landing on a non-property space
    private fun earnCentralMoney(board: GameBoard, player: Player, message: MutableState<String>) {
        val amount = board.centralMoney
        player.totalMoney += amount
        board.centralMoney = 0
        message.value = "${player.name} collected $${amount} from Free Parking!"
    }

    fun goToJail(player: Player) {
        player.inJail = true
        player.numCell = 23
    }

    //TODO
    private fun getChance(player: Player, message: MutableState<String>? = null) {
        // Implementation will be added in the future
        message?.let {
            it.value = "${player.name} drew a Chance card"
        }
    }

    private fun collectSalary(player: Player, message: MutableState<String>? = null) {
        player.totalMoney += 200
        message?.let {
            it.value = "${player.name} collected $200 salary for passing GO!"
        }
    }

    //TODO
    private fun getCommunityChest(player: Player, message: MutableState<String>? = null) {
        // Implementation will be added in the future
        message?.let {
            it.value = "${player.name} drew a Community Chest card"
        }
    }

    private fun payIncomeTax(player: Player, message: MutableState<String>? = null) {
        player.totalMoney -= 150
        message?.let {
            it.value = "${player.name} paid $150 income tax"
        }
    }

    private fun payLuxuryTax(player: Player, message: MutableState<String>? = null) {
        player.totalMoney -= 200
        message?.let {
            it.value = "${player.name} paid $200 luxury tax"
        }
    }

    fun landingAction(board: GameBoard, playerId: Int, message: MutableState<String>): String {
        val player = board.getPlayerById(playerId) ?: return ""
        val cell = board.getCellById(player.numCell) ?: return ""

        if (cell.isProperty()) {
            val property = board.getPropertyById(cell.propertyId) ?: return ""
            return when {
                property.isUtility -> collectUtilities(board, playerId, message)
                property.isRailRoad -> collectRailroads(board, playerId, message)
                else -> collectRent(board, playerId, message)
            }
        } else if (cell.isParking) {
            earnCentralMoney(board, player, message)
        } else if (cell.isGoToJail) {
            goToJail(player)
            message.value = "${player.name} was sent to jail!"
            return "jailed"
        } else if (cell.isChance) {
            getChance(player, message)
        } else if (cell.isCollectSalary) {
            collectSalary(player, message)
        } else if (cell.isCommunityChest) {
            getCommunityChest(player, message)
        } else if (cell.isIncomeTax) {
            payIncomeTax(player, message)
        } else if (cell.isLuxuryTax) {
            payLuxuryTax(player, message)
        }
        // do nothing if isVisitingJail
        return ""
    }

    suspend fun finishTurn(board: GameBoard, currentTurn: MutableState<Int>, message: MutableState<String>) {
        do {
            setNextTurn(board, currentTurn)
            val currentPlayerId = board.turnOrder[currentTurn.value]
            val player = board.getPlayerById(currentPlayerId)
        } while (player?.isEliminated(board) == true)

        val currentPlayerId = board.turnOrder[currentTurn.value]
        val player = board.getPlayerById(currentPlayerId)

        if (player?.isAI == true) {
            performAITurn(board, player.id, message)
            finishTurn(board, currentTurn, message) // recursively finish again until a human player
        }
    }

    private fun setNextTurn(board: GameBoard, currentTurn: MutableState<Int>) {
        currentTurn.value = (currentTurn.value + 1) % board.turnOrder.size
        board.currentTurn = currentTurn.value
    }

    // to display notifications message always needs to be passed as a parameter of the function and then
    // the notification will be automatically prompted!
    fun purchaseProperty(board: GameBoard, playerId: Int, message: MutableState<String>): String {
        val player = board.getPlayerById(playerId) ?: return ""
        val cell = board.getCellById(player.numCell) ?: return ""
        val property = board.getPropertyById(cell.propertyId) ?: return ""
        val isPropertyOwned = board.isPropertyOwned(cell.propertyId)

        if (player.totalMoney >= property.price && !isPropertyOwned) {
            player.propertyIds.add(property.id)
            player.totalMoney -= property.price
            message.value = "${player.name} purchased ${property.name}"
            return "purchased"
        }
        return ""
    }

    /**
     * Mortgages a property owned by a player.
     * The player receives half the property's price in cash.
     * A property can only be mortgaged if:
     * - It is owned by the player
     * - It is not already mortgaged
     * - It has no houses or hotels
     */
    fun mortgageProperty(board: GameBoard, playerId: Int, propertyId: Int, message: MutableState<String>? = null) {
        val player = board.getPlayerById(playerId) ?: return
        val property = board.getPropertyById(propertyId) ?: return

        // Check if the property can be mortgaged
        if (board.getPropertyOwner(property) != player) return
        if (property.isMortgaged) return
        if (property.numHouses > 0 || property.numHotels > 0) return

        // Calculate mortgage value
        val mortgageValue = property.price / 2

        // Mortgage the property and give the player money
        property.isMortgaged = true
        player.totalMoney += mortgageValue

        // Update message if provided
        message?.let {
            it.value = "${player.name} mortgaged ${property.name} for $$mortgageValue"
        }
    }

    /**
     * Unmortgages a property owned by a player.
     * The player must pay the mortgage value plus 10% interest.
     * A property can only be unmortgaged if:
     * - It is owned by the player
     * - It is currently mortgaged
     * - The player has enough money to pay the unmortgage cost
     */
    fun unmortgageProperty(board: GameBoard, playerId: Int, propertyId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        val property = board.getPropertyById(propertyId) ?: return

        if (property.isMortgaged && board.getPropertyOwner(property) == player) {
            // Calculate unmortgage cost: mortgage value + 10% interest
            val mortgageValue = property.price / 2
            val unmortgageCost = mortgageValue + (mortgageValue / 10)

            if (player.totalMoney >= unmortgageCost) {
                // Player has enough money to unmortgage
                player.totalMoney -= unmortgageCost
                property.isMortgaged = false
                message.value = "${player.name} unmortgaged ${property.name} for $$unmortgageCost"
            } else {
                // Player doesn't have enough money
                message.value = "${player.name} does not have enough money to unmortgage ${property.name}"
            }
        }
    }

    //////////////////////////////////

    fun canPurchaseProperty(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>): Boolean {
        val player = gameBoard.getPlayerById(selectedPlayerId.value) ?: return false
        val cell = gameBoard.getCellById(player.numCell) ?: return false

        return cell.isProperty() && !gameBoard.isPropertyOwned(cell.propertyId)
    }

    fun canPerformAction(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>, actionType: ActionType): Boolean {
        return when (actionType) {
            ActionType.PURCHASE_PROPERTY -> canPurchaseProperty(gameBoard, selectedPlayerId)
            else -> true
        }
    }

    suspend fun performAITurn(board: GameBoard, playerId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        executeTurnStep(board, player, message, isHuman = false)
    }

    suspend fun executeTurnStep(board: GameBoard, player: Player, message: MutableState<String>, isHuman: Boolean) {
        val diceRoll = Random.nextInt(1, 7) + Random.nextInt(1, 7)
        message.value = "${player.name} rolled a $diceRoll!"
        if (!isHuman) delay(2500L)

        movePlayer(board, player.id, diceRoll, message)

        val cell = board.getCellById(player.numCell)
        message.value = "${player.name} landed on ${cell?.getName(board)}."
        if (!isHuman) delay(2500L)

        var actionResult = landingAction(board, player.id, message)

        if (actionResult.isEmpty()) {
            actionResult = purchaseProperty(board, player.id, message)
        }

        if (actionResult.isNotEmpty() && !isHuman) {
            delay(5000L)
        }
    }
}
