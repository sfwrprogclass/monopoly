package com.dallascollege.monopoly.model

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
    // Instead of directly reading from console, we'll use a callback
    // The UI layer will be responsible for showing a dialog and calling onDecision
    // with the player's choice

    // For logging purposes
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
 * Building Houses and Hotels
 */
fun buildHouse(player: Player, property: Property) {
    if (player.canAfford(property.houseCost) && property.canBuildHouse()) {
        player.deductMoney(property.houseCost)
        property.addHouse()
        println("${player.name} built a house on ${property.name} for $${property.houseCost}")
    } else {
        println("${player.name} cannot build a house on ${property.name}")
    }
}

fun buildHotel(player: Player, property: Property) {
    if (player.canAfford(property.hotelCost) && property.canBuildHotel()) {
        player.deductMoney(property.hotelCost)
        property.addHotel()
        println("${player.name} built a hotel on ${property.name} for $${property.hotelCost}")
    } else {
        println("${player.name} cannot build a hotel on ${property.name}")
    }
}

/**
 * Mortgaging Properties
 */
fun mortgageProperty(player: Player, property: Property) {
    if (property.canBeMortgaged()) {
        player.addMoney(property.mortgageValue)
        property.mortgage()
        println("${player.name} mortgaged ${property.name} for $${property.mortgageValue}")
    } else {
        println("${property.name} cannot be mortgaged")
    }
}

fun liftMortgage(player: Player, property: Property) {
    if (player.canAfford(property.mortgageLiftCost)) {
        player.deductMoney(property.mortgageLiftCost)
        property.liftMortgage()
        println("${player.name} lifted the mortgage on ${property.name} for $${property.mortgageLiftCost}")
    } else {
        println("${player.name} cannot afford to lift the mortgage on ${property.name}")
    }
}

/**
 * Rent collection
 */
fun calculateRent(property: Property): Int {
    return when {
        property.hasHotel() -> property.rentWithHotel
        property.houseCount > 0 -> property.rentWithHouses[property.houseCount - 1]
        property.isMonopoly() -> property.rent * 2
        else -> property.rent
    }
}
/**
 * Trading Properties
 */
fun tradeProperties(player1: Player, player2: Player, properties1: List<Property>, properties2: List<Property>) {
    properties1.forEach { it.transferOwnership(player2) }
    properties2.forEach { it.transferOwnership(player1) }
    println("${player1.name} and ${player2.name} traded properties")
}

/**
 * Auctioning Properties
 */
fun auctionProperty(property: Property, players: List<Player>) {
    // Implement auction logic here
    println("Auctioning ${property.name} to the highest bidder")
}

/**
 * Bankruptcy Handling
 */
fun handleBankruptcy(player: Player, creditor: Player) {
    player.properties.forEach { it.transferOwnership(creditor) }
    println("${player.name} has gone bankrupt. All properties transferred to ${creditor.name}")
}

/**
 * Property Sets
 */
fun checkMonopoly(player: Player, colorGroup: ColorGroup): Boolean {
    return colorGroup.properties.all { it.owner == player }
}

/**
 * Utility & Railroad Calculation
 */
fun calculateUtilityRent(utility: Utility, diceRoll: Int): Int {
    return if (utility.owner?.let { checkMonopoly(it, utility.colorGroup) } == true) {
        diceRoll * 10
    } else {
        diceRoll * 4
    }
}

fun calculateRailroadRent(railroad: Railroad): Int {
    return when (railroad.owner?.ownedRailroads?.size) {
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
 * Propety Status Check
 */
fun checkPropertyStatus(property: Property) {
    println("${property.name} is owned by ${property.owner?.name ?: "the bank"}")
    if (property.isMortgaged) {
        println("${property.name} is currently mortgaged")
    }
    if (property.houseCount > 0) {
        println("${property.name} has ${property.houseCount} houses")
    }
    if (property.hasHotel()) {
        println("${property.name} has a hotel")
    }
}

/**
 * Property Sale
 */
fun sellProperty(player: Player, property: Property) {
    if (property.owner == player) {
        player.addMoney(property.sellPrice)
        property.owner = null
        println("${player.name} sold ${property.name} for $${property.sellPrice}")
    } else {
        println("${player.name} does not own ${property.name}")
    }
}

/**
 * Property Improvement Check
 */
fun canImproveProperty(player: Player, property: Property): Boolean {
    return property.owner == player && player.canAfford(property.houseCost) && property.canBuildHouse()
}

/**
 * Property Improvement Removal
 */
fun removeImprovement(player: Player, property: Property) {
    if (property.houseCount > 0) {
        player.addMoney(property.houseCost / 2)
        property.removeHouse()
        println("${player.name} removed a house from ${property.name} and received $${property.houseCost / 2}")
    } else if (property.hasHotel()) {
        player.addMoney(property.hotelCost / 2)
        property.removeHotel()
        println("${player.name} removed a hotel from ${property.name} and received $${property.hotelCost / 2}")
    } else {
        println("${property.name} has no improvements to remove")
    }
}


/**
 * Property Set Rent Calculation
 */

fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}


/**
 * Property Set Check
 */
fun checkPropertySet(player: Player, colorGroup: ColorGroup): Boolean {
    return colorGroup.properties.all { it.owner == player }
}

/**
 * Property Set Notification
 */
fun notifyPropertySet(player: Player, colorGroup: ColorGroup) {
    if (checkPropertySet(player, colorGroup)) {
        println("${player.name} has a monopoly on the ${colorGroup.name} color group")
    }
}

/**
 * Property Set Rent Calculation
 */
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}

fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}

fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}

fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}

fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}

fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}

fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}

fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}

fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}

fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}

fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}

fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}

fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
fun notifyPropertySetRent(player: Player, property: Property) {
    if (property.isMonopoly()) {
        println("${player.name} owns all properties in the ${property.colorGroup.name} color group. Rent is doubled.")
    }
}
fun calculatePropertySetRent(property: Property): Int {
    return if (property.isMonopoly()) {
        property.rent * 2
    } else {
        property.rent
    }
}
