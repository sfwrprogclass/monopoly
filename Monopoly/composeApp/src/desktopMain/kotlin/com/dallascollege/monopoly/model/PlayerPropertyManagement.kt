package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.model.Property.*


/**
 * Contains property management utility functions for player interactions
 */

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
    println("${player.name}, do you want to buy ${property.name} for $${property.price}?")
}

/**
 * Notifies that a property was purchased
 */
fun notifyPropertyPurchased(player: Player, property: Property) {
    println("${player.name} purchased ${property.name} for $${property.price}")
}

/**
 * Notifies that a player has insufficient funds
 */
fun notifyInsufficientFunds(player: Player) {
    println("${player.name} doesn't have enough money to make this purchase")
}

/**
 * Notifies that rent was paid
 */
fun notifyRentPaid(player: Player, owner: Player, amount: Int) {
    println("${player.name} paid $${amount} rent to ${owner.name}")
}

/**
 * Builds a house on this property if possible
 */
fun buildHouse(player: Player, property: Property) {
    if (property.buildHouse()) {
        println("${player.name} built a house on ${property.name} for $${property.getHousePrice()}")
    } else {
        println("${player.name} cannot build a house on ${property.name}")
    }
}

/**
 * Builds a hotel on this property if possible
 */
fun buildHotel(player: Player, property: Property) {
    if (property.buildHotel()) {
        println("${player.name} built a hotel on ${property.name} for $${property.getHotelPrice()}")
    } else {
        println("${player.name} cannot build a hotel on ${property.name}")
    }
}

/**
 * Mortgages the property if possible
 */
fun mortgageProperty(player: Player, property: Property) {
    if (!property.isMortgaged) {
        player.addMoney(property.price / 2)
        property.isMortgaged = true
        println("${player.name} mortgaged ${property.name} for $${property.price / 2}")
    } else {
        println("${property.name} is already mortgaged")
    }
}

/**
 * Lifts the mortgage on the property if possible
 */
fun liftMortgage(player: Player, property: Property) {
    if (property.isMortgaged && player.money >= property.price / 2) {
        player.deductMoney(property.price / 2)
        property.isMortgaged = false
        println("${player.name} lifted the mortgage on ${property.name} for $${property.price / 2}")
    } else {
        println("${player.name} cannot afford to lift the mortgage on ${property.name}")
    }
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
    println("${player1.name} and ${player2.name} traded properties")
}

/**
 * Auctioning Properties
 */
fun auctionProperty(property: Property, players: List<Player>) {
    println("Auctioning ${property.name} to the highest bidder")
}

/**
 * Bankruptcy Handling
 */
fun handleBankruptcy(player: Player, creditor: Player) {
    player.properties.forEach { it.owner = creditor }
    println("${player.name} has gone bankrupt. All properties transferred to ${creditor.name}")
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
    println("${property.name} has been transferred to ${newOwner.name}")
}

/**
 * Property Status Check
 */
fun checkPropertyStatus(property: Property) {
    println("${property.name} is owned by ${property.owner?.name ?: "the bank"}")
    if (property.isMortgaged) {
        println("${property.name} is currently mortgaged")
    }
    if (property.numHouses > 0) {
        println("${property.name} has ${property.numHouses} houses")
    }
    if (property.numHotels > 0) {
        println("${property.name} has a hotel")
    }
}

/**
 * Property Sale
 */
fun sellProperty(player: Player, property: Property) {
    if (property.owner == player) {
        player.addMoney(property.price / 2)
        property.owner = null
        println("${player.name} sold ${property.name} for $${property.price / 2}")
    } else {
        println("${player.name} does not own ${property.name}")
    }
}

/**
 * Property Improvement Check
 */
fun canImproveProperty(player: Player, property: Property): Boolean {
    return property.owner == player && player.money >= property.getHousePrice() && property.numHouses < 4
}

/**
 * Property Improvement Removal
 */
fun removeImprovement(player: Player, property: Property) {
    if (property.numHouses > 0) {
        player.addMoney(property.getHousePrice() / 2)
        property.numHouses--
        println("${player.name} removed a house from ${property.name} and received $${property.getHousePrice() / 2}")
    } else if (property.numHotels > 0) {
        player.addMoney(property.getHotelPrice() / 2)
        property.numHotels--
        println("${player.name} removed a hotel from ${property.name} and received $${property.getHotelPrice() / 2}")
    } else {
        println("${property.name} has no improvements to remove")
    }
}

/**
 * Property Set Notification
 */
fun notifyPropertySet(player: Player, colorGroup: PropertyColor) {
    if (checkMonopoly(player, colorGroup)) {
        println("${player.name} has a monopoly on the ${colorGroup} color group")
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
        println("${player.name} owns all properties in the ${property.color} color group. Rent is doubled.")
    }
}
