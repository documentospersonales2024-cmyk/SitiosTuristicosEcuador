package com.example.sitiosturisticosecuador.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sitiosturisticosecuador.data.datastore.SettingDataStore
import com.example.sitiosturisticosecuador.data.local.TouristDatabase
import com.example.sitiosturisticosecuador.data.repository.TouristRepository
import com.example.sitiosturisticosecuador.ui.screens.detail.DetailScreen
import com.example.sitiosturisticosecuador.ui.screens.detail.DetailUiState
import com.example.sitiosturisticosecuador.ui.screens.detail.DetailViewModel
import com.example.sitiosturisticosecuador.ui.screens.favorites.FavoritesScreen
import com.example.sitiosturisticosecuador.ui.screens.favorites.FavoritesViewModel
import com.example.sitiosturisticosecuador.ui.screens.home.HomeScreen
import com.example.sitiosturisticosecuador.ui.screens.home.HomeViewModel
import com.example.sitiosturisticosecuador.ui.screens.location.LocationScreen
import com.example.sitiosturisticosecuador.ui.screens.settings.SettingsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    favoritesViewModel: FavoritesViewModel,
    settingDataStore: SettingDataStore,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val database = TouristDatabase.getDatabase(context)
    val repository = TouristRepository(database.touristPlaceDao())

    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            HomeScreen(viewModel = homeViewModel, onPlaceClick = { id -> navController.navigate("detail/$id") })
        }
        composable("favorites") {
            FavoritesScreen(viewModel = favoritesViewModel, onDetail = { id -> navController.navigate("detail/$id") })
        }
        composable("location") {
            LocationScreen()
        }
        composable("settings") {
            SettingsScreen(settingDataStore = settingDataStore)
        }
        composable("detail/{placeId}") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
            val viewModel = androidx.lifecycle.viewmodel.compose.viewModel {
                DetailViewModel(repository, placeId)
            }
            val uiState by viewModel.uiState.collectAsState()

            when (val state = uiState) {
                is DetailUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is DetailUiState.Success -> {
                    DetailScreen(
                        place = state.place,
                        onBack = { navController.popBackStack() },
                        onFavoriteClick = { place -> viewModel.toggleFavorite(place) }
                    )
                }
                is DetailUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}