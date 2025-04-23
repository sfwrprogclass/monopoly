package com.dallascollege.monopoly.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CellTest {
    //Jenny
    @Test
    fun `cell should correctly store its default values`() {
        val cell = Cell()

        assertEquals(1, cell.numCell)
        assertEquals(-1, cell.propertyId)
        assertFalse(cell.isGoToJail)
        assertFalse(cell.isLuxuryTax)
        assertFalse(cell.isIncomeTax)
        assertFalse(cell.isChance)
        assertFalse(cell.isCommunityChest)
        assertFalse(cell.isParking)
        assertFalse(cell.isCollectSalary)
        assertFalse(cell.isVisitingJail)
        assertFalse(cell.isProperty())  // Because propertyId = -1
    }
    //Jenny
    @Test
    fun `cell should identify as property when propertyId is not -1`() {
        val cell = Cell(propertyId = 5)
        assertTrue(cell.isProperty())
    }
    // Jenny
    @Test
    fun `cell should set and return correct values`() {
        val cell = Cell(
            numCell = 10,
            propertyId = 3,
            isGoToJail = true,
            isLuxuryTax = true,
            isIncomeTax = false,
            isChance = true,
            isCommunityChest = true,
            isParking = true,
            isCollectSalary = true,
            isVisitingJail = true
        )

        assertEquals(10, cell.numCell)
        assertEquals(3, cell.propertyId)
        assertTrue(cell.isGoToJail)
        assertTrue(cell.isLuxuryTax)
        assertFalse(cell.isIncomeTax)
        assertTrue(cell.isChance)
        assertTrue(cell.isCommunityChest)
        assertTrue(cell.isParking)
        assertTrue(cell.isCollectSalary)
        assertTrue(cell.isVisitingJail)
        assertTrue(cell.isProperty())
    }
}
