package com.dallascollege.monopoly.model

import androidx.compose.ui.graphics.ImageBitmap

class GameBoard(
    turnOrder: Array<Int> = emptyArray(),
    currentTurn: Int = 0,
    centralMoney: Int = 0,
    speedDieMode: Boolean = false,
    freeParkingRule: Boolean = false,
    cells: Array<Cell> = emptyArray(),
    dice1: Dice,
    dice2: Dice,
    properties: Array<Property> = emptyArray()
) {
    var turnOrder: Array<Int> = turnOrder
        get() = field
        set(value) {
            field = value
        }

    var currentTurn: Int = currentTurn
        get() = field
        set(value) {
            field = value
        }

    var centralMoney: Int = centralMoney
        get() = field
        set(value) {
            field = value
        }

    var speedDieMode: Boolean = speedDieMode
        get() = field
        set(value) {
            field = value
        }

    var freeParkingRule: Boolean = freeParkingRule
        get() = field
        set(value) {
            field = value
        }

    var cells: Array<Cell> = cells
        get() = field
        set(value) {
            field = value
        }

    var dice1: Dice = dice1
        get() = field
        set(value) {
            field = value
        }

    var dice2: Dice = dice2
        get() = field
        set(value) {
            field = value
        }

    var properties: Array<Property> = properties
        get() = field
        set(value) {
            field = value
        }
}

