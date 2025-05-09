package com.dallascollege.monopoly.model

import com.dallascollege.monopoly.enums.PropertyColor

class GameBoard(
    var players: Array<Player>,
    var turnOrder: Array<Int> = emptyArray(),
    var currentTurn: Int = 0,
    var selectedPlayerId: Int = 1,
    var centralMoney: Int = 0,
    val speedDieMode: Boolean = false,
    val freeParkingRule: Boolean = false,
    var cells: Array<Cell> = emptyArray(),
    var properties: Array<Property> = emptyArray(),
    var cards: Array<Card> = emptyArray(),
) {

    private fun createCells() {
        val cells = arrayOf<Cell>()
        val cellList = cells.toMutableList()
        var numCell = 1

        cellList.add(
            Cell(
                numCell = numCell,
                isCollectSalary = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 1,
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isCommunityChest = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 2
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isIncomeTax = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 3
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 4
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isChance = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 5
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 6
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isVisitingJail = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 7
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 8
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 9
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 10
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 11
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 12
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isCommunityChest = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 13
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 14
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isParking = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 15
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isChance = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 16
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 17
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 18
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 19
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 20
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 21
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 22
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isGoToJail = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 23
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 24
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isCommunityChest = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 25
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 26
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isChance = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 27
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                isLuxuryTax = true
            )
        )
        numCell++
        cellList.add(
            Cell(
                numCell = numCell,
                propertyId = 28
            )
        )

        this.cells = cellList.toTypedArray()
    }

    private fun createProperties() {
        val properties = arrayOf<Property>()
        val propertyList = properties.toMutableList()
        var id = 1

        propertyList.add(
            Property(
                id = id,
                name = "San Diego Drive",
                price = 60,
                baseRent = 2,
                color = PropertyColor.BROWN,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Kansas Drive",
                price = 60,
                baseRent = 4,
                color = PropertyColor.BROWN,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Beverly RailRoad",
                price = 200,
                baseRent = 16,
                isRailRoad = true,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Vermont Drive",
                price = 100,
                baseRent = 32,
                color = PropertyColor.LIGHT_BLUE,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Phoenix Drive",
                price = 100,
                baseRent = 32,
                color = PropertyColor.LIGHT_BLUE,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Boston Drive",
                price = 120,
                baseRent = 36,
                color = PropertyColor.LIGHT_BLUE,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Olivia Gardens",
                price = 140,
                baseRent = 40,
                color = PropertyColor.PINK,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Car Company",
                price = 150,
                baseRent = 40,
                isUtility = true,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "California Drive",
                price = 160,
                baseRent = 44,
                color = PropertyColor.PINK,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "States Drive",
                price = 140,
                baseRent = 44,
                color = PropertyColor.PINK,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Manhattan Railroad",
                price = 200,
                baseRent = 16,
                isRailRoad = true,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Bethany Drive",
                price = 180,
                baseRent = 48,
                color = PropertyColor.ORANGE,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "New York Drive",
                price = 200,
                baseRent = 50,
                color = PropertyColor.ORANGE,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Atlanta Drive",
                price = 200,
                baseRent = 50,
                color = PropertyColor.ORANGE,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Almond Drive",
                price = 200,
                baseRent = 50,
                color = PropertyColor.RED,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Clement Drive",
                price = 200,
                baseRent = 50,
                color = PropertyColor.RED,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Pacific Drive",
                price = 260,
                baseRent = 60,
                color = PropertyColor.RED,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Water Works",
                price = 60,
                baseRent = 2,
                isUtility = true,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Rodeo Drive",
                price = 260,
                baseRent = 60,
                color = PropertyColor.YELLOW,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Nashville Drive",
                price = 260,
                baseRent = 60,
                color = PropertyColor.YELLOW,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Railroad",
                price = 200,
                baseRent = 16,
                isRailRoad = true,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Oakville",
                price = 230,
                baseRent = 50,
                color = PropertyColor.YELLOW,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Atlantic Drive",
                price = 300,
                baseRent = 75,
                color = PropertyColor.GREEN,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Clement Drive", //change name because is duplicated in RED property color
                price = 300,
                baseRent = 75,
                color = PropertyColor.GREEN,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Riverside",
                price = 250,
                baseRent = 70,
                color = PropertyColor.GREEN,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Short line",
                price = 200,
                baseRent = 16,
                isRailRoad = true,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Folklore Heights",
                price = 200,
                baseRent = 16,
                color = PropertyColor.BLUE,
            )
        )
        id++
        propertyList.add(
            Property(
                id = id,
                name = "Salt Lake",
                price = 350,
                baseRent = 100,
                color = PropertyColor.BLUE,
            )
        )

        this.properties = propertyList.toTypedArray()
    }

    private fun createCards() {
        val cards = arrayOf<Card>()
        val cardList = cards.toMutableList()

        //Chance cards
        cardList.add(
            //advanced to go
            Card(
                isChance = true,
                advancedToGo = true,
                message = "Advance to Go – Advance directly to Go. Collect \$200! 🤑"
            )
        )
        cardList.add(
            Card(
                isChance = true,
                amount = 200,
                message = "Bank error in your favor – Receive \$200 from the bank 💵"
            ),
        )
        cardList.add(
            Card(
                isChance = true,
                goToJail = true,
                message = "Go to Jail 🚨 – Go directly to Jail. Do not pass Go. Do not collect \$200"
            ),
        )
        cardList.add(
            Card(
                isChance = true,
                isPayment = true,
                amount = 150,
                message = "Pay school fees 🏫 – Pay \$150"
            ),
        )
        cardList.add(
            Card(
                isChance = true,
                amount = 150,
                message = "Your building loan matures – Receive \$150 💵"
            ),
        )
        cardList.add(
            Card(
                isChance = true,
                isPayment = true,
                amount = 50,
                message = "Speeding fine – Pay \$50"
            ),
        )
        cardList.add(
            Card(
                isChance = true,
                back3Cells = true,
                message = "Go back 3 cells ➡️➡️➡️"
            ),
        )
        cardList.add(
            Card(
                isChance = true,
                getOutOfJail = true,
                message = "Get Out of Jail Free!  This card can be used to get out of Jail at any time without paying the fine or rolling for doubles"
            ),
        )

        //Community chest cards
        cardList.add(
            Card(
                isPayment = true,
                amount = 50,
                message = "Doctor’s fees 👨🏻‍⚕️ – Pay \$50"
            ),
        )
        cardList.add(
            Card(
                amount = 100,
                message = "You inherit \$100 – Receive \$100 💵"
            ),
        )
        cardList.add(
            Card(
                amount = 100,
                message = "Life insurance matures – Receive \$100 💵"
            ),
        )
        cardList.add(
            Card(
                isPayment = true,
                amount = 100,
                message = "Pay hospital fees 🏥 – Pay \$100 💸"
            ),
        )
        cardList.add(
            Card(
                amount = 25,
                message = "Receive for services – Collect \$25 💵"
            ),
        )
        cardList.add(
            Card(
                amount = 10,
                message = "You won second prize in a beauty contest – Collect \$10 💵"
            ),
        )
        cardList.add(
            Card(
                amount = 50,
                message = "From sale of stock you get \$50 – Receive \$50 from stock sale 💵"
            ),
        )
        cardList.add(
            Card(
                amount = 100,
                message = "Holiday fund matures – Receive \$100 💵"
            ),
        )

        this.cards = cardList.toTypedArray()
    }

    fun createModels() {
        createProperties()
        createCells()
        createCards()
    }

    //NEW Methods to access objects by id
    fun getPlayerById(id: Int): Player? {
        return players.find { it.id == id }
    }

    fun getPropertyById(id: Int): Property? {
        return properties.find { it.id == id }
    }

    fun getCellById(numCell: Int): Cell? {
        return cells.find { it.numCell == numCell }
    }

    fun getPropertyOwner(property: Property): Player? {
        return players.find( { it.propertyIds.contains(property.id)})
    }

    fun isPropertyOwned(propertyId: Int): Boolean {
        return players.any { it.propertyIds.contains(propertyId) }
    }

    fun getChanceCards(): Array<Card> {
        return this.cards.filter{ it.isChance}.toTypedArray()
    }

    fun getCommunityChestCards(): Array<Card> {
        return this.cards.filter{ !it.isChance}.toTypedArray()
    }
}

