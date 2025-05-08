package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.ActionType

class Action(
   val name: String,
   val description: String = "",
   val actionType: ActionType,
   var propertyId: Int = -1,
   var money: Int = 0,
   var quantity: Int = 0
)