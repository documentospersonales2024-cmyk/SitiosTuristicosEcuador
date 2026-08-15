package com.example.sitiosturisticosecuador.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sitiosturisticosecuador.data.local.TouristPlaceEntity
import com.example.sitiosturisticosecuador.data.repository.TouristRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: TouristRepository) : ViewModel() {
    val favorites: StateFlow<List<TouristPlaceEntity>> = repository.favorites
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeFavorite(place: TouristPlaceEntity) {
        viewModelScope.launch {
            repository.removeFavorite(place)
        }
    }
}