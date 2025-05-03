package com.dallascollege.monopoly.enums

enum class ActionType(val text: String) {
    PAY_RENT("Pay rent"),
    PURCHASE_PROPERTY("Purchase property"),
    AUCTION_PROPERTY("Auction Property"),
    GO_TO_JAIL("Go to jail"),
    PAY_BANK("Pay bank"),
    BUY_HOUSE("Buy house"),
    UPGRADE_TO_HOTEL("Upgrade 4 houses to hotel"),
    SELL_HOUSE("Sell house"),
    DOWNGRADE_TO_HOUSES("Downgrade hotel to 4 houses"),
    MORTGAGE_PROPERTY("Mortgage property"),
    UNMORTGAGE_PROPERTY("Unmortgage property"),
    SURRENDER("Surrender"),
    GET_OUT_OF_JAIL("Get out of jail"),
    SKIP("Skip"),
    FINISH_TURN("Finish turn");

    override fun toString(): String {
        return text
    }
}