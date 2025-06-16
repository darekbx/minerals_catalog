package com.darekbx.mineralscatalog.ui.new

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darekbx.mineralscatalog.domain.AddEntryUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NewScreenState {
    data object Idle : NewScreenState()
    data class Error(val error: Throwable) : NewScreenState()
    object Loading : NewScreenState()
    object Success : NewScreenState()
}

class NewScreenViewModel(private val addEntryUseCase: AddEntryUseCase): ViewModel() {

    private val _uiState = MutableStateFlow<NewScreenState>(NewScreenState.Idle)
    val uiState: StateFlow<NewScreenState> = _uiState.asStateFlow()

    fun save(uri: String, label: String, location: String, description: String) {
        viewModelScope.launch {
            _uiState.value = NewScreenState.Loading
            try {
                addEntryUseCase(uri, label, location, description)
                delay(500) // For better UX
                _uiState.value = NewScreenState.Success
            } catch (e: Exception) {
                _uiState.value = NewScreenState.Error(e)
            }
        }
    }

    fun resetState() {
        _uiState.value = NewScreenState.Idle
    }
}
