package com.dallascollege.monopoly.logic

import androidx.compose.runtime.MutableState
import com.dallascollege.monopoly.enums.ActionType
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import kotlin.random.Random
import androidx.compose.runtime.mutableStateOf
import com.dallascollege.monopoly.utils.HOUSE_PRICE_PER_COLOR
import kotlinx.coroutines.delay // <-- added for delays

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
    fun collectRent(board: GameBoard, playerId: Int, message: MutableState<String> =  mutableStateOf("")): String {
        val player = board.getPlayerById(playerId) ?: return ""
        val cell = board.getCellById(player.numCell) ?: return ""
        if (!cell.isProperty()) return ""

        val property = board.getPropertyById(cell.propertyId) ?: return ""
        if (property.isMortgaged) return ""
        val owner = board.getPropertyOwner(property) ?: return ""
        if (owner == player) return ""

        var rent = property.baseRent

        if (owner.hasAllPropertiesByColor(board, property.color)) {
            if (property.numHouses > 0)
                rent *= 5
            else
                rent *= 2
        }

        player.totalMoney -= rent
        owner.totalMoney += rent

        message.value = "${player.name} paid rent of $$rent to ${owner.name}"
        return "paid rent"
    }

    // As a player, I can collect the appropriate rent for utilities based on how many in the set I own.
    fun collectUtilities(board: GameBoard, playerId: Int, message: MutableState<String> =  mutableStateOf("")): String {
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
    fun collectRailroads(board: GameBoard, playerId: Int, message: MutableState<String> =  mutableStateOf("")): String {
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
    private fun earnCentralMoney(board: GameBoard, player: Player) {
        player.totalMoney += board.centralMoney
        board.centralMoney = 0
    }

    fun goToJail(player: Player, message: MutableState<String> =  mutableStateOf("")): String {
        player.inJail = true
        player.numCell = 23
        message.value = "${player.name} was sent to jail!"
        return "jailed"
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

    fun landingAction(board: GameBoard, playerId: Int, message: MutableState<String> = mutableStateOf("")): String {
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
            earnCentralMoney(board, player)
        } else if (cell.isGoToJail) {
            return goToJail(player)
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
        // do nothing if isVisitingJail
        return ""
    }

    private fun setNextTurn(board: GameBoard, currentTurn: MutableState<Int>) {
        currentTurn.value = (currentTurn.value + 1) % board.turnOrder.size
        board.currentTurn = currentTurn.value
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

    // to display notifications message always needs to be passed as a parameter of the function and then
    // the notification will be automatically prompted!
    fun purchaseProperty(board: GameBoard, playerId: Int, message: MutableState<String> = mutableStateOf("")): String {
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

    // MARVELLOUS
    fun buyHouse(
        board: GameBoard,
        playerId: Int,
        propertyId: Int?,
        quantity: Int,
        message: MutableState<String> = mutableStateOf("")
    ): String {

        if (propertyId == null) return ""
        val player = board.getPlayerById(playerId) ?: return ""
        val cell = board.getCellById(player.numCell) ?: return ""
        val landedProperty = board.getPropertyById(cell.propertyId) ?: return ""

        val property = board.getPropertyById(propertyId) ?: return ""

        // calculate estimated price
        val housePrice = HOUSE_PRICE_PER_COLOR[landedProperty.color]
        val estimatedPrice = (housePrice ?: 0) * quantity

        if (property.color != landedProperty.color) {
            message.value = "You can only build houses on properties that match the color you landed on!"
            return ""
        } else if (player.totalMoney < estimatedPrice){
            message.value = "You don't have enough money to build those houses!"
            return ""
        } else {
            player.totalMoney -= estimatedPrice
            val properties = player.getPropertiesByColor(board, landedProperty.color)

            // let's check amount to distribute houses equally
            val housesPerProperty = quantity / properties.size
            var remainder = quantity % properties.size
            // if houserPerProperty is greater than 0 we distribute the houses equally and then the remainder goes to
            // the selected property
            if (housesPerProperty > 0) {
                properties.forEach {
                    it.numHouses = housesPerProperty
                    if (it.id == propertyId) {
                        it.numHouses += remainder
                    }
                }
            } else {
                // if there is not enough houses per every property we distribute houses one by one starting by the
                // selected property
                val index = properties.indexOf(property)
                for (i in index until properties.size) {
                    if (remainder > 0) {
                        properties[i].numHouses += 1
                        remainder--
                    } else {
                        break
                    }
                }
            }
        }
        return "bought houses"
    }

    // testing lines to figure out why there's a unmortgage bug
    fun mortgageProperty(board: GameBoard, playerId: Int, propertyId: Int) {
        val player = board.getPlayerById(playerId) ?: return
        val property = board.getPropertyById(propertyId) ?: return

        println("Attempting to mortgage property: ${property.name}")
        println("Before mortgaging: property.isMortgaged = ${property.isMortgaged}")

        if (board.getPropertyOwner(property) != player) return
        if (property.isMortgaged) return
        if (property.numHouses > 0 || property.numHotels > 0) return

        property.isMortgaged = true
        player.totalMoney += property.price / 2

        println("After mortgaging: property.isMortgaged = ${property.isMortgaged}")
    }

    // INCOMPLETE. TESTING
    fun unmortgageProperty(board: GameBoard, playerId: Int, propertyId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        val property = board.getPropertyById(propertyId) ?: return

        println("Checking unmortgage:")
        println(" - property.isMortgaged = ${property.isMortgaged}")
        println(" - property owner = ${board.getPropertyOwner(property)?.name}")
        println(" - player = ${player.name}")

        if (property.isMortgaged && board.getPropertyOwner(property) == player) {
            val unmortgageCost = (property.price / 2) + ((property.price / 2) / 10)
            println(" - unmortgageCost = $unmortgageCost")
            println(" - player.totalMoney = ${player.totalMoney}")

            if (player.totalMoney >= unmortgageCost) {
                player.totalMoney -= unmortgageCost
                property.isMortgaged = false
                message.value = "${player.name} unmortgaged ${property.name} for $$unmortgageCost"
            } else {
                message.value = "${player.name} does not have enough money to unmortgage ${property.name}"
            }
        }
    }

    //////////////////////////////////

    private fun canPurchaseProperty(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>): Boolean {
        val player = gameBoard.getPlayerById(selectedPlayerId.value) ?: return false
        val cell = gameBoard.getCellById(player.numCell) ?: return false

        return cell.isProperty() && !gameBoard.isPropertyOwned(cell.propertyId)
    }

    //MARVELLOUS
    private fun canBuyHouse(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>): Boolean {
        val player = gameBoard.getPlayerById(selectedPlayerId.value) ?: return false
        val cell = gameBoard.getCellById(player.numCell) ?: return false

        if (cell.isProperty() && !gameBoard.isPropertyOwned(cell.propertyId))
            return false

        val property = gameBoard.getPropertyById(cell.propertyId) ?: return false

        return player.hasAllPropertiesByColor(gameBoard, property.color)
    }

    fun canPerformAction(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>, actionType: ActionType): Boolean {
        return when (actionType) {
            ActionType.PURCHASE_PROPERTY -> canPurchaseProperty(gameBoard, selectedPlayerId)
            ActionType.BUY_HOUSE -> canBuyHouse(gameBoard, selectedPlayerId)
            else -> true
        }
    }

    //////////////////////////////////

    // methods for AI player logic
    suspend fun performAITurn(board: GameBoard, playerId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        executeTurnStep(board, player, message, isHuman = false)
    }

    suspend fun executeTurnStep(board: GameBoard, player: Player, message: MutableState<String>, isHuman: Boolean) {
        val diceRoll = Random.nextInt(1, 7) + Random.nextInt(1, 7)
        message.value = "${player.name} rolled a $diceRoll!"
        if (!isHuman) delay(2500L)

        movePlayer(board, player.id, diceRoll)

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