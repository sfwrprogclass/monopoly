package com.dallascollege.monopoly.model
import androidx.compose.ui.graphics.ImageBitmap

class Cell(
    numCell: Int = 1,
    propertyId: Int = 1,
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
}
