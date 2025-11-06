package com.asistentedelmago.feature.repertory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asistentedelmago.core.domain.usecase.GetAllTricksUseCase
import com.asistentedelmago.core.model.MagicTrick
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ArsenalUiState(
    val tricks: List<MagicTrick> = emptyList(),
    val isLoading: Boolean = false
)

class ArsenalViewModel(
    private val getAllTricksUseCase: GetAllTricksUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ArsenalUiState())
    val uiState: StateFlow<ArsenalUiState> = _uiState.asStateFlow()
    
    init {
        // TODO: Get userId from auth
        val userId = "user1" // Placeholder
        loadTricks(userId)
    }
    
    private fun loadTricks(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                getAllTricksUseCase(userId).collect { tricks ->
                    _uiState.value = _uiState.value.copy(
                        tricks = tricks,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}

