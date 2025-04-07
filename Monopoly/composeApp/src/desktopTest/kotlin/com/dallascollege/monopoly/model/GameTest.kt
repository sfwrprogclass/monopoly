package com.dallascollege.monopoly.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.Date

class GameTest {

    @Test
    fun `game should store and return correct values`() {
        val testDate = Date()
        val game = Game(
            position = 7,
            owedBank = true,
            owedPlayer = false,
            date = testDate
        )

        assertEquals(7, game.position)
        assertTrue(game.owedBank)
        assertFalse(game.owedPlayer)
        assertEquals(testDate, game.date)
    }

    @Test
    fun `game should allow updating values`() {
        val game = Game(
            position = 0,
            owedBank = false,
            owedPlayer = false,
            date = Date()
        )

        val newDate = Date()

        game.position = 12
        game.owedBank = true
        game.owedPlayer = true
        game.date = newDate

        assertEquals(12, game.position)
        assertTrue(game.owedBank)
        assertTrue(game.owedPlayer)
        assertEquals(newDate, game.date)
    }
}
