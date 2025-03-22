package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor

/**
 * Contains property management utility functions for player interactions
 */
class PlayerPropertyManagement{


/**
 * Displays a message to the console
 */
    fun notify(message: String) {
    println(message)
}

/**
 * Asks the player if they want to buy a property
 * @param player The player who landed on the property
 * @param property The property that can be purchased
 * @param onDecision Callback that receives the player's decision (true to buy, false to decline)
 */
    fun askPlayerIfTheyWantToBuy(
    player: Player,
    property: Property,
    onDecision: (Boolean) -> Unit
    ) {
    notify("${player.name}, do you want to buy ${property.name} for $${property.price}?")
    }

/**
 * Notifies that a property was purchased
 */
    fun notifyPropertyPurchased(player: Player, property: Property) {
        notify("${player.name} purchased ${property.name} for $${property.price}")
    }

/**
 * Notifies that a player has insufficient funds
 */
    fun notifyInsufficientFunds(player: Player) {
        notify("${player.name} doesn't have enough money to make this purchase")
    }

    /**
     * Notifies that rent was paid
     */
    fun notifyRentPaid(player: Player, owner: Player, amount: Int) {
        notify("${player.name} paid $${amount} rent to ${owner.name}")
    }

    /**
     * Extension function to handle building improvements (houses or hotels)
     */
    fun Property.buildImprovement(player: Player, improvementType: String, buildAction: () -> Boolean, getPrice: () -> Int) {
        if (buildAction()) {
            notify("${player.name} built a $improvementType on ${this.name} for $${getPrice()}")
        } else {
            notify("${player.name} cannot build a $improvementType on ${this.name}")
        }
    }

    /**
     * Builds a house on this property if possible
     */
    fun buildHouse(player: Player, property: Property) {
        property.buildImprovement(player, "house", property::buildHouse, property::getHousePrice)
    }

    /**
     * Builds a hotel on this property if possible
     */
    fun buildHotel(player: Player, property: Property) {
        property.buildImprovement(player, "hotel", property::buildHotel, property::getHotelPrice)
    }

    /**
     * Extension function to handle property mortgage actions
     */
    fun Property.handleMortgage(player: Player, mortgageAction: () -> Boolean, actionName: String, onMortgage: () -> Unit) {
        if (mortgageAction()) {
            onMortgage()
            notify("${player.name} $actionName ${this.name} for $${this.price / 2}")
        } else {
            notify("Cannot $actionName ${this.name}")
        }
    }

    /**
     * Mortgages the property if possible
     */
    fun mortgageProperty(player: Player, property: Property) {
        property.handleMortgage(player, { !property.isMortgaged }, "mortgaged", {
            player.addMoney(property.price / 2)
            property.isMortgaged = true
        })
    }

    /**
     * Lifts the mortgage on the property if possible
     */
    fun liftMortgage(player: Player, property: Property) {
        property.handleMortgage(player, { property.isMortgaged && player.money >= property.price / 2 }, "lifted the mortgage on", {
            player.deductMoney(property.price / 2)
            property.isMortgaged = false
        })
    }

    /**
     * Calculates the rent for a property
     */
    fun calculateRent(property: Property): Int {
        return when {
            property.isUtility -> property.calculateUtilityRent()
            property.isRailRoad -> property.calculateRailroadRent()
            property.numHotels > 0 -> property.calculateHotelRent()
            property.numHouses > 0 -> property.calculateHouseRent()
            else -> property.baseRent
        }
    }

    /**
     * Trading Properties
     */
    fun tradeProperties(player1: Player, player2: Player, properties1: List<Property>, properties2: List<Property>) {
        properties1.forEach { it.owner = player2 }
        properties2.forEach { it.owner = player1 }
        notify("${player1.name} and ${player2.name} traded properties")
    }

    /**
     * Auctioning Properties
     */
    fun auctionProperty(property: Property, players: List<Player>) {
        notify("Auctioning ${property.name} to the highest bidder")
    }

    /**
     * Bankruptcy Handling
     */
    fun handleBankruptcy(player: Player, creditor: Player) {
        player.properties.forEach { it.owner = creditor }
        notify("${player.name} has gone bankrupt. All properties transferred to ${creditor.name}")
    }

    /**
     * Property Sets
     */
    fun checkMonopoly(player: Player, colorGroup: PropertyColor): Boolean {
        return player.properties.count { it.color == colorGroup } == colorGroup.propertiesInGroup
    }

    /**
     * Utility & Railroad Calculation
     */
    fun calculateUtilityRent(utility: Property, diceRoll: Int): Int {
        return if (utility.owner?.getUtilityCount() == 2) {
            diceRoll * 10
        } else {
            diceRoll * 4
        }
    }

    fun calculateRailroadRent(railroad: Property): Int {
        return when (railroad.owner?.getRailroadCount()) {
            1 -> 25
            2 -> 50
            3 -> 100
            4 -> 200
            else -> 0
        }
    }

    /**
     * Property Transfer
     */
    fun transferProperty(property: Property, newOwner: Player) {
        property.owner = newOwner
        notify("${property.name} has been transferred to ${newOwner.name}")
    }

    /**
     * Property Status Check
     */
    fun checkPropertyStatus(property: Property) {
        notify("${property.name} is owned by ${property.owner?.name ?: "the bank"}")
        if (property.isMortgaged) {
            notify("${property.name} is currently mortgaged")
        }
        if (property.numHouses > 0) {
            notify("${property.name} has ${property.numHouses} houses")
        }
        if (property.numHotels > 0) {
            notify("${property.name} has a hotel")
        }
    }

    /**
     * Property Sale
     */
    fun sellProperty(player: Player, property: Property) {
        if (property.owner == player) {
            val salePrice = property.price / 2
            player.addMoney(salePrice)
            property.owner = null
            notify("${player.name} sold ${property.name} for $${salePrice}")
        } else {
            notify("${player.name} does not own ${property.name}")
        }
    }

    /**
     * Property Improvement Check
     */
    fun canImproveProperty(player: Player, property: Property): Boolean {
        return property.owner == player && player.money >= property.getHousePrice() && property.numHouses < 4
    }

    /**
     * Extension function to handle removing improvements (houses or hotels)
     */
    fun Property.handleRemoveImprovement(player: Player, removeAction: () -> Boolean, improvementType: String, getPrice: () -> Int) {
        if (removeAction()) {
            val refund = getPrice() / 2
            player.addMoney(refund)
            notify("${player.name} removed a $improvementType from ${this.name} and received $${refund}")
        } else {
            notify("${this.name} has no improvements to remove")
        }
    }

    /**
     * Property Improvement Removal
     */
    fun removeImprovement(player: Player, property: Property) {
        when {
            property.numHouses > 0 -> {
                property.handleRemoveImprovement(player, { property.numHouses > 0 }, "house", property::getHousePrice)
                property.numHouses--
            }
            property.numHotels > 0 -> {
                property.handleRemoveImprovement(player, { property.numHotels > 0 }, "hotel", property::getHotelPrice)
                property.numHotels--
            }
            else -> notify("${property.name} has no improvements to remove")
        }
    }

    /**
     * Property Set Notification
     */
    fun notifyPropertySet(player: Player, colorGroup: PropertyColor) {
        if (checkMonopoly(player, colorGroup)) {
            notify("${player.name} has a monopoly on the ${colorGroup} color group")
        }
    }

    /**
     * Property Set Rent Calculation
     */
    fun calculatePropertySetRent(property: Property): Int {
        return if (checkMonopoly(property.owner ?: return property.baseRent, property.color)) {
            property.baseRent * 2
        } else {
            property.baseRent
        }
    }

    /**
     * Property Set Rent Notification
     */
    fun notifyPropertySetRent(player: Player, property: Property) {
        if (checkMonopoly(player, property.color)) {
            notify("${player.name} owns all properties in the ${property.color} color group. Rent is doubled.")
        }
}
}
