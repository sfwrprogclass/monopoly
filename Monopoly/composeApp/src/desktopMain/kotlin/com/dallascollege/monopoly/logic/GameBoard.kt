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
    var cells: Array<Cell> = mutableListOf<Cell>().apply { addDefaultCells(1) }.toTypedArray()
    var properties: Array<Property> = mutableListOf<Property>().apply { addDefaultProperties(1) }.toTypedArray()

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
        var currentCellId = startCellId

        // Add cells for the top side
        for (i in 1..10) { // Assume 10 cells per side
            add(Cell(numCell = currentCellId++, isCollectSalary = i == 1)) // First cell: START
        }

        // Add cells for the right side
        for (i in 1..10) {
            add(Cell(numCell = currentCellId++, propertyId = currentCellId)) // Properties or other actions
        }

        // Add cells for the bottom side
        for (i in 1..10) {
            add(Cell(numCell = currentCellId++)) // Use generic cells or add specific logic if required
        }

        // Add cells for the left side
        for (i in 1..10) {
            add(Cell(numCell = currentCellId++, propertyId = if (i % 2 == 0) currentCellId else -1)) // Alternate properties
        }
    }
    }

    // New helper function to add default properties
    private fun MutableList<Property>.addDefaultProperties(startPropertyId: Int) {
        add(Property(id = startPropertyId, name = "San Diego Drive", price = 60, color = PropertyColor.BROWN))
        add(Property(id = startPropertyId + 1, name = "Kansas Drive", price = 90, color = PropertyColor.BROWN))
        // Add additional properties as needed...
    }

fun getPlayerById(id: Int): Player? = GameEngine.players.find { it.id == id }
fun GameBoard.findPropertyById(propertyId: Int): Property? {
    require(propertyId > 0) { "Property ID must be greater than 0." }
    return properties.firstOrNull { it.id == propertyId }
}
fun GameBoard.findCellById(cellId: Int): Cell? {
    require(cellId > 0) { "Cell ID must be greater than 0." }
    return cells.firstOrNull { it.numCell == cellId }

    fun getPropertyOwner(property: Property): Player? {
        return this.players.find { property.id in it.propertyIds }
    }
}