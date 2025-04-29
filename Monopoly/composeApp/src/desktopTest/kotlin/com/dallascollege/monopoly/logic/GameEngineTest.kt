package com.dallascollege.monopoly.logic

import com.dallascollege.monopoly.enums.Token
import com.dallascollege.monopoly.model.GameBoard
import com.dallascollege.monopoly.model.Player
import com.dallascollege.monopoly.model.Cell
import com.dallascollege.monopoly.model.Property
import com.dallascollege.monopoly.enums.PropertyColor
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import androidx.compose.runtime.mutableStateOf

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

        gameBoard = GameBoard(
            players = players,
            turnOrder = turnOrder,
            currentTurn = 1,
            selectedPlayerId = 1,
            centralMoney = 0,
            speedDieMode = false,
            freeParkingRule = false,
        )

        gameBoard.createModels()

        player = gameBoard.getPlayerById(1)!!
    }

    //JENNY
    @Test
    fun `player should start the game with the standard rules, at Go cell and with $1500`() {
        val player = gameBoard.getPlayerById(1)

        assertTrue(player != null)
        if (player != null) {
            assertEquals(player.numCell, 1, "Player start atGO cel#1")
            assertEquals(player.totalMoney, 1500, "Player has $1500.00")

            println("--------> Player '${player.name}' starts at Go (cell 1) with \$${player.totalMoney}.")
        }
    }
    // JENNY
    @Test
    fun `movePlayer should move player correctly`() {
        val initialPosition = player.numCell
        GameEngine.movePlayer(gameBoard, player.id, 5)

        assertEquals(initialPosition + 5, player.numCell)
    }

    // JENNY
    // As a player, I can collect the base rent when someone lands on my property.
    @Test
    fun `collectRent should deduct and add rent correctly`() {
        val owner = Player(id = 2, name = "Player2", token = Token.DOG)
        val property = Property(id = 1, name = "San Diego Drive", baseRent = 50, price = 60, color = PropertyColor.BLUE)
        owner.propertyIds = mutableListOf(1)
        val cell = Cell(numCell = 5, propertyId = 1)
        gameBoard.players = arrayOf(player, owner)
        gameBoard.cells = arrayOf(cell)
        gameBoard.properties = arrayOf(property)

        player.numCell = 5
        val message = mutableStateOf("")

        println("Before rent collection:")
        println("Player totalMoney: ${player.totalMoney}")
        println("Owner totalMoney: ${owner.totalMoney}")
        GameEngine.collectRent(gameBoard, player.id, message)

        println("After rent collection:")
        println("Player totalMoney: ${player.totalMoney}")
        println("Owner totalMoney: ${owner.totalMoney}")
        assertEquals(1450, player.totalMoney)
        assertEquals(1550, owner.totalMoney)
    }

    // JENNY
    @Test
    fun `collectRent should not be able to deduct and add rent correctly because property is mortgaged`() {
        val owner = Player(id = 2, name = "Player2", token = Token.DOG)
        val property = Property(id = 1, name = "San Diego Drive", baseRent = 50, price = 60, color = PropertyColor.BLUE, isMortgaged = true)
        owner.propertyIds = mutableListOf(1)
        val cell = Cell(numCell = 5, propertyId = 1)
        gameBoard.players = arrayOf(player, owner)
        gameBoard.cells = arrayOf(cell)
        gameBoard.properties = arrayOf(property)

        player.numCell = 5
        val message = mutableStateOf("")
        GameEngine.collectRent(gameBoard, player.id, message)

        assertEquals(1500, player.totalMoney)
        assertEquals(1500, owner.totalMoney)
    }

    // JENNY
    @Test
    fun `collectRent should collect double base rent when someone lands on my property and I own all color properties `() {
        val owner = Player(id = 2, name = "Player2", token = Token.DOG)
        gameBoard.players = arrayOf(player, owner)
        gameBoard.createModels()
        owner.propertyIds = mutableListOf(1, 2)

        player.numCell = 4
        val message = mutableStateOf("")
        GameEngine.collectRent(gameBoard, player.id, message)

        assertEquals(1492, player.totalMoney)
        assertEquals(1508, owner.totalMoney)
    }

    // JENNY BACKLOG
    @Test
    fun `As a player, I can collect the appropriate rent for utilities based on how many in the set I own`() {
        val owner = Player(id = 2, name = "Player2", token = Token.DOG)
        val utility = Property(id = 1, name = "Car Company", baseRent = 100, price = 150, isUtility = true)
        val utility2 = Property(id = 2, name = "Water Works", baseRent = 100, price = 150, isUtility = true)
        val cell = Cell(numCell = 5, propertyId = 1)
        val cell2 = Cell(numCell = 6, propertyId = 2)
        gameBoard.players = arrayOf(player, owner)
        gameBoard.cells = arrayOf(cell, cell2)
        gameBoard.properties = arrayOf(utility, utility2)

        player.numCell = 5
        owner.propertyIds = mutableListOf(1, 2)
        println("--------> Before: ${owner.name} has \$${owner.totalMoney}, ${player.name} has \$${player.totalMoney}")

        GameEngine.collectUtilities(gameBoard, player.id)

        println("--------> After: ${owner.name} has \$${owner.totalMoney}, ${player.name} has \$${player.totalMoney}")
        println("${owner.name} owns utilities with IDs: ${owner.propertyIds.joinToString(", ")}")
        println("${player.name} landed on cell ${player.numCell}, which is property ID: ${cell.propertyId}")

        assertEquals(1300, player.totalMoney)
        assertEquals(1700, owner.totalMoney)
    }

    // JENNY BACKLOG
    @Test
    fun `As a player, I can collect the appropriate rent for railroads based on how many in the set I own`() {
        val owner = Player(id = 2, name = "Player2", token = Token.DOG)
        val railroad = Property(id = 1, name = "Railroad", baseRent = 100, price = 200, isRailRoad = true)
        val railroad2 = Property(id = 2, name = "Manhattan Railroad", baseRent = 100, price = 200, isRailRoad = true)
        val cell = Cell(numCell = 5, propertyId = 1)
        val cell2 = Cell(numCell = 6, propertyId = 2)
        gameBoard.players = arrayOf(player, owner)
        gameBoard.cells = arrayOf(cell, cell2)
        gameBoard.properties = arrayOf(railroad, railroad2)

        player.numCell = 5
        owner.propertyIds = mutableListOf(1, 2)

        GameEngine.collectRailroads(gameBoard, player.id)

        assertEquals(1300, player.totalMoney)
        assertEquals(1700, owner.totalMoney)
    }

    // JENNY BACKLOG
    @Test
    fun `As a player, I can take appropriate action when landing on a non-property space`() {
        val incomeTaxCell = Cell(numCell = 4, isIncomeTax = true)
        val luxuryTaxCell = Cell(numCell = 6, isLuxuryTax = true)
        val goToJailCell = Cell(numCell = 10, isGoToJail = true)

        val playerIncomeTax = Player(id = 1, name = "IncomeTaxPlayer", token = Token.CAR, totalMoney = 1500, numCell = 4)
        val playerLuxuryTax = Player(id = 2, name = "LuxuryTaxPlayer", token = Token.DOG, totalMoney = 1500, numCell = 6)
        val playerGoToJail = Player(id = 3, name = "JailPlayer", token = Token.BATTLESHIP, totalMoney = 1500, numCell = 10)

        gameBoard.players = arrayOf(playerIncomeTax, playerLuxuryTax, playerGoToJail)
        gameBoard.cells = arrayOf(incomeTaxCell, luxuryTaxCell, goToJailCell)

        val engine = GameEngine
        val message = mutableStateOf("")

        engine.landingAction(gameBoard, playerIncomeTax.id, message)
        println("--------> The ${playerIncomeTax.name} landed on Income Tax. Total money after tax (-150): /$${playerIncomeTax.totalMoney}")
        assertEquals(1350, playerIncomeTax.totalMoney)

        engine.landingAction(gameBoard, playerLuxuryTax.id, message)
        println("--------> The ${playerLuxuryTax.name} landed on Luxury Tax. total money after tax (-200): /$${playerLuxuryTax.totalMoney}")
        assertEquals(1300, playerLuxuryTax.totalMoney)

        engine.landingAction(gameBoard, playerGoToJail.id, message)
        assertTrue(playerGoToJail.inJail)
        assertEquals(23, playerGoToJail.numCell)
        println("--------> The ${playerGoToJail.name} was sent to jail. In jail: ${playerGoToJail.inJail}, Position: ${playerGoToJail.numCell}")
    }

    // JENNY BACKLOG
    @Test
    fun `As a player, I can purchase an available property`() {
        val engine = GameEngine

        val player = gameBoard.getPlayerById(1)
        assertTrue(player != null)
        player!!.numCell = 2
        val message = mutableStateOf("")
        engine.purchaseProperty(gameBoard, playerId = 1, message)


        val property = gameBoard.getPropertyById(1)
        assertTrue(property != null)

        assertTrue(player.propertyIds.contains(property!!.id))
        assertEquals(1440, player.totalMoney)

        println("--------> Player '${player.name}' purchased '${property.name}' for \$${property.price}.")
    }

    // JENNY BACKLOG
    @Test
    fun `As a player, I can't purchase an available property because I don't have enough money`() {
        val engine = GameEngine

        val player = gameBoard.getPlayerById(1)
        assertTrue(player != null)
        player!!.numCell = 2
        player.totalMoney = 30
        val message = mutableStateOf("")
        engine.purchaseProperty(gameBoard, playerId = 1, message )

        val property = gameBoard.getPropertyById(1)
        assertTrue(property != null)

        assertFalse(player.propertyIds.contains(property!!.id))
        assertEquals(30, player.totalMoney)
        println("--------> Player '${player.name}' can not purchase '${property!!.name}' because he only has \$${player.totalMoney}.")
    }

    // Maria backlog
    @Test
    fun `player should go to jail after rolling three consecutive doubles`() {
        player.consecutiveDoubles = 2
        val currentTurn = mutableStateOf(0)
        val message = mutableStateOf("")

        GameEngine.handleTurnWithDice(
            board = gameBoard,
            currentTurn = currentTurn,
            message = message,
            die1 = 4,
            die2 = 4
        )

        assertTrue(player.inJail, "Player should be in jail after 3 doubles")
        assertEquals(23, player.numCell, "Player should be on Jail cell (23)")
        assertEquals(0, player.consecutiveDoubles, "consecutiveDoubles should be reset after jail")

        println("--------> Player '${player.name}' rolled 3 doubles and was sent to jail (cell ${player.numCell}).")
    }

}


