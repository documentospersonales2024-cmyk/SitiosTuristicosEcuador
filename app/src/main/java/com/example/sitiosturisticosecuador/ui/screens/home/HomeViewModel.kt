package com.example.sitiosturisticosecuador.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sitiosturisticosecuador.data.local.TouristPlaceEntity
import com.example.sitiosturisticosecuador.data.remote.TouristPlace
import com.example.sitiosturisticosecuador.data.repository.TouristRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TouristRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val remotePlaces = repository.getRemotePlaces()

                // Nos suscribimos a los cambios de favoritos en Room para actualizar el estado automáticamente
                repository.favorites.collectLatest { favoriteEntities ->
                    val favoriteIds = favoriteEntities.map { it.id }.toSet()

                    // Marcamos isFavorite = true si el ID está guardado en Room
                    val updatedPlaces = remotePlaces.map { place ->
                        place.copy(isFavorite = favoriteIds.contains(place.id))
                    }

                    _uiState.value = HomeUiState.Success(updatedPlaces)
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Error de conexión: ${e.localizedMessage ?: "Verifica tu red"}")
            }
        }
    }

    fun toggleFavorite(place: TouristPlace) {
        viewModelScope.launch {
            val entity = TouristPlaceEntity(
                id = place.id,
                name = place.name,
                province = place.province,
                description = place.description,
                imageUrl = place.imageUrl,
                latitude = place.latitude,
                longitude = place.longitude
            )

            // Si ya es favorito, lo borramos de Room; si no, lo agregamos
            if (place.isFavorite) {
                repository.removeFavorite(entity)
            } else {
                repository.addFavorite(entity)
            }
            // Como estamos usando .collectLatest en repository.favorites,
            // la lista se actualizará sola de inmediato reflejando el cambio en el corazón.
        }
    }
}