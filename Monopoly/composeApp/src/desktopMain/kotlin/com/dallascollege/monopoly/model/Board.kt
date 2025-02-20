package com.dallascollege.monopoly.model

class Board {
    val tiles: List<Tile> = listOf(
        Tile.SpecialSpace(Tile.SpecialType.GO),
        Tile.Property(1, 60, 2),  // Example property
        Tile.CardSpace(Tile.CardType.COMMUNITY_CHEST),
        Tile.Property(3, 60, 4),
        Tile.SpecialSpace(Tile.SpecialType.JAIL),
        // Add more tiles to complete the board (Total 40)
    )

    fun getTile(position: Int): Tile {
        return tiles[position % tiles.size]
    }
}
