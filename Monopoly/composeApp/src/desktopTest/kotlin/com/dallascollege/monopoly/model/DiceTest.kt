package com.dallascollege.monopoly.model

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DiceTest {

    private val dice = Dice()

    @Test
    fun `roll should return number between 1 and 6`() {
        val result = dice.roll()
        assertTrue(result in 1..6, "Dice roll should return a number between 1 and 6")
    }
}
