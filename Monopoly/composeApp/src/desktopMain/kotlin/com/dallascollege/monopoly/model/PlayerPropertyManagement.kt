package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.model.Player.*
import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.logic.GameBoard


/**
 * Contains property management utility functions for player interactions.
 * Handles actions such as buying, building, mortgaging, and trading properties.
 */
class PlayerPropertyManagement(private val notificationService: NotificationService) {

    companion object {
        private const val HOUSE = "House"
        private const val HOTEL = "Hotel"
    }

    /**
     * Utility to display a notification message.
     */
    private fun notify(message: String) {
        notificationService.sendNotification(message)
    }

    /**
     * Checks if the player owns the property.
     */
    private fun Property.isOwnedBy(player: Player): Boolean {
        return if (this.owner != player) {
            notify("${player.name} does not own ${this.name}.")
            false
        } else {
            true
        }
    }

    /**
     * Prompts the player to decide whether to buy a property.
     */
    fun promptBuyDecision(player: Player, property: Property, onDecision: (Boolean) -> Unit) {
        if (!property.isPurchased && player.totalMoney >= property.price) {
            notify("${player.name}, do you want to buy ${property.name} for \$${property.price}?")
            onDecision(true)
        } else {
            notify("${player.name} cannot buy ${property.name} (insufficient funds or already purchased).")
            onDecision(false)
        }
    }

    /**
     * Builds an improvement (house or hotel) on a property.
     */
    private fun Property.buildImprovement(
        player: Player,
        improvementType: String,
        buildAction: () -> Boolean,
        improvementPrice: Int
    ) {
        if (!this.isOwnedBy(player)) return

        if (player.totalMoney < improvementPrice) {
            notify("${player.name} does not have enough money to build a $improvementType on ${this.name}.")
            return
        }

        if (buildAction()) {
            player.totalMoney -= improvementPrice
            notify("${player.name} built a $improvementType on ${this.name}.")
        } else {
            notify("Cannot build a $improvementType on ${this.name}. Check conditions.")
        }
    }

    /**
     * Builds a house on a property.
     */
    fun buildHouse(player: Player, property: Property, gameBoard: GameBoard) {
        property.buildImprovement(player, HOUSE, { property.buildHouse(gameBoard) }, property.getHousePrice())
    }

    /**
     * Builds a hotel on a property.
     */
    fun buildHotel(player: Player, property: Property, gameBoard: GameBoard) {
        property.buildImprovement(player, HOTEL, { property.buildHotel(gameBoard) }, property.getHotelPrice())
    }

    /**
     * Mortgages a property for the player.
     */
    fun mortgageProperty(player: Player, property: Property) {
        if (property.isMortgaged) {
            notify("${property.name} is already mortgaged.")
            return
        }

        if (property.mortgage()) {
            notify("${player.name} has mortgaged ${property.name} and received \$${property.price / 2}.")
        } else {
            notify("Failed to mortgage ${property.name}.")
        }
    }

    /**
     * Lifts the mortgage from a property, applying a 10% interest fee.
     */
    fun liftMortgage(player: Player, property: Property) {
        if (!property.isMortgaged) {
            notify("${property.name} is not currently mortgaged.")
            return
        }

        if (property.unmortgage()) {
            notify("${player.name} has lifted the mortgage on ${property.name}.")
        } else {
            notify("${player.name} could not afford to lift the mortgage on ${property.name}.")
        }
    }

    /**
     * Trades properties between two players.
     */
    fun tradeProperties(player1: Player, player2: Player, properties1: List<Property>, properties2: List<Property>, gameboard: GameBoard) {
        val arePropertiesValid = properties1.all { it.owner == player1 } && properties2.all { it.owner == player2 }

        if (!arePropertiesValid) {
            notify("Trade failed due to invalid property ownership.")
            return
        }

        properties1.forEach { transferProperty(it, player2, gameboard) }
        properties2.forEach { transferProperty(it, player1, gameboard) }

        notify("Trade completed between ${player1.name} and ${player2.name}.")
    }

    /**
     * Auctions a property among eligible players.
     */
    fun auctionProperty(property: Property, players: List<Player>) {
        val bidders = players.filter { it.totalMoney > property.price }
        if (bidders.isEmpty()) {
            notify("No players can afford to bid on ${property.name}.")
            return
        }

        val winner = bidders.maxByOrNull { it.totalMoney } ?: return

        if (property.purchase(winner, GameBoard(players.toTypedArray()))) {
            notify("${winner.name} has won the auction and purchased ${property.name} for \$${property.price}.")
        } else {
            notify("${winner.name} failed to complete the auction purchase of ${property.name}.")
        }
    }

    /**
     * Handles bankruptcy, transferring all a player's assets to a creditor.
     */
    fun handleBankruptcy(player: Player, creditor: Player, gameboard: GameBoard) {
        notify("${player.name} is bankrupt! Transferring all assets to ${creditor.name}.")

        // Transfer all properties owned by the bankrupt player
        player.getPropertyIds().forEach { propertyId ->
            val property = gameboard.getPropertyById(propertyId)
            if (property != null) {
                transferProperty(property, creditor, gameboard)
            }
        }

        // Set the bankrupt player's money to zero
        player.totalMoney = 0
    }

    /**
     * Checks if a player has a monopoly for a given color group.
     */
    fun checkMonopoly(player: Player, colorGroup: PropertyColor, gameboard: GameBoard): Boolean {
        val requiredGroupSize = colorGroup.propertiesInGroup
        val ownedPropertiesInGroup = gameboard.properties.filter {
            it.owner == player && it.color == colorGroup && !it.isMortgaged
        }

        return ownedPropertiesInGroup.size == requiredGroupSize
    }

    /**
     * Transfers ownership of a property to a new owner.
     */
    fun transferProperty(property: Property, newOwner: Player, gameboard: GameBoard) {
        property.owner?.let { oldOwner: Player ->
            oldOwner.removeProperty(property.id, gameboard)
        }
        newOwner.addProperty(property.id, gameboard)
        property.owner = newOwner

        notify("${property.name} has been transferred to ${newOwner.name}.")
    }
}

/**
 * Notification service to decouple messages from console output.
 */
interface NotificationService {
    fun sendNotification(message: String)
}