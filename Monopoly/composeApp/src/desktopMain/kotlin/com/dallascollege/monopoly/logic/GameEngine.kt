package com.dallascollege.monopoly.logic

import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.MutableState
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Dice
import com.dallascollege.monopoly.ui.SnackbarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
    fun collectBaseRent(board: GameBoard, playerId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        if (!cell.isProperty()) return

        val property = board.getPropertyById(cell.propertyId) ?: return
        if (property.isMortgaged) return
        val owner = board.getPropertyOwner(property) ?: return
        if (owner == player) return

        player.totalMoney -= property.baseRent
        owner.totalMoney += property.baseRent

        CoroutineScope(Dispatchers.Main).launch{
            SnackbarManager.showMessage(
                message = "${player.name} paid rent of ${property.baseRent} to ${owner.name}",
                duration = SnackbarDuration.Short
            )
        }
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

    fun landingAction(board: GameBoard, playerId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        if (cell.isProperty()) {
            collectBaseRent(board, playerId)
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

    fun finishTurn(board: GameBoard, currentTurn: MutableState<Int>) {
        // Set the next player's turn
        currentTurn.value = (currentTurn.value + 1) % board.turnOrder.size

        // Optionally: Any other logic to handle the end of the player's turn
    }


    private fun setNextTurn(board: GameBoard, currentTurn: MutableState<Int>) {
        currentTurn.value = (currentTurn.value + 1) % board.turnOrder.size
        board.currentTurn = currentTurn.value
    }

    fun purchaseProperty(board: GameBoard, playerId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        val property = board.getPropertyById(cell.propertyId) ?: return

        if (player.totalMoney >= property.price) {
            player.propertyIds.add(property.id)
            player.totalMoney -= property.price
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
        landingAction(board, playerId)
        board.selectedPlayerId = playerId

        // Only finish turn if not doubles
        if (roll1 != roll2) {
            finishTurn(board, currentTurn)
        }
    }

}