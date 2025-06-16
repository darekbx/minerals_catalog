package com.darekbx.mineralscatalog.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darekbx.mineralscatalog.domain.DeleteEntryUseCase
import com.darekbx.mineralscatalog.domain.FetchMineralUseCase
import com.darekbx.mineralscatalog.model.Mineral
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailsState {
    data object Idle : DetailsState()
    data class Error(val error: Throwable) : DetailsState()
    data class Success(val mineral: Mineral) : DetailsState()
    object Loading : DetailsState()
    object Deleted : DetailsState()
}

class DetailsViewModel(
    private val fetchMineralUseCase: FetchMineralUseCase,
    private val deleteEntryUseCase: DeleteEntryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsState>(DetailsState.Idle)
    val uiState: StateFlow<DetailsState> = _uiState.asStateFlow()

    fun deleteEntry(id: String) {
        viewModelScope.launch {
            try {
                deleteEntryUseCase(id)
                delay(500) // For better UX
                _uiState.value = DetailsState.Deleted
            } catch (e: Exception) {
                _uiState.value = DetailsState.Error(e)
            }
        }
    }

    fun load(id: String) {
        viewModelScope.launch {
            _uiState.value = DetailsState.Loading
            try {
                val mineral = fetchMineralUseCase(id)
                delay(500) // For better UX
                _uiState.value = DetailsState.Success(mineral)
            } catch (e: Exception) {
                _uiState.value = DetailsState.Error(e)
            }
        }
    }

    fun resetState() {
        _uiState.value = DetailsState.Idle
    }
}