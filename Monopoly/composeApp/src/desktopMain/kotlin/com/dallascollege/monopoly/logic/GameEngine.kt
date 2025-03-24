package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.model.*

//make it static class with static methods for the different actions to be executed
object GameEngine {

    fun movePlayer(board: GameBoard, playerId: Int, steps: Int) {
        board.getPlayerById(playerId).let { player ->
            if (player != null) {
                player.numCell += steps;
            }
        }
    }

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

    fun collectUtilities(board: GameBoard, playerId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        if (!cell.isProperty()) return

        val property = board.getPropertyById(cell.propertyId) ?: return
        if (!property.isUtility) return
        val owner = board.getPropertyOwner(property) ?: return

        if (owner == player) return

        val numberOfUtilities: Int = player.getUtilities(board).size

        player.totalMoney -= property.baseRent * numberOfUtilities
        owner.totalMoney += property.baseRent * numberOfUtilities
    }

    fun collectRailroads(board: GameBoard, playerId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val cell = board.getCellById(player.numCell) ?: return
        if (!cell.isProperty()) return

        val property = board.getPropertyById(cell.propertyId) ?: return
        if (!property.isRailRoad) return
        val owner = board.getPropertyOwner(property) ?: return

        if (owner == player) return

        val numberOfRailroads: Int = player.getRailroads(board).size

        player.totalMoney -= property.baseRent * numberOfRailroads
        owner.totalMoney += property.baseRent * numberOfRailroads
    }

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
        player.totalMoney -= 100
    }

    private fun payLuxuryTax(player: Player) {
        player.totalMoney -= 100
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
