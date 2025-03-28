package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor

class Property(
    id: Int,
    name: String,
    price: Int,
    color: PropertyColor = PropertyColor.WHITE,
    isPurchased: Boolean= false,
    baseRent: Int = 0,
    isUtility: Boolean = false,
    isRailRoad: Boolean = false,
    numHouses: Int = 0,
    numHotels: Int = 0,
    isMortgaged: Boolean = false
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

    var color: PropertyColor = color
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
