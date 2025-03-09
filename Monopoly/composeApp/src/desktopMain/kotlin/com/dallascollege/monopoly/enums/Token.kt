package com.dallascollege.monopoly.enums

enum class Token {
    TOP_HAT, BATTLESHIP, IRON, BOOT, WHEEL_BARREL, THIMBLE, CAR, DOG
}

fun convertTokenToImageStr(token: Token): String {
    return when (token) {
        Token.TOP_HAT -> "TopHat"
        Token.BATTLESHIP -> "Battleship"
        Token.BOOT -> "Boot"
        Token.IRON -> "Iron"
        Token.THIMBLE -> "Thimble"
        Token.WHEEL_BARREL -> "WheelBarrow"
        Token.CAR -> "Car"
        Token.DOG -> "Dog"
    }
}