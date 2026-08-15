package com.example.sitiosturisticosecuador.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sitiosturisticosecuador.data.local.TouristPlaceEntity
import com.example.sitiosturisticosecuador.data.remote.TouristPlace
import com.example.sitiosturisticosecuador.data.repository.TouristRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val place: TouristPlace) : DetailUiState
    data class Error(val message: String) : DetailUiState
}

class DetailViewModel(
    private val repository: TouristRepository,
    private val placeId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadPlaceDetail()
    }

    private fun loadPlaceDetail() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val allPlaces = repository.getRemotePlaces()
                val place = allPlaces.find { it.id == placeId }
                if (place != null) _uiState.value = DetailUiState.Success(place)
                else _uiState.value = DetailUiState.Error("Sitio no encontrado")
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error("Error: ${e.message}")
            }
        }
    }

    fun toggleFavorite(place: TouristPlace) {
        viewModelScope.launch {
            val entity = TouristPlaceEntity(place.id, place.name, place.province, place.description, place.imageUrl, place.latitude, place.longitude)
            if (place.isFavorite) repository.removeFavorite(entity) else repository.addFavorite(entity)
            loadPlaceDetail()
        }
    }
}