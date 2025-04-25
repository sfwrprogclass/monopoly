package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor
import com.dallascollege.monopoly.enums.Token
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayerTest {

    private lateinit var board: GameBoard

    @BeforeEach
    fun setup() {
        val players = arrayOf(
            Player(
                name = "Jenny",
                token = Token.BOOT,
                propertyIds = mutableListOf(2, 8, 11)
            )
        )

        val turnOrder = arrayOf(1)

        board = GameBoard(
            players = players,
            turnOrder = turnOrder,
            currentTurn = 1,
            selectedPlayerId = 1,
            centralMoney = 0,
            speedDieMode = false,
            freeParkingRule = false,
        )

        board.createModels()
    }
    // JENNY
    @Test
    fun `getProperties returns only player's owned properties`() {
        val player = board.players[0]
        val properties = player.getProperties(board)
        assertEquals(3, properties.size)
        val ids = properties.map { it.id }
        assertTrue(ids.containsAll(listOf(2, 8, 11)))
    }
    // JENNY
    @Test
    fun `getUtilities returns only utilities the player owns`() {
        val player = board.players[0]
        val utilities = player.getUtilities(board)

        assertEquals(1, utilities.size)
        assertEquals(8, utilities[0].id)
    }
    // JENNY
    @Test
    fun `getRailroads returns only railroads the player owns`() {
        val player = board.players[0]
        val railroads = player.getRailroads(board)
        val ids = railroads.map { it.id }
        assertEquals(1, railroads.size)
        assertTrue(ids.containsAll(listOf(11)), "Checked that we got all the railroads owned by player")
    }
    // JENNY
    @Test
    fun `player properties are mutable`() {
        val player = board.players[0]
        player.totalMoney = 1200
        player.inJail = true
        assertEquals(1200, player.totalMoney)
        assertTrue(player.inJail)
    }
}
