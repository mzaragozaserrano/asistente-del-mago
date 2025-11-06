package com.asistentedelmago.feature.repertory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asistentedelmago.core.domain.usecase.CreateTrickUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddTrickUiState(
    val isLoading: Boolean = false
)

@HiltViewModel
class AddTrickViewModel @Inject constructor(
    private val createTrickUseCase: CreateTrickUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddTrickUiState())
    val uiState: StateFlow<AddTrickUiState> = _uiState.asStateFlow()
    
    fun createTrick(
        title: String,
        description: String?,
        materials: String?,
        angles: String?,
        resetTime: String?
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // TODO: Get userId from auth
                val userId = "user1" // Placeholder
                createTrickUseCase(
                    userId = userId,
                    title = title,
                    description = description,
                    materials = materials,
                    angles = angles,
                    resetTime = resetTime
                )
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}

