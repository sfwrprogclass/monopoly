package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.model.Cell
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Property

class GameBoard(
    initialPlayers: Array<Player>
) {

    companion object {
        const val MAX_HOUSES = 4
        const val MAX_HOTELS = 1
        private const val START_CELL_ID = 1
        private const val FIRST_PROPERTY_ID = 1
    }

    // Mutable state
    var players: Array<Player> = initialPlayers
    var turnOrder: Array<Int> = emptyArray()
    var currentTurn: Int = 0
    var selectedPlayerId: Int = 1
    var centralMoney: Int = 0
    var speedDieMode: Boolean = false
    var freeParkingRule: Boolean = false
    var cells: Array<Cell> = emptyArray()
    var properties: Array<Property> = emptyArray()

    fun createModels() {
        initializeProperties()
        initializeCells()
    }

    private fun initializeCells() {
        val cellList = mutableListOf<Cell>()
        cellList.addDefaultCells(START_CELL_ID)
        this.cells = cellList.toTypedArray()
    }

    private fun initializeProperties() {
        val propertyList = mutableListOf<Property>()
        propertyList.addDefaultProperties(FIRST_PROPERTY_ID)
        this.properties = propertyList.toTypedArray()
    }

    // New helper function to add default cells
    private fun MutableList<Cell>.addDefaultCells(startCellId: Int) {
        add(Cell(numCell = startCellId, isCollectSalary = true))
        add(Cell(numCell = startCellId + 1, propertyId = FIRST_PROPERTY_ID))
        // Add additional cells as needed...
    }

    // New helper function to add default properties
    private fun MutableList<Property>.addDefaultProperties(startPropertyId: Int) {
        add(Property(id = startPropertyId, name = "San Diego Drive", price = 60, color = PropertyColor.BROWN))
        add(Property(id = startPropertyId + 1, name = "Kansas Drive", price = 90, color = PropertyColor.BROWN))
        // Add additional properties as needed...
    }

    fun getPlayerById(id: Int): Player? = players.find { it.id == id }

    fun getPropertyById(id: Int): Property? = properties.find { it.id == id }

    fun getCellById(numCell: Int): Cell? = cells.find { it.numCell == numCell }

    fun getPropertyOwner(property: Property): Player? {
        return players.find { property.id in it.propertyIds }
    }
}