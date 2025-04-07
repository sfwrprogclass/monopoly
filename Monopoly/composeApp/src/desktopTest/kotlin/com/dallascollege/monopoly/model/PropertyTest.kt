package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PropertyTest {

    @Test
    fun `property is correctly initialized with default values`() {
        val property = Property(
            id = 1,
            name = "San Diego Drive",
            price = 60,
            color = PropertyColor.BROWN
        )

        assertEquals(1, property.id)
        assertEquals("San Diego Drive", property.name)
        assertEquals(60, property.price)
        assertEquals(PropertyColor.BROWN, property.color)
        assertFalse(property.isPurchased)
        assertEquals(0, property.baseRent)
        assertFalse(property.isUtility)
        assertFalse(property.isRailRoad)
        assertEquals(0, property.numHouses)
        assertEquals(0, property.numHotels)
        assertFalse(property.isMortgaged)
    }

    @Test
    fun `property is correctly initialized with all custom values`() {
        val property = Property(
            id = 11,
            name = "Manhattan Railroad",
            price = 200,
            color = PropertyColor.WHITE,
            isPurchased = true,
            baseRent = 75,
            isUtility = true,
            isRailRoad = false,
            numHouses = 1,
            numHotels = 0,
            isMortgaged = true
        )

        assertEquals(11, property.id)
        assertEquals("Manhattan Railroad", property.name)
        assertEquals(200, property.price)
        assertEquals(PropertyColor.WHITE, property.color)
        assertTrue(property.isPurchased)
        assertEquals(75, property.baseRent)
        assertTrue(property.isUtility)
        assertFalse(property.isRailRoad)
        assertEquals(1, property.numHouses)
        assertEquals(0, property.numHotels)
        assertTrue(property.isMortgaged)
    }

    @Test
    fun `property setters work correctly`() {
        val property = Property(id = 3, name = "California Drive", price = 160, color = PropertyColor.PINK)

        property.name = "Modified California Drive"
        property.price = 250
        property.color = PropertyColor.RED
        property.isPurchased = true
        property.baseRent = 50
        property.isUtility = false
        property.isRailRoad = true
        property.numHouses = 2
        property.numHotels = 1
        property.isMortgaged = true

        assertEquals("Modified California Drive", property.name)
        assertEquals(250, property.price)
        assertEquals(PropertyColor.RED, property.color)
        assertTrue(property.isPurchased)
        assertEquals(50, property.baseRent)
        assertFalse(property.isUtility)
        assertTrue(property.isRailRoad)
        assertEquals(2, property.numHouses)
        assertEquals(1, property.numHotels)
        assertTrue(property.isMortgaged)
    }
}
