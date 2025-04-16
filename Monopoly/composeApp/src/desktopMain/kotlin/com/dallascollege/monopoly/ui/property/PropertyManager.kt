package com.dallascollege.monopoly.logic

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Property
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * Handles property-related actions in the game
 */
class PropertyManager(
    private val gameBoard: GameBoard,
    private val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope
) {
    /**
     * Displays a message using the Snackbar with exception handling
     */
    private fun showSnackbarMessage(message: String) {
        coroutineScope.launch {
            try {
                snackbarHostState.showSnackbar(message)
            } catch (e: Exception) {
                // Handle or log snackbar exceptions to avoid crashes
                println("Error showing snackbar: ${e.message}")
            }
        }
    }

    // Dialog states
    val showPurchaseDialog = mutableStateOf(false)
    val showPayRentDialog = mutableStateOf(false)
    val showMortgageDialog = mutableStateOf(false)
    val showTradeDialog = mutableStateOf(false)
    val showBuildDialog = mutableStateOf(false)

    // Current property and related data
    val currentProperty = mutableStateOf<Property?>(null)
    val currentRentAmount = mutableStateOf(0)
    val purchaseAmount = mutableStateOf(0)
    val mortgageAmount = mutableStateOf(0)
    val unmortgageAmount = mutableStateOf(0)
    val houseCost = mutableStateOf(0)
    val hotelCost = mutableStateOf(0)

    /**
     * Hide all dialogs
     */
    fun hideAllDialogs() {
        showPurchaseDialog.value = false
        showPayRentDialog.value = false
        showMortgageDialog.value = false
        showTradeDialog.value = false
        showBuildDialog.value = false
    }

    /**
     * Handle player landing on a property cell
     */
    fun handlePropertyLanding(player: Player, propertyId: Int) {
        val property = gameBoard.properties.find { it.id == propertyId }
        if (property == null) {
            showSnackbarMessage("Invalid property ID.")
            return
        }
        currentProperty.value = property

        // Check property ownership
        val owner = gameBoard.players.find { it.getProperties(gameBoard).any { prop -> prop.id == property.id } }

        when {
            // Property is available for purchase
            owner == null -> {
                purchaseAmount.value = property.price
                showPurchaseDialog.value = true
            }

            // Property is mortgaged
            property.isMortgaged -> {
                showSnackbarMessage("This property is mortgaged. No rent is due.")
            }

            // Property is owned by another player
            owner.id != player.id -> {
                val rentAmount = calculateRent(property, owner)
                currentRentAmount.value = rentAmount
                showPayRentDialog.value = true
            }

            // Property is owned by the current player
            else -> {
                showSnackbarMessage("You own this property: ${property.name}")
            }
        }
    }

    /**
     * Calculate rent based on property type and ownership structure
     */
    private fun calculateRent(property: Property, owner: Player): Int {
        return when {
            // For utilities
            property.isUtility -> {
                val utilities = owner.getUtilities(gameBoard)
                val diceValue = gameBoard.lastDiceRoll?.sum() ?: 0
                if (utilities.size == 1) {
                    diceValue * 4  // Rent for 1 utility
                } else {
                    diceValue * 10 // Rent for 2 utilities
                }
            }

            // For railroads
            property.isRailRoad -> {
                val railroads = owner.getRailroads(gameBoard)
                when (railroads.size) {
                    1 -> 25
                    2 -> 50
                    3 -> 100
                    4 -> 200
                    else -> property.baseRent
                }
            }

            // For regular properties
            else -> {
                when {
                    property.hasHotel -> property.hotelRent
                    property.numHouses in 1..4 -> property.houseRents.getOrElse(property.numHouses - 1) { 0 }
                    hasMonopoly(property, owner) -> property.baseRent * 2 // Double rent for monopoly
                    else -> property.baseRent
                }
            }
        }
    }

    /**
     * Check if player has a monopoly on the property's color group
     */
    private fun hasMonopoly(property: Property, player: Player): Boolean {
        if (property.colorGroup.isNullOrEmpty()) return false

        val propertiesInGroup = gameBoard.properties.filter { it.colorGroup == property.colorGroup }
        val playerPropertiesInGroup = propertiesInGroup.filter { playerProp ->
            player.getProperties(gameBoard).any { it.id == playerProp.id }
        }

        return propertiesInGroup.size == playerPropertiesInGroup.size
    }

    /**
     * Purchase property for the current player
     */
    fun purchaseProperty(player: Player) {
        val property = currentProperty.value ?: return

        if (player.totalMoney >= property.price) {
            if (player.deductMoney(property.price)) {
                player.addProperty(property.id, gameBoard)
                showSnackbarMessage("${player.name} purchased ${property.name} for $${property.price}")
            } else {
                showSnackbarMessage("Transaction failed. Check your funds.")
            }
        } else {
            showSnackbarMessage("You don't have enough money to purchase this property.")
        }

        showPurchaseDialog.value = false
    }

    /**
     * Pay rent to property owner
     */
    fun payRent(player: Player) {
        val property = currentProperty.value ?: return
        val rentAmount = currentRentAmount.value

        // Find owner
        val owner = gameBoard.players.find { it.getProperties(gameBoard).any { prop -> prop.id == property.id } }
        if (owner == null) {
            showSnackbarMessage("Error: Unable to find property owner.")
            return
        }

        if (player.deductMoney(rentAmount)) {
            owner.addMoney(rentAmount)
            showSnackbarMessage("${player.name} paid $${rentAmount} rent to ${owner.name}")
        } else {
            // Handle insufficient funds (bankruptcy logic could go here)
            showSnackbarMessage("${player.name} doesn't have enough money to pay rent. Bankruptcy process should begin.")
            initiatePlayerBankruptcy(player, owner, rentAmount)
        }

        showPayRentDialog.value = false
    }

    /**
     * Manages player bankruptcy (placeholder implementation)
     */
    private fun initiatePlayerBankruptcy(player: Player, owner: Player, debt: Int) {
        // Example bankruptcy logic placeholder
        showSnackbarMessage("${player.name} has declared bankruptcy owing $${debt} to ${owner.name}.")
    }
}