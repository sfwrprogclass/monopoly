package com.dallascollege.monopoly.ui.property

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dallascollege.monopoly.model.Property
import androidx.compose.material.Text

@Composable
fun PropertyView(property: Property) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "🏠 ${property.name}")
            Text(text = "💰 Price: \$${property.price}")
            Text(text = "📊 Base rent: \$${property.baseRent}")
            Text(text = "🎨 Color: ${property.color.name}")
            Text(text = "Number of houses: ${property.numHouses}")
            Text(text = "Number of hotels: ${property.numHotels}")
        }
    }
}