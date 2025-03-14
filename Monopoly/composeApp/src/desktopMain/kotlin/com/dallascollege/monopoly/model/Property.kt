package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor
import kotlin.math.min

class Property(
    id: Int,
    name: String,
    price: Int,
    color: PropertyColor = PropertyColor.WHITE,
    isPurchased: Boolean = false,
    baseRent: Int = 0,
    isUtility: Boolean = false,
    isRailRoad: Boolean = false,
    numHouses: Int = 0,
    numHotels: Int = 0,
    isMortgaged: Boolean = false
) {
    var id: Int = id
    var name: String = name
    var price: Int = price
    var color: PropertyColor = color
    var isPurchased: Boolean = isPurchased
    var baseRent: Int = baseRent
    var isUtility: Boolean = isUtility
    var isRailRoad: Boolean = isRailRoad
    var numHouses: Int = numHouses
    var numHotels: Int = numHotels
    var isMortgaged: Boolean = isMortgaged

    // New property for owner reference
    var owner: Player? = null
        private set

    /**
     * Attempts to purchase this property for the given player
     */
    fun purchase(player: Player): Boolean {
        if (isPurchased) return false
        if (player.money < price) return false

        player.money -= price
        isPurchased = true
        owner = player
        player.addProperty(this)
        return true
    }

    /**
     * Calculates and charges rent to a player landing on this property
     */
    fun chargeRent(player: Player): Int {
        if (!isPurchased || isMortgaged || owner == player) return 0

        val rentAmount = calculateRent()
        val amountPaid = min(rentAmount, player.money)
        player.money -= amountPaid
        owner?.money = (owner?.money ?: 0) + amountPaid
        return amountPaid
    }

    /**
     * Calculates the rent amount based on property type and development
     */
    private fun calculateRent(): Int {
        return when {
            isUtility -> calculateUtilityRent()
            isRailRoad -> calculateRailroadRent()
            numHotels > 0 -> calculateHotelRent()
            numHouses > 0 -> calculateHouseRent()
            else -> baseRent
        }
    }

    private fun calculateUtilityRent(): Int {
        val diceValue = 7 // Replace with actual dice value
        val multiplier = if (owner?.getUtilityCount() == 2) 10 else 4
        return diceValue * multiplier
    }

    private fun calculateRailroadRent(): Int {
        val railroadCount = owner?.getRailroadCount() ?: 0
        return baseRent * (1 shl (railroadCount - 1))
    }

    private fun calculateHouseRent(): Int {
        return baseRent * (numHouses + 1) * 2
    }

    private fun calculateHotelRent(): Int {
        return baseRent * 10
    }

    /**
     * Builds a house on this property if possible
     */
    fun buildHouse(): Boolean {
        if (numHotels > 0 || numHouses >= 4) return false
        if (owner?.money ?: 0 < getHousePrice()) return false

        owner?.money = (owner?.money ?: 0) - getHousePrice()
        numHouses++
        return true
    }

    /**
     * Builds a hotel on this property if possible
     */
    fun buildHotel(): Boolean {
        if (numHouses < 4) return false
        if (owner?.money ?: 0 < getHotelPrice()) return false

        owner?.money = (owner?.money ?: 0) - getHotelPrice()
        numHouses = 0
        numHotels++
        return true
    }

    /**
     * Gets the price to build a house on this property
     */
    fun getHousePrice(): Int {
        return when (color) {
            PropertyColor.BROWN, PropertyColor.LIGHT_BLUE -> 50
            PropertyColor.PINK, PropertyColor.ORANGE -> 100
            PropertyColor.RED, PropertyColor.YELLOW -> 150
            PropertyColor.GREEN, PropertyColor.BLUE -> 200
            else -> 0
        }
    }

    /**
     * Gets the price to build a hotel on this property
     */
    fun getHotelPrice(): Int {
        return getHousePrice() * 5
    }
}
