package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.model.*

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

    // As a player, I can collect the appropriate rent for utilities based on how many in the set I own.
    fun collectBaseRent(board: GameBoard, playerId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        if (!cell.isProperty()) return

        val property = board.getPropertyById(cell.propertyId) ?: return
        val owner = board.getPropertyOwner(property) ?: return
        if (owner == player) return

        player.totalMoney -= property.baseRent
        owner.totalMoney += property.baseRent
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

//        val numberOfUtilities: Int = player.getUtilities(board).size
        val numberOfUtilities = owner.getUtilities(board).size

        val rentToPay = property.baseRent * numberOfUtilities
        player.totalMoney -= rentToPay
        owner.totalMoney += rentToPay

//        player.totalMoney -= property.baseRent * numberOfUtilities
//        owner.totalMoney += property.baseRent * numberOfUtilities
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

    private fun goToJail(player: Player) {
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
}
