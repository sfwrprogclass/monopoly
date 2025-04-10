package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.Token
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DiceTest {

    private lateinit var gameBoard: GameBoard
    private lateinit var dice1: Dice
    private lateinit var dice2: Dice
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
        dice1 = Dice()
        dice2 = Dice()
    }


    @Test
    fun `roll should return number between 1 and 6`() {
        val result = dice1.roll()
        assertTrue(result in 1..6, "Dice roll should return a number between 1 and 6")
    }

    @Test
    fun `player should advance correctly based on dice roll`() {

        val roll1 = dice1.roll()
        val roll2 = dice2.roll()
        val totalMove = roll1 + roll2

        val previousNumCell = player.numCell
        player.numCell += totalMove

        assertTrue(totalMove in 2..12, "Sum of two dice should be between 2 and 12")
        assertEquals(totalMove + previousNumCell, player.numCell, "Player should have advanced by the total of two dice rolls")
    }

    @Test
    fun testDiceRollerUpdatesPlayerPosition() {

        val initialPosition = player.numCell

        val dice1 = dice1.roll()
        val dice2 = dice2.roll()
        val totalMove = dice1 + dice2
        player.numCell += totalMove

        println("Player initial position: $initialPosition")
        println("Player rolled the dice and got : $dice1 and $dice2 (Total: $totalMove)")
        println("Player updated position: ${player.numCell}")

        assertEquals(initialPosition + totalMove, player.numCell, "Player position should increase after rolling dice" )
    }

    @Test
    fun `user should be able to track token location` () {

        val initialPosition = player.numCell
        println("PLayer 1 initial position: $initialPosition")

        val value1 = dice1.roll()
        val value2 = dice2.roll()
        val totalMove = value1 + value2
        player.numCell += totalMove
        val cell = gameBoard.getCellById(player.numCell)

        if (cell != null) {
            println("Player1 got: $value1 and $value2. Player1 CurrentPosition: ${player.numCell} ${cell.getName(gameBoard)}")
        } else {
            println("Player1 got: $value1 and $value2. Unfortunately cell wasn't found")
        }

        assertEquals( initialPosition + totalMove, player.numCell, "Player should move correctly after rolling dice")
    }

}

