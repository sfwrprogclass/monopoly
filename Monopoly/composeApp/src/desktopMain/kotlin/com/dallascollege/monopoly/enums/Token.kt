package com.dallascollege.monopoly.enums

import com.dallascollege.monopoly.enums.Token.*

enum class Token {
    TOP_HAT, BATTLESHIP, IRON, BOOT, WHEEL_BARREL, THIMBLE, CAR, DOG
}

fun convertTokenToImageStr(token: Token): String {
    return when (token) {
        TOP_HAT -> "TopHat"
        BATTLESHIP -> "Battleship"
        BOOT -> "Boot"
        IRON -> "Iron"
        THIMBLE -> "Thimble"
        WHEEL_BARREL -> "WheelBarrow"
        CAR -> "Car"
        DOG -> "Dog"
    }
}