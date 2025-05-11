package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor

class Property(
    var id: Int,
    var name: String,
    var price: Int,
    var color: PropertyColor = PropertyColor.WHITE,
    var isPurchased: Boolean= false,
    var baseRent: Int = 0,
    var isUtility: Boolean = false,
    var isRailRoad: Boolean = false,
    var numHouses: Int = 0,
    var numHotels: Int = 0,
    var isMortgaged: Boolean = false,
) {

    fun isMortgageable(): Boolean {
        return !isMortgaged && numHotels == 0 && numHouses == 0;
    }
}
