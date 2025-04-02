package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor

/**
 * Contains property management utility functions for player interactions
 */
class PlayerPropertyManagement {

    /**
     * Displays a message to the console
     */
    fun notify(message: String) {
        println(message)
    }

    /**
     * Asks the player if they want to buy a property
     *
     * @param player The player who landed on the property
     * @param property The property to purchase
     * @param onDecision Callback that receives the player's decision (true to buy, false to decline)
     */
    fun askPlayerIfTheyWantToBuy(
        player: Player,
        property: Property,
        onDecision: (Boolean) -> Unit
    ) {
        if (!property.isPurchased && player.money >= property.price) {
            println("${player.name}, do you want to buy ${property.name} for ${property.price}?")
            onDecision(true) // Simulate decision logic (replace with actual UI if needed)
        } else {
            onDecision(false)
            notify("${player.name} cannot buy ${property.name}.")
        }
    }

    /**
     * Notifies that a property was purchased
     */
    fun notifyPropertyPurchased(player: Player, property: Property) {
        println("${player.name} has purchased ${property.name} for \$${property.price}.")
    }

    /**
     * Notifies that a player has insufficient funds
     */
    fun notifyInsufficientFunds(player: Player) {
        println("${player.name} does not have enough money for this action.")
    }

    /**
     * Notifies that rent was paid
     */
    fun notifyRentPaid(player: Player, owner: Player, amount: Int) {
        println("${player.name} paid \$${amount} in rent to ${owner.name}.")
    }

    /**
     * Extension function to handle building improvements (houses or hotels)
     */
    fun Property.buildImprovement(
        player: Player,
        improvementType: String,
        buildAction: () -> Boolean,
        getPrice: () -> Int
    ) {
        if (buildAction()) {
            player.deductMoney(getPrice())
            notify("${player.name} built a $improvementType on ${this.name}.")
        } else {
            notify("${player.name} cannot build a $improvementType on ${this.name}.")
        }
    }

    /**
     * Builds a house on this property if possible
     */
    fun buildHouse(player: Player, property: Property) {
        property.buildImprovement(player, "House", { property.buildHouse() }, { property.getHousePrice() })
    }

    /**
     * Builds a hotel on this property if possible
     */
    fun buildHotel(player: Player, property: Property) {
        property.buildImprovement(player, "Hotel", { property.buildHotel() }, { property.getHotelPrice() })
    }

    /**
     * Extension function to handle property mortgage actions
     */
    fun Property.handleMortgage(
        player: Player,
        mortgageAction: () -> Unit,
        actionName: String,
        onMortgage: () -> Unit
    ) {
        if (mortgageAction()) {
            onMortgage()
            notify("${player.name} successfully $actionName ${this.name}.")
        } else {
            notify("${player.name} could not $actionName ${this.name}.")
        }
    }

    /**
     * Mortgages the property if possible
     */
    fun mortgageProperty(player: Player, property: Property) {
        property.handleMortgage(player, { property.mortgage() }, "mortgaged") {
            player.addMoney(property.price / 2)
        }
    }

    /**
     * Lifts the mortgage on the property if possible
     */
    fun liftMortgage(player: Player, property: Property) {
        property.handleMortgage(player, { property.unmortgage() }, "unmortgaged") {
            player.deductMoney((property.price / 2) + (property.price / 10)) // Adds 10% interest
        }
    }

    /**
     * Trades properties between two players
     */
    fun tradeProperties(
        player1: Player,
        player2: Player,
        properties1: List<Property>,
        properties2: List<Property>
    ) {
        properties1.forEach { transferProperty(it, player2) }
        properties2.forEach { transferProperty(it, player1) }

        println("Trade completed between ${player1.name} and ${player2.name}.")
    }

    /**
     * Auctions a property among players
     */
    fun auctionProperty(property: Property, players: List<Player>) {
        val bidders = players.filter { it.money >= property.price }
        if (bidders.isEmpty()) {
            notify("No players can afford to bid on ${property.name}.")
            return
        }

        val winner = bidders.maxByOrNull { it.money }!! // Simulating auction logic
        property.purchase(winner)
        notifyPropertyPurchased(winner, property)
    }

    /**
     * Handles bankruptcy of a player
     */
    fun handleBankruptcy(player: Player, creditor: Player) {
        // Transfer all assets to the creditor and reset player
        notify("${player.name} is bankrupt and transferring assets to ${creditor.name}.")

        player.properties.forEach { transferProperty(it, creditor) }
        player.money = 0
        player.isCPU = true // Potentially mark as inactive
    }

    /**
     * Checks if a player has a monopoly of a given color group
     *
     * @param player The player in question
     * @param colorGroup The color to check
     * @return `true` if the player owns all properties in the color group
     */
    fun checkMonopoly(player: Player, colorGroup: PropertyColor): Boolean {
        val groupSize = PropertyColor.getColorGroupSize(colorGroup)
        val ownedInGroup = player.properties.count { it.color == colorGroup && !it.isMortgaged }
        return ownedInGroup == groupSize
    }

    /**
     * Calculates rent for utilities based on the dice roll
     */
    fun calculateUtilityRent(utility: Property, diceRoll: Int): Int {
        return diceRoll * (if (utility.owner?.getUtilityCount() == 1) 4 else 10)
    }

    /**
     * Calculates rent for railroads
     */
    fun calculateRailroadRent(railroad: Property): Int {
        return 25 * (railroad.owner?.getRailroadCount() ?: 0)
    }

    /**
     * Transfers ownership of a property
     */
    fun transferProperty(property: Property, newOwner: Player) {
        property.owner?.let { previousOwner ->
            previousOwner._properties.remove(property)
        }
        newOwner._properties.add(property)
        property.owner = newOwner
        println("${property.name} transferred to ${newOwner.name}.")
    }

    /**
     * Checks and displays property details
     */
    fun checkPropertyStatus(property: Property) {
        val ownerText = property.owner?.name ?: "Bank"
        notify("Property: ${property.name}, Price: ${property.price}, Owned by: $ownerText.")
    }
}