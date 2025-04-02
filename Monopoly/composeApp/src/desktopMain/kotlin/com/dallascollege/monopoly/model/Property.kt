package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.enums.PropertyColor.NONE
import kotlin.math.min

class Property(
    val id: Int,
    val name: String,
    val price: Int,
    val color: PropertyColor = NONE,
    var isPurchased: Boolean = false,
    val baseRent: Int = 0,
    val isUtility: Boolean = false,
    val isRailRoad: Boolean = false,
    var numHouses: Int = 0,
    var numHotels: Int = 0,
    var isMortgaged: Boolean = false
) {
    var owner: Player? = null
        internal set

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
    fun calculateRent(): Int {
        return when {
            numHotels > 0 -> baseRent * (numHotels + 5)
            numHouses > 0 -> baseRent * (numHouses + 1)
            else -> baseRent
        }
    }

    fun unmortgage(): Boolean {
        return true
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

    /**
     * Mortgages the property if it is not already mortgaged
     */
    fun mortgage(): Boolean {
        if (isMortgaged) return false
        isMortgaged = true
        owner?.money = (owner?.money ?: 0) + (price / 2)
        return true
    }

    /**
     * Unmortgages the property if it is mortgaged
     */
    fun unmortgage(): Boolean {
        if (!isMortgaged) return false
        val unmortgageCost = (price / 2) + (price / 10) // 10% interest
        if (owner?.money ?: 0 < unmortgageCost) return false
        owner?.money = (owner?.money ?: 0) - unmortgageCost
        isMortgaged = false
        return true
    }
}