package com.dallascollege.monopoly.model

sealed class Tile(val name: String) {
    data class Property(
        val id: Int,
        val price: Int,
        val rent: Int,
        var owner: Player? = null
    ) : Tile("Property #$id")

    data class SpecialSpace(val type: SpecialType) : Tile(type.name)

    data class CardSpace(val type: CardType) : Tile(type.name)

    enum class SpecialType {
        GO, JAIL, FREE_PARKING, GO_TO_JAIL
    }

    enum class CardType {
        CHANCE, COMMUNITY_CHEST
    }
}