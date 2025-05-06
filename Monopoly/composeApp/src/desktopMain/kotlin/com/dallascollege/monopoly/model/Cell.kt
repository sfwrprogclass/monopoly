package com.dallascollege.monopoly.model

class Cell(
    val numCell: Int = 1,
    val propertyId: Int = -1,
    val isGoToJail: Boolean = false,
    val isLuxuryTax: Boolean = false,
    val isIncomeTax: Boolean = false,
    val isChance: Boolean = false,
    val isCommunityChest: Boolean = false,
    val isParking: Boolean = false,
    val isCollectSalary: Boolean = false,
    val isVisitingJail: Boolean = false
) {

    fun isProperty(): Boolean {
        return propertyId != -1
    }

    fun getName(board: GameBoard): String {
        if (isProperty()) {
            val property = board.getPropertyById(propertyId)
            if (property != null)
                return property.name
            else
                return "Unknown property"
        } else if (isVisitingJail) {
                return "Visiting jail"
        } else if (isCollectSalary) {
                return "Collect salary"
        } else if (isParking) {
            return "Parking"
        } else if (isCommunityChest) {
            return "Community Chest"
        } else if (isChance) {
            return "Chance"
        } else if (isIncomeTax) {
            return "Income Tax"
        } else if (isLuxuryTax) {
            return "Luxury Tax"
        } else if (isGoToJail) {
            return "Go to Jail"
        } else {
            return "Unknown cell"
        }
    }
}
