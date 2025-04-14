package com.dallascollege.monopoly.model

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

    var propertyId: Int = propertyId

    var isGoToJail: Boolean = isGoToJail

    var isLuxuryTax: Boolean = isLuxuryTax

    var isIncomeTax: Boolean = isIncomeTax

    var isChance: Boolean = isChance

    var isCommunityChest: Boolean = isCommunityChest

    var isParking: Boolean = isParking

    var isCollectSalary: Boolean = isCollectSalary

    var isVisitingJail: Boolean = isVisitingJail

    fun isProperty(): Boolean {
        return propertyId != -1
    }
}
