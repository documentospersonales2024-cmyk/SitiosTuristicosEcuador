package com.example.sitiosturisticosecuador.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomItem(val title: String, val icon: ImageVector, val route: String)

@Composable
fun BottomBar(current: String, onClick: (String) -> Unit) {
    val items = listOf(
        BottomItem("Inicio", Icons.Default.Home, "home"),
        BottomItem("Favoritos", Icons.Default.Favorite, "favorites"),
        BottomItem("Ubicación", Icons.Default.LocationOn, "location"),
        BottomItem("Ajustes", Icons.Default.Settings, "settings")
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = current == item.route,
                onClick = { onClick(item.route) },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}