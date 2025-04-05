package com.dallascollege.monopoly.enums

import com.dallascollege.monopoly.enums.Token.*

enum class Token {
    BOOT,
    DOG,
    CAR,
    IRON,
    TOPHAT,
    THIMBLE,
    WHEELBARROW,
    BATTLESHIP;

    override fun toString(): String {
        return name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }
    }
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