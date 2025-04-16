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
    isMortgaged: Boolean = false,
    val houseRents: List<Int> = listOf(),
    val hotelRent: Int = 0,
    val colorGroup: String? = null,
    var ownerId: Int? = null,
) {
    var id: Int = id

    var name: String = name

    var price: Int = price

    var color: PropertyColor = color

    var isPurchased: Boolean = isPurchased

    var baseRent: Int = baseRent

    var isUtility: Boolean = isUtility

    var isRailRoad: Boolean = isRailRoad

    var numHouses: Int = numHouses

    var numHotels: Int = numHotels

    var isMortgaged: Boolean = isMortgaged
    var hasHotel: Boolean = false
        get() = numHotels > 0

}

