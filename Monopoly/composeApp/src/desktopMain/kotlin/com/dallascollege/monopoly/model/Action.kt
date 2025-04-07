package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.ActionType

class Action(
    name: String,
    description: String = "",
    actionType: ActionType,
    propertyId: Int = -1,
    money: Int = 0,
    quantity: Int = 0
) {
    var name: String = name

    var description: String = description

    var actionType: ActionType = actionType

    var propertyId: Int = propertyId

    var money: Int = money

    var quantity: Int = quantity
}
