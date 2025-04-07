package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Cell
import com.dallascollege.monopoly.model.Property
import com.dallascollege.monopoly.enums.PropertyColor

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GameEngineTest {

    private lateinit var gameBoard: GameBoard
    private lateinit var player: Player

    @BeforeEach
    fun setUp() {
        val players = arrayOf(
            Player(
                id = 1,
                name = "Player1",
                token = Token.TOPHAT
            )
        )
        val turnOrder = arrayOf(1)
        val cells = arrayOf<Cell>()
        val properties = arrayOf<Property>()

        gameBoard = GameBoard(
            players = players,
            turnOrder = turnOrder,
            currentTurn = 1,
            selectedPlayerId = 1,
            centralMoney = 0,
            speedDieMode = false,
            freeParkingRule = false,
            cells = cells,
            properties = properties
        )

        player = gameBoard.getPlayerById(1)!!
    }

    @Test
    fun `movePlayer should move player correctly`() {
        val initialPosition = player.numCell
        GameEngine.movePlayer(gameBoard, player.id, 5)

        assertEquals(initialPosition + 5, player.numCell)
    }

    @Test
    fun `collectBaseRent should deduct and add rent correctly`() {
        val owner = Player(id = 2, name = "Player2", token = Token.DOG)
        val property = Property(id = 1, name = "San Diego Drive", baseRent = 50, price = 60, color = PropertyColor.BLUE)
        owner.propertyIds = arrayOf(1)
        val cell = Cell(numCell = 5, propertyId = 1)
        gameBoard.players = arrayOf(player, owner)
        gameBoard.cells = arrayOf(cell)
        gameBoard.properties = arrayOf(property)

        player.numCell = 5
        GameEngine.collectBaseRent(gameBoard, player.id)

        assertEquals(1450, player.totalMoney)
        assertEquals(1550, owner.totalMoney)
    }

    @Test
    fun `collectUtilities should deduct and add rent based on number of utilities owned`() {
        val owner = Player(id = 2, name = "Player2", token = Token.DOG)
        val utility = Property(id = 1, name = "Car Company", baseRent = 100, price = 150, isUtility = true)
        val utility2 = Property(id = 2, name = "Water Works", baseRent = 100, price = 150, isUtility = true)
        val cell = Cell(numCell = 5, propertyId = 1)
        val cell2 = Cell(numCell = 6, propertyId = 2)
        gameBoard.players = arrayOf(player, owner)
        gameBoard.cells = arrayOf(cell, cell2)
        gameBoard.properties = arrayOf(utility, utility2)

        player.numCell = 5
        owner.propertyIds = arrayOf(1, 2)

        GameEngine.collectUtilities(gameBoard, player.id)

        assertEquals(1300, player.totalMoney)
        assertEquals(1700, owner.totalMoney)
    }

    @Test
    fun `collectRailroads should deduct and add rent based on number of railroads owned`() {
        val owner = Player(id = 2, name = "Player2", token = Token.DOG)
        val railroad = Property(id = 1, name = "Railroad", baseRent = 100, price = 200, isRailRoad = true)
        val railroad2 = Property(id = 2, name = "Manhattan Railroad", baseRent = 100, price = 200, isRailRoad = true)
        val cell = Cell(numCell = 5, propertyId = 1)
        val cell2 = Cell(numCell = 6, propertyId = 2)
        gameBoard.players = arrayOf(player, owner)
        gameBoard.cells = arrayOf(cell, cell2)
        gameBoard.properties = arrayOf(railroad, railroad2)

        player.numCell = 5
        owner.propertyIds = arrayOf(1, 2)

        GameEngine.collectRailroads(gameBoard, player.id)

        assertEquals(1300, player.totalMoney)
        assertEquals(1700, owner.totalMoney)    }

    @Test
    fun `landingAction should perform correct action based on cell type`() {
//        val property = Property(id = 1, name = "Park Place", baseRent = 50, price = 350, color = PropertyColor.BLUE)
//        val cell = Cell(numCell = 5, propertyId = 1)
//        gameBoard.cells = arrayOf(cell)
//        gameBoard.properties = arrayOf(property)
//
//        player.numCell = 5
//        player.totalMoney = 1500
//        GameEngine.landingAction(gameBoard, player.id)
//
//        assertEquals(1450, player.totalMoney)
    }
}
