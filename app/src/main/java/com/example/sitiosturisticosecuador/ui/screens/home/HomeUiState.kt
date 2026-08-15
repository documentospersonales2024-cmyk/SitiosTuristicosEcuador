package com.example.sitiosturisticosecuador.ui.screens.home

import com.example.sitiosturisticosecuador.data.remote.TouristPlace

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val places: List<TouristPlace>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}