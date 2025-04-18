package com.dallascollege.monopoly.enums

enum class ActionType(val text: String) {
    PAY_RENT("Pay rent"),
    PURCHASE_PROPERTY("Purchase property"),
    GO_TO_JAIL("Go to jail"),
    PAY_BANK("Pay bank"),
    BUY_HOUSE("Buy house"),
    BUY_HOTEL("Buy hotel"),
    SELL_HOUSE("Sell house"),
    SELL_HOTEL("Sell hotel"),
    MORTGAGE_PROPERTY("Mortgage property"),
    SURRENDER("Surrender"),
    GET_OUT_OF_JAIL("Get out of jail"),
    SKIP("Skip"),
    FINISH_TURN("Finish turn"),
    FINISH_GAME("Finish game");

    override fun toString(): String {
        return text
    }
}