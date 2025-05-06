package com.dallascollege.monopoly.utils

import com.dallascollege.monopoly.enums.PropertyColor

val PROPERTY_COLOR_VALUES: Map<PropertyColor, Int> = mapOf(
    PropertyColor.PINK to 3,
    PropertyColor.ORANGE to 3,
    PropertyColor.BLUE to 2,
    PropertyColor.LIGHT_BLUE to 3,
    PropertyColor.GREEN to 3,
    PropertyColor.YELLOW to 3,
    PropertyColor.BROWN to 2,
    PropertyColor.RED to 3
)

val HOUSE_PRICE_PER_COLOR: Map<PropertyColor, Int> = mapOf(
    PropertyColor.PINK to 100,
    PropertyColor.ORANGE to 100,
    PropertyColor.BLUE to 200,
    PropertyColor.LIGHT_BLUE to 50,
    PropertyColor.GREEN to 200,
    PropertyColor.YELLOW to 150,
    PropertyColor.BROWN to 50,
    PropertyColor.RED to 150
)
