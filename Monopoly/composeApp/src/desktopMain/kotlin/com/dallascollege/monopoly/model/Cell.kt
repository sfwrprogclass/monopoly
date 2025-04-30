package com.dallascollege.monopoly.model
import androidx.compose.ui.graphics.ImageBitmap

class Cell(
    numCell: Int = 1,
    propertyId: Int = -1,
    isGoToJail: Boolean = false,
    isLuxuryTax: Boolean = false,
    isIncomeTax: Boolean = false,
    isChance: Boolean = false,
    isCommunityChest: Boolean = false,
    isParking: Boolean = false,
    isCollectSalary: Boolean = false,
    isVisitingJail: Boolean = false
) {

    var numCell: Int = numCell
        get() = field
        set(value) {
            field = value
        }

    var propertyId: Int = propertyId
        get() = field
        set(value) {
            field = value
        }

    var isGoToJail: Boolean = isGoToJail
        get() = field
        set(value) {
            field = value
        }

    var isLuxuryTax: Boolean = isLuxuryTax
        get() = field
        set(value) {
            field = value
        }

    var isIncomeTax: Boolean = isIncomeTax
        get() = field
        set(value) {
            field = value
        }

    var isChance: Boolean = isChance
        get() = field
        set(value) {
            field = value
        }

    var isCommunityChest: Boolean = isCommunityChest
        get() = field
        set(value) {
            field = value
        }

    var isParking: Boolean = isParking
        get() = field
        set(value) {
            field = value
        }

    var isCollectSalary: Boolean = isCollectSalary
        get() = field
        set(value) {
            field = value
        }

    var isVisitingJail: Boolean = isVisitingJail
        get() = field
        set(value) {
            field = value
        }

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
