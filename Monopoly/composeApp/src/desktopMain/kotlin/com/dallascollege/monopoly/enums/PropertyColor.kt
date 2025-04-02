package com.dallascollege.monopoly.enums

import androidx.compose.ui.graphics.Color


enum class PropertyColor(
    val propertiesInGroup: Int,
    val colorCode: Color // Added for visual representation
) {
    BROWN(2, Color(0xFF8B4513)),
    LIGHT_BLUE(3, Color(0xFFADD8E6)),
    PINK(3, Color(0xFFFFC0CB)),
    ORANGE(3, Color(0xFFFFA500)),
    RED(3, Color(0xFFFF0000)),
    YELLOW(3, Color(0xFFFFFF00)),
    GREEN(3, Color(0xFF008000)),
    BLUE(2, Color(0xFF0000FF)),
    UTILITY(2, Color.LightGray), // Neutral color
    RAILROAD(4, Color.DarkGray), // Neutral color
    NONE(0, Color.Transparent);

    companion object {
        fun getColorGroupSize(color: PropertyColor): Int {
            return when (color) {
                BROWN, LIGHT_BLUE, PINK, ORANGE,
                RED, YELLOW, GREEN, BLUE -> 3

                else -> 0
            }
        }
    }
}