package com.dallascollege.monopoly.model

import androidx.compose.ui.graphics.ImageBitmap

class GameBoard(
    turnOrder: Array<Int>,
    currentTurn: Int,
    centralMoney: Int,
    centralImage: ImageBitmap,
    speedDieMode: Boolean,
    freeParkingRule: Boolean,
    cells: Array<Cell>,
    dice: Dice,
    properties: Array<Property>
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

    var centralImage: ImageBitmap = centralImage
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

    var dice: Dice = dice
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

