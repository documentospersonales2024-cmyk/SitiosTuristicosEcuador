package com.example.sitiosturisticosecuador.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sitiosturisticosecuador.data.local.TouristPlaceEntity

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onDetail: (String) -> Unit
) {
    val favorites by viewModel.favorites.collectAsState()

    if (favorites.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No existen lugares favoritos", style = MaterialTheme.typography.titleMedium)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(favorites) { place ->
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    onClick = { onDetail(place.id) }
                ) {
                    ListItem(
                        headlineContent = { Text(place.name) },
                        supportingContent = { Text(place.province) },
                        trailingContent = {
                            IconButton(onClick = { viewModel.removeFavorite(place) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    )
                }
            }
        }
    }
}