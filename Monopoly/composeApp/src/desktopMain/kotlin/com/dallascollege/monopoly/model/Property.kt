package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.enums.PropertyColor.NONE
import com.dallascollege.monopoly.logic.GameBoard
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

    // Purchase the property
    fun purchase(player: Player, gameBoard: GameBoard): Boolean {
        if (isPurchased) return false
        if (player.totalMoney < price) return false

        player.totalMoney -= price
        isPurchased = true
        owner = player
        player.addProperty(id, gameBoard)
        return true
    }

    // Charge rent to a player landing on the property
    fun chargeRent(player: Player, gameBoard: GameBoard): Int {
        if (!isPurchased || isMortgaged || owner == player) return 0

        val rentAmount = calculateRent(gameBoard)
        val amountPaid = min(rentAmount, player.totalMoney)
        player.totalMoney -= amountPaid
        owner?.totalMoney = (owner?.totalMoney ?: 0) + amountPaid

        return amountPaid
    }

    // Calculate rent based on the property configuration
    fun calculateRent(gameBoard: GameBoard): Int {
        val ownsFullSet = owner?.propertyIds?.count {
            val property = gameBoard.getPropertyById(it)
            property?.color == color
        } == PropertyColor.getColorGroupSize(color)

        return when {
            isUtility -> {
                val utilitiesOwned = owner?.getUtilities(gameBoard)?.size ?: 0
                baseRent * (4 * utilitiesOwned)
            }
            isRailRoad -> {
                val railroadsOwned = owner?.getRailroads(gameBoard)?.size ?: 0
                baseRent * (2 * railroadsOwned)
            }
            numHotels > 0 -> baseRent * (numHotels + 5)
            numHouses > 0 -> baseRent * (numHouses + 1)
            ownsFullSet -> baseRent * 2 // Double rent for owning the full set
            else -> baseRent
        }
    }

    // Build a house on the property
    fun buildHouse(gameBoard: GameBoard): Boolean {
        if (numHotels > 0 || numHouses >= 4) return false

        val ownsFullSet = owner?.propertyIds?.count {
            val property = gameBoard.getPropertyById(it)
            property?.color == color
        } == PropertyColor.getColorGroupSize(color)

        if (!ownsFullSet) return false
        if (gameBoard.properties.sumOf { it.numHouses } >= GameBoard.MAX_HOUSES) return false
        if (owner?.totalMoney ?: 0 < getHousePrice()) return false

        owner?.totalMoney = (owner?.totalMoney ?: 0) - getHousePrice()
        numHouses++
        return true
    }

    // Build a hotel on the property
    fun buildHotel(gameBoard: GameBoard): Boolean {
        if (numHouses < 4) return false
        if (gameBoard.properties.sumOf { it.numHotels } >= GameBoard.MAX_HOTELS) return false
        if (owner?.totalMoney ?: 0 < getHotelPrice()) return false

        owner?.totalMoney = (owner?.totalMoney ?: 0) - getHotelPrice()
        numHouses = 0
        numHotels++
        return true
    }

    // Get the price of a house for the property
    fun getHousePrice(): Int {
        return when (color) {
            PropertyColor.BROWN, PropertyColor.LIGHT_BLUE -> 50
            PropertyColor.PINK, PropertyColor.ORANGE -> 100
            PropertyColor.RED, PropertyColor.YELLOW -> 150
            PropertyColor.GREEN, PropertyColor.BLUE -> 200
            else -> 0
        }
    }

    // Get the price of a hotel for the property
    fun getHotelPrice(): Int {
        return getHousePrice() * 5
    }

    // Mortgage the property
    fun mortgage(): Boolean {
        if (isMortgaged) return false
        isMortgaged = true
        owner?.totalMoney = (owner?.totalMoney ?: 0) + (price / 2)
        return true
    }

    // Unmortgage the property
    fun unmortgage(): Boolean {
        if (!isMortgaged) return false
        val unmortgageCost = (price / 2) + (price / 10)
        if (owner?.totalMoney ?: 0 < unmortgageCost) return false
        owner?.totalMoney = (owner?.totalMoney ?: 0) - unmortgageCost
        isMortgaged = false
        return true
    }
}