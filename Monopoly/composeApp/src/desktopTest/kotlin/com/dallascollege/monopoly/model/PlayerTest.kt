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
        board = GameBoard(
            properties = arrayOf(
                Property(id = 1, name = "San Diego Drive", price = 60, color = PropertyColor.BROWN),
                Property(id = 2, name = "Kansas Drive", price = 90, color = PropertyColor.BROWN),
                Property(id = 3, name = "Beverly RailRoad", price = 200, isUtility = true),
                Property(id = 4, name = "Vermont Drive", price = 120, PropertyColor.LIGHT_BLUE),
                Property(id = 5, name = "Phoenix Drive", price = 130, PropertyColor.LIGHT_BLUE),
                Property(id = 11, name = "Manhattan Railroad", price = 200)
            ),
            players = arrayOf(
                Player(
                    name = "Jenny",
                    token = Token.BOOT,
                    propertyIds = arrayOf(2, 3, 11)
                )
            )
        )
    }

    @Test
    fun `getProperties returns only player's owned properties`() {
        val player = board.players[0]
        val properties = player.getProperties(board)
        assertEquals(3, properties.size)
        val ids = properties.map { it.id }
        assertTrue(ids.containsAll(listOf(2, 3, 11)))
    }

    @Test
    fun `getUtilities returns only utilities the player owns`() {
        val player = board.players[0]
        val utilities = player.getUtilities(board)
        assertEquals(1, utilities.size)
        assertEquals(3, utilities[0].id)
    }

    @Test
    fun `getRailroads returns only railroads the player owns`() {
        val player = board.players[0]
        val railroads = player.getRailroads(board)
        assertEquals(1, railroads.size)
        val ids = railroads.map { it.id }
        assertTrue(ids.containsAll(listOf(11)))
    }

    @Test
    fun `player properties are mutable`() {
        val player = board.players[0]
        player.totalMoney = 1200
        player.inJail = true
        assertEquals(1200, player.totalMoney)
        assertTrue(player.inJail)
    }
}
