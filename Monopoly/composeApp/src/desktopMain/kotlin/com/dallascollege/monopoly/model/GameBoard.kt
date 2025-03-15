package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor

class GameBoard(
    players: Array<Player>,
    turnOrder: Array<Int> = emptyArray(),
    currentTurn: Int = 0,
    selectedPlayerId: Int = 1,
    centralMoney: Int = 0,
    speedDieMode: Boolean = false,
    freeParkingRule: Boolean = false,
    cells: Array<Cell> = emptyArray(),
    properties: Array<Property> = emptyArray()
) {
    private fun createCells() {
        val cellList = mutableListOf<Cell>()
        var numCell = 1

        // Add cells to the list
        cellList.add(Cell(numCell = numCell, isCollectSalary = true))
        numCell++
        cellList.add(Cell(numCell = numCell, propertyId = 1))
        numCell++
        // Add remaining cells...

        this.cells = cellList.toTypedArray()
    }

    private fun createProperties() {
        val propertyList = mutableListOf<Property>()
        var id = 1

        // Add properties to the list
        propertyList.add(Property(id = id, name = "San Diego Drive", price = 60, color = PropertyColor.BROWN))
        id++
        propertyList.add(Property(id = id, name = "Kansas Drive", price = 90, color = PropertyColor.BROWN))
        id++
        // Add remaining properties...

        this.properties = propertyList.toTypedArray()
    }

    fun createModels() {
        createProperties()
        createCells()
    }

    var players: Array<Player> = players
    var turnOrder: Array<Int> = turnOrder
    var currentTurn: Int = currentTurn
    var selectedPlayerId: Int = selectedPlayerId
    var centralMoney: Int = centralMoney
    var speedDieMode: Boolean = speedDieMode
    var freeParkingRule: Boolean = freeParkingRule
    var cells: Array<Cell> = cells
    var properties: Array<Property> = properties
}

