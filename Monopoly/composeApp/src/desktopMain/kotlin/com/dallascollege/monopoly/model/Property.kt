package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Color

class Property(
    id: Int,
    name: String,
    price: Int,
    color: Color,
    isPurchased: Boolean,
    baseRent: Int,
    isUtility: Boolean,
    isRailRoad: Boolean,
    numHouses: Int,
    numHotels: Int,
    isMortgaged: Boolean
) {
    var id: Int = id
        get() = field
        set(value) {
            field = value
        }

    var name: String = name
        get() = field
        set(value) {
            field = value
        }

    var price: Int = price
        get() = field
        set(value) {
            field = value
        }

    var color: Color = color
        get() = field
        set(value) {
            field = value
        }

    var isPurchased: Boolean = isPurchased
        get() = field
        set(value) {
            field = value
        }

    var baseRent: Int = baseRent
        get() = field
        set(value) {
            field = value
        }

    var isUtility: Boolean = isUtility
        get() = field
        set(value) {
            field = value
        }

    var isRailRoad: Boolean = isRailRoad
        get() = field
        set(value) {
            field = value
        }

    var numHouses: Int = numHouses
        get() = field
        set(value) {
            field = value
        }

    var numHotels: Int = numHotels
        get() = field
        set(value) {
            field = value
        }

    var isMortgaged: Boolean = isMortgaged
        get() = field
        set(value) {
            field = value
        }
}
