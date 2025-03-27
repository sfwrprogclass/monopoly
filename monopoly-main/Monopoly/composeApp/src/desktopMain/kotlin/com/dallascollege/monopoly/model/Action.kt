package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.ActionType

class Action(
    name: String,
    description: String,
    actionType: ActionType,
    propertyId: Int,
    money: Int,
    quantity: Int
) {
    var name: String = name
        get() = field
        set(value) {
            field = value
        }

    var description: String = description
        get() = field
        set(value) {
            field = value
        }

    var actionType: ActionType = actionType
        get() = field
        set(value) {
            field = value
        }

    var propertyId: Int = propertyId
        get() = field
        set(value) {
            field = value
        }

    var money: Int = money
        get() = field
        set(value) {
            field = value
        }

    var quantity: Int = quantity
        get() = field
        set(value) {
            field = value
        }
}
