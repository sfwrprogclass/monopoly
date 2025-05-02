package com.dallascollege.monopoly.logic

import androidx.compose.runtime.MutableState
import com.dallascollege.monopoly.enums.ActionType
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Dice

// Singleton (Static Class) with static methods for the different actions to be executed
object GameEngine {

    fun movePlayer(board: GameBoard, playerId: Int, steps: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val totalCells = board.cells.size

        val oldCell = player.numCell
        player.numCell = ((player.numCell - 1 + steps) % totalCells) + 1

        if (player.numCell < oldCell) {
            collectSalary(player)
        }
    }

    // As a player, I can collect the base rent when someone lands on my property (unless property is mortgaged).
    fun collectRent(board: GameBoard, playerId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        if (!cell.isProperty()) return

        val property = board.getPropertyById(cell.propertyId) ?: return
        if (property.isMortgaged) return
        val owner = board.getPropertyOwner(property) ?: return
        if (owner == player) return

        val rent = if (owner.hasAllPropertiesByColor(board, property.color)) property.baseRent * 2 else property.baseRent

        player.totalMoney -= rent
        owner.totalMoney += rent

        message.value = "${player.name} paid rent of ${property.baseRent} to ${owner.name}"
    }

    // As a player, I can collect the appropriate rent for utilities based on how many in the set I own.
    fun collectUtilities(board: GameBoard, playerId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        if (!cell.isProperty()) return

        val property = board.getPropertyById(cell.propertyId) ?: return
        if (!property.isUtility) return
        val owner = board.getPropertyOwner(property) ?: return

        if (owner == player) return

        val numberOfUtilities = owner.getUtilities(board).size

        val rentToPay = property.baseRent * numberOfUtilities
        player.totalMoney -= rentToPay
        owner.totalMoney += rentToPay
    }

    // As a player, I can collect the appropriate rent for railroads based on how many in the set I own.
    fun collectRailroads(board: GameBoard, playerId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        if (!cell.isProperty()) return

        val property = board.getPropertyById(cell.propertyId) ?: return
        if (!property.isRailRoad) return
        val owner = board.getPropertyOwner(property) ?: return

        if (owner == player) return

        val numberOfRailroads: Int = owner.getRailroads(board).size
        val rentToPay = property.baseRent * numberOfRailroads
        player.totalMoney -= rentToPay
        owner.totalMoney += rentToPay
    }

    //As a player, I can take appropriate action when landing on a non-property space
    private fun earnCentralMoney(board: GameBoard, player: Player) {
        player.totalMoney += board.centralMoney
        board.centralMoney = 0
    }

     fun goToJail(player: Player) {
        player.inJail = true
        player.numCell = 23
    }

    //TODO
    private fun getChance(player: Player) {

    }

    private fun collectSalary(player: Player) {
        player.totalMoney += 200
    }

    //TODO
    private fun getCommunityChest(player: Player) {

    }

    private fun payIncomeTax(player: Player) {
        player.totalMoney -= 150
    }

    private fun payLuxuryTax(player: Player) {
        player.totalMoney -= 200
    }

    fun landingAction(board: GameBoard, playerId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        //if (cell.isProperty()) {
            //collectRent(board, playerId, message)
        if (cell.isProperty()) {
            val property = board.getPropertyByCell(cell) ?: return

            // Check if the property has houses, and collect appropriate rent
            val rent = if (property.houses > 0) {
                player.collectRent(property) // Collect rent based on houses
            } else {
                collectBaseRent(property) // Collect base rent if no houses are present
            }

            message.value = "${player.name} landed on ${property.name} and owes $$rent."
        } else if (cell.isParking) {
            earnCentralMoney(board, player)
        } else if (cell.isGoToJail) {
            goToJail(player)
        } else if (cell.isChance) {
            getChance(player)
        } else if (cell.isCollectSalary) {
            collectSalary(player)
        } else if (cell.isCommunityChest) {
            getCommunityChest(player)
        } else if (cell.isIncomeTax) {
            payIncomeTax(player)
        } else if (cell.isLuxuryTax) {
            payLuxuryTax(player)
        }
        //do nothing if isVisitingJail
    }
    private fun collectBaseRent(property: Property): Int {
        return property.rentBase
    }
    fun buildHouses(player: Player, colorSet: String, totalHouses: Int) {
        if (!player.canBuildHouses(colorSet)) {
            println("${player.name} does not own all properties in the $colorSet set.")
            return
        }

        val properties = player.getPropertiesByColorSet(colorSet)
        val baseHouses = totalHouses / properties.size
        var extraHouses = totalHouses % properties.size

        println("Building $totalHouses houses in $colorSet set.")

        properties.forEach { it.houses += baseHouses }

        while (extraHouses > 0) {
            val chosenProperty = properties.maxByOrNull { it.houses }
            chosenProperty?.houses = chosenProperty.houses + 1
            extraHouses--
        }
    }

    fun executeAction(player: Player, action: String, colorSet: String, totalHouses: Int) {
        when (action) {
            "build_houses" -> buildHouses(player, colorSet, totalHouses)
            else -> println("Invalid action")
        }
    }

    fun finishTurn(board: GameBoard, currentTurn: MutableState<Int>) {
        do {
            setNextTurn(board, currentTurn)
            val currentPlayerId = board.turnOrder[currentTurn.value]
            val player = board.getPlayerById(currentPlayerId)
        } while (player?.isEliminated(board) == true)
    }

    private fun setNextTurn(board: GameBoard, currentTurn: MutableState<Int>) {
        currentTurn.value = (currentTurn.value + 1) % board.turnOrder.size
        board.currentTurn = currentTurn.value
    }

    //to display notifications message always needs to be passed as a parameter of the function and then
    // the notification will be automatically prompted!
    fun purchaseProperty(board: GameBoard, playerId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        val property = board.getPropertyById(cell.propertyId) ?: return
        val isPropertyOwned = board.isPropertyOwned(playerId)

        if (player.totalMoney >= property.price && !isPropertyOwned) {
            player.propertyIds.add(property.id)
            player.totalMoney -= property.price
            message.value = "${player.name} purchased ${property.name}"
        }
    }

    fun mortgageProperty(board: GameBoard, playerId: Int, propertyId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val property = board.getPropertyById(propertyId) ?: return

        if (board.getPropertyOwner(property) != player) return
        if (property.isMortgaged) return
        if (property.numHouses > 0 || property.numHotels > 0) return


        property.isMortgaged = true
        player.totalMoney += property.price / 2
    }

    fun handleTurnWithDice(
        board: GameBoard,
        currentTurn: MutableState<Int>,
        message: MutableState<String>,
        die1: Int? = null,
        die2: Int? = null
    ) {
        val playerId = board.turnOrder[currentTurn.value]
        val player = board.getPlayerById(playerId) ?: return


        val dice = Dice()
        val roll1 = die1 ?: dice.roll()
        val roll2 = die2 ?: dice.roll()
        val total = roll1 + roll2

        if (roll1 == roll2) {
            player.consecutiveDoubles += 1
        } else {
            player.consecutiveDoubles = 0
        }

        if (player.consecutiveDoubles == 3) {
            player.consecutiveDoubles = 0
            goToJail(player)
            finishTurn(board, currentTurn)
            return
        }

        movePlayer(board, playerId, total)
        landingAction(board, playerId, message)
        board.selectedPlayerId = playerId

        // Only finish turn if not doubles
        if (roll1 != roll2) {
            finishTurn(board, currentTurn)
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
            // Add other ActionType cases here as needed
            else -> return true
        }
    }
    fun adminViewPlayerInfo(board: GameBoard, playerId: Int): String {
        val player = board.getPlayerById(playerId) ?: return "Player not found."

        return """
        Player Name: ${player.name}
        Balance: ${player.balance}
        Properties Owned: ${player.properties.map { it.name }}
        Total Houses: ${player.properties.sumOf { it.houses }}
    """.trimIndent()
    }

}