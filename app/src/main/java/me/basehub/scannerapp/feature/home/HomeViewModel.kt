package me.basehub.scannerapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            permissionRequested = savedStateHandle["permissionRequested"] ?: false,
        )
    )
    val state: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun showCameraPermission(granted: Boolean) {
        _uiState.update {
            it.copy(
                hasCameraPermission = granted
            )
        }
    }

    fun markPermissionRequested() {
        savedStateHandle["permissionRequested"] = true
        _uiState.update { it.copy(permissionRequested = true) }
    }

    fun setFlashlightEnabled(enabled: Boolean) {
        _uiState.update { it.copy(isFlashlightOn = enabled) }
    }

    fun setMenuOpen(open: Boolean) {
        _uiState.update { it.copy(isMenuOpen = open) }
    }
}
