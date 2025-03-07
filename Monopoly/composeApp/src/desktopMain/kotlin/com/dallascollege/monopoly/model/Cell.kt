package com.dallascollege.monopoly.model
import androidx.compose.ui.graphics.ImageBitmap

class Cell(
    image: ImageBitmap,
    numCell: Int,
    propertyId: Int,
    isGoToJail: Boolean,
    isLuxuryTax: Boolean,
    isIncomeTax: Boolean,
    isChance: Boolean,
    isCommunityChest: Boolean,
    isParking: Boolean
) {
    var image: ImageBitmap = image
        get() = field
        set(value) {
            field = value
        }

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
}
