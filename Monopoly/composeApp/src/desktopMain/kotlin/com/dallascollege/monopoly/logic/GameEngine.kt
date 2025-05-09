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
    private fun earnCentralMoney(board: GameBoard, player: Player, message: MutableState<String> =  mutableStateOf("")) {
        player.totalMoney += board.centralMoney
        board.centralMoney = 0
        message.value = "You landed on Free Parking! Collect all the money from the center of the board"
    }

    fun goToJail(player: Player, message: MutableState<String> =  mutableStateOf("")): String {
        player.inJail = true
        player.jailTurnCount = 0
        player.numCell = 23
        message.value = "${player.name} was sent to jail!"
        return "jailed"
    }

    private fun getChance(gameBoard: GameBoard, player: Player, message: MutableState<String> =  mutableStateOf("")) {
        val cards = gameBoard.getChanceCards()
        val chanceCard = cards.random()

        chanceCard.performCardAction(player, message)
    }

    private fun getCommunityChest(gameBoard: GameBoard, player: Player, message: MutableState<String> =  mutableStateOf("")) {
        val cards = gameBoard.getCommunityChestCards()
        val communityChestCard = cards.random()

        communityChestCard.performCardAction(player, message)
    }

    private fun collectSalary(player: Player, message: MutableState<String> =  mutableStateOf("")) {
        player.totalMoney += 200
        message.value = "You passed Go! Collect \$200 salary from the bank"
    }

    private fun payIncomeTax(player: Player, board: GameBoard, message: MutableState<String> =  mutableStateOf("")) {
        player.totalMoney -= 150
        message.value = "You landed on Income Tax. Pay \$200 to the bank!"
        if (player.totalMoney < 0) {
            bankruptToBank(player, board, message)
        }
    }

    private fun payLuxuryTax(player: Player, board: GameBoard, message: MutableState<String>) {
        player.totalMoney -= 200
        message.value = "You landed on Luxury Tax. Pay \$100 for your extravagant lifestyle!"

        if (player.totalMoney < 0) {
            bankruptToBank(player, board, message)
        }
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
            earnCentralMoney(board, player, message)
        } else if (cell.isGoToJail) {
            return goToJail(player)
        } else if (cell.isChance) {
            getChance(board, player, message)
        } else if (cell.isCollectSalary) {
            collectSalary(player, message)
        } else if (cell.isCommunityChest) {
            getCommunityChest(board, player, message)
        } else if (cell.isIncomeTax) {
            payIncomeTax(player, board, message)
        } else if (cell.isLuxuryTax) {
            payLuxuryTax(player, board, message)
        }
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

        if (player?.isAI == true && !player.isEliminated(board)) {
            performAITurn(board, player.id, message)
            finishTurn(board, currentTurn, message)
        }
    }

    fun AuctionProperty(board: GameBoard, playerId: Int, message: MutableState<String>) {
        // not yet completed
    }

    // to display notifications message always needs to be passed as a parameter of the function and then
    // the notification will be automatically prompted!
    fun purchaseProperty(board: GameBoard, playerId: Int, message: MutableState<String> =  mutableStateOf("")): String {
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

    fun getOutOfJailUsingCard(board: GameBoard, playerId: Int, message: MutableState<String> = mutableStateOf("")): String {
        val player = board.getPlayerById(playerId) ?: return ""
        player.hasOutJailCard = false
        player.inJail = false
        message.value = "You are free!"
        return "out of jail"
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
    fun mortgageProperty(board: GameBoard, playerId: Int, propertyId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        val property = board.getPropertyById(propertyId) ?: return

        println("Attempting to mortgage property: ${property.name}")
        println("Before mortgaging: property.isMortgaged = ${property.isMortgaged}")
        println(" - player.id = ${player.id}")
        println(" - property.id = ${property.id}")
        println(" - player.totalMoney before addition = ${player.totalMoney}")

        if (board.getPropertyOwner(property) != player) {
            message.value = "You don't own this property"
            return
        }
        if (property.isMortgaged) {
            message.value = "${property.name} is already mortgaged"
            return
        }
        if (property.numHouses > 0 || property.numHotels > 0) {
            message.value = "You must sell all houses and hotels before mortgaging ${property.name}"
            return
        }

        // Calculate mortgage value
        val mortgageValue = property.price / 2
        println(" - mortgageValue = $mortgageValue")

        // Store the player's money before addition for verification
        val moneyBeforeAddition = player.totalMoney

        // Explicitly add the mortgage value to the player's money
        val newMoney = player.totalMoney + mortgageValue
        player.totalMoney = newMoney

        // Verify the addition
        val moneyAfterAddition = player.totalMoney
        val actualAddition = moneyAfterAddition - moneyBeforeAddition

        println(" - player.totalMoney after addition = ${player.totalMoney}")
        println(" - actual addition = $actualAddition (should be $mortgageValue)")
        println(" - newMoney = $newMoney")

        // Double-check that the money was actually added
        if (player.totalMoney != newMoney) {
            println(" - WARNING: player.totalMoney (${player.totalMoney}) does not match newMoney ($newMoney)!")
            // Force the addition again
            player.totalMoney = newMoney
            println(" - Forced player.totalMoney to $newMoney")
        }

        // Mortgage the property
        property.isMortgaged = true

        // Set success message
        message.value = "${player.name} mortgaged ${property.name} for $$mortgageValue"

        // Final verification
        println(" - Final player.totalMoney = ${player.totalMoney}")
        println(" - Final property.isMortgaged = ${property.isMortgaged}")
    }

    fun unmortgageProperty(board: GameBoard, playerId: Int, propertyId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        val property = board.getPropertyById(propertyId) ?: return

        println("Checking unmortgage:")
        println(" - property.isMortgaged = ${property.isMortgaged}")
        println(" - property owner = ${board.getPropertyOwner(property)?.name}")
        println(" - player = ${player.name}")
        println(" - player.id = ${player.id}")
        println(" - property.id = ${property.id}")

        val propertyOwner = board.getPropertyOwner(property)

        if (propertyOwner != player) {
            message.value = "You don't own this property"
            return
        }

        if (!property.isMortgaged) {
            message.value = "${property.name} is not mortgaged"
            return
        }

        // Calculate unmortgage cost: mortgage value (half the property price) plus 10% interest
        val mortgageValue = property.price / 2
        val interest = mortgageValue / 10
        val unmortgageCost = mortgageValue + interest

        println(" - mortgageValue = $mortgageValue")
        println(" - interest = $interest")
        println(" - unmortgageCost = $unmortgageCost")
        println(" - player.totalMoney before deduction = ${player.totalMoney}")

        if (player.totalMoney >= unmortgageCost) {
            // Store the player's money before deduction for verification
            val moneyBeforeDeduction = player.totalMoney

            // Explicitly deduct the unmortgage cost from the player's money
            val newMoney = player.totalMoney - unmortgageCost
            player.totalMoney = newMoney

            // Verify the deduction
            val moneyAfterDeduction = player.totalMoney
            val actualDeduction = moneyBeforeDeduction - moneyAfterDeduction

            println(" - player.totalMoney after deduction = ${player.totalMoney}")
            println(" - actual deduction = $actualDeduction (should be $unmortgageCost)")
            println(" - newMoney = $newMoney")

            // Double-check that the money was actually deducted
            if (player.totalMoney != newMoney) {
                println(" - WARNING: player.totalMoney (${player.totalMoney}) does not match newMoney ($newMoney)!")
                // Force the deduction again
                player.totalMoney = newMoney
                println(" - Forced player.totalMoney to $newMoney")
            }

            // Unmortgage the property
            property.isMortgaged = false

            message.value = "${player.name} unmortgaged ${property.name} for $$unmortgageCost"

            // Final verification
            println(" - Final player.totalMoney = ${player.totalMoney}")
            println(" - Final property.isMortgaged = ${property.isMortgaged}")
        } else {
            message.value = "${player.name} does not have enough money to unmortgage ${property.name}"
        }
    }

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

    private fun canGetOutOfJail(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>): Boolean {
        val player = gameBoard.getPlayerById(selectedPlayerId.value) ?: return false
        val cell = gameBoard.getCellById(player.numCell) ?: return false

        return cell.isVisitingJail && player.hasOutJailCard
    }

    private fun canMortgageProperty(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>): Boolean {
        val player = gameBoard.getPlayerById(selectedPlayerId.value) ?: return false

        // Check if player owns any properties that can be mortgaged
        return player.propertyIds.any { propertyId ->
            val property = gameBoard.getPropertyById(propertyId) ?: return@any false
            property.isMortgageable() && gameBoard.getPropertyOwner(property) == player
        }
    }

    private fun canUnmortgageProperty(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>): Boolean {
        val player = gameBoard.getPlayerById(selectedPlayerId.value) ?: return false

        // Check if player owns any mortgaged properties and has enough money to unmortgage at least one
        return player.propertyIds.any { propertyId ->
            val property = gameBoard.getPropertyById(propertyId) ?: return@any false
            if (!property.isMortgaged || gameBoard.getPropertyOwner(property) != player) return@any false

            // Calculate unmortgage cost
            val mortgageValue = property.price / 2
            val interest = mortgageValue / 10
            val unmortgageCost = mortgageValue + interest

            player.totalMoney >= unmortgageCost
        }
    }

    fun canPerformAction(gameBoard: GameBoard, selectedPlayerId: MutableState<Int>, actionType: ActionType): Boolean {
        return when (actionType) {
            ActionType.PURCHASE_PROPERTY -> canPurchaseProperty(gameBoard, selectedPlayerId)
            ActionType.BUY_HOUSE -> canBuyHouse(gameBoard, selectedPlayerId)
            ActionType.GET_OUT_OF_JAIL -> canGetOutOfJail(gameBoard, selectedPlayerId)
            ActionType.MORTGAGE_PROPERTY -> canMortgageProperty(gameBoard, selectedPlayerId)
            ActionType.UNMORTGAGE_PROPERTY -> canUnmortgageProperty(gameBoard, selectedPlayerId)
            else -> true
        }
    }

    //////////////////////////////////

    // methods for AI player logic
    suspend fun performAITurn(board: GameBoard, playerId: Int, message: MutableState<String>) {
        val player = board.getPlayerById(playerId) ?: return
        executeTurnStep(board, player, message, isHuman = false)
    }

    // handles human and AI logic for jail rolls
    suspend fun executeTurnStep(board: GameBoard, player: Player, message: MutableState<String>, isHuman: Boolean) {
        if (player.isEliminated(board)) return

        if (player.inJail) {
            if (player.hasOutJailCard) {
                player.hasOutJailCard = false
                player.inJail = false
                message.value = "${player.name} used a Get Out of Jail Free card!"
            } else if (player.wantsToPayJailFee && player.totalMoney >= 50) {
                player.totalMoney -= 50
                player.inJail = false
                message.value = "${player.name} paid $50 to get out of jail!"
            } else {
                val die1 = Random.nextInt(1, 7)
                val die2 = Random.nextInt(1, 7)
                message.value = "${player.name} rolled a $die1 and a $die2."
                if (!isHuman) delay(2500L)

                if (die1 == die2) {
                    player.inJail = false
                    message.value = "${player.name} rolled doubles and escaped jail!"
                    movePlayer(board, player.id, die1 + die2)
                } else {
                    player.jailTurnCount += 1
                    if (player.jailTurnCount >= 3) {
                        player.totalMoney -= 50
                        player.inJail = false
                        message.value = "${player.name} failed 3 rolls and paid $50 to leave jail."
                        movePlayer(board, player.id, die1 + die2)
                    } else {
                        message.value = "${player.name} did not roll doubles and remains in jail (Turn ${player.jailTurnCount}/3)."
                        return
                    }
                }
            }
        } else {
            val diceRoll = Random.nextInt(1, 7) + Random.nextInt(1, 7)
            message.value = "${player.name} rolled a $diceRoll!"
            if (!isHuman) delay(2500L)

            movePlayer(board, player.id, diceRoll)
        }

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

    // lets a player manually try to get out of jail early, before ending turn
    fun getOutOfJail(player: Player, message: MutableState<String>): Boolean {
        return when {
            player.hasOutJailCard -> {
                player.hasOutJailCard = false
                player.inJail = false
                message.value = "${player.name} used a Get Out of Jail Free card!"
                true
            }
            player.totalMoney >= 50 -> {
                player.totalMoney -= 50
                player.inJail = false
                message.value = "${player.name} paid \$50 to get out of jail!"
                true
            }
            else -> {
                message.value = "${player.name} cannot get out of jail yet!"
                false
            }
        }
    }

    fun bankruptToBank(player: Player, board: GameBoard, message: MutableState<String>) {
        player.propertyIds.clear()
        player.isBankrupt = true
        message.value = "${player.name} was eliminated. All assets have been surrendered to the bank."
    }
}
