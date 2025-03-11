package com.dallascollege.monopoly.model

/**
 * Contains property management utility functions for player interactions
 */

/**
 * Asks the player if they want to buy a property
 */
fun askPlayerIfTheyWantToBuy(player: Player, property: Property): Boolean {
    println("${player.name}, do you want to buy ${property.name} for $${property.price}? (yes/no)")
    val response = readLine()?.lowercase()
    return response == "yes" || response == "y"
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
