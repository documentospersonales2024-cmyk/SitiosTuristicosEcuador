package com.example.sitiosturisticosecuador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sitiosturisticosecuador.data.datastore.SettingDataStore
import com.example.sitiosturisticosecuador.data.local.TouristDatabase
import com.example.sitiosturisticosecuador.data.repository.TouristRepository
import com.example.sitiosturisticosecuador.ui.components.BottomBar
import com.example.sitiosturisticosecuador.ui.screens.favorites.FavoritesViewModel
import com.example.sitiosturisticosecuador.ui.screens.home.HomeViewModel
import com.example.sitiosturisticosecuador.navigation.AppNavigation
import com.example.sitiosturisticosecuador.ui.screens.settings.SettingsScreen
import com.example.sitiosturisticosecuador.ui.screens.location.LocationScreen
import com.example.sitiosturisticosecuador.ui.theme.SitiosturisticosecuadorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicialización de fuentes de datos locales y repositorios
        val database = TouristDatabase.getDatabase(applicationContext)
        val repository = TouristRepository(database.touristPlaceDao())
        val settingDataStore = SettingDataStore(applicationContext)

        val homeViewModel = HomeViewModel(repository)
        val favoritesViewModel = FavoritesViewModel(repository)

        setContent {
            val darkMode by settingDataStore.darkModeFlow.collectAsState(initial = false)

            // AQUÍ ESTÁ EL CAMBIO: Usamos tu tema personalizado
            SitiosturisticosecuadorTheme(darkTheme = darkMode) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: "home"

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBar(
                            current = currentRoute,
                            onClick = { route ->
                                if (currentRoute != route) {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        favoritesViewModel = favoritesViewModel,
                        settingDataStore = settingDataStore,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}