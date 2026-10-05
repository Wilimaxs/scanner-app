package me.basehub.scannerapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.datastore.preferences.core.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import me.basehub.scannerapp.core.data.local.pref.DataStoreManager
import me.basehub.scannerapp.core.data.local.pref.PreferenceKeys
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            permissionRequested = savedStateHandle["permissionRequested"] ?: false,
        )
    )
    val state: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                dataStoreManager.get(PreferenceKeys.VIBRATE_ON_SCAN, true),
                dataStoreManager.get(PreferenceKeys.SOUND_ON_SCAN, false),
                dataStoreManager.get(PreferenceKeys.PREFERRED_BROWSER_PACKAGE, ""),
            ) { vibrate, sound, browser -> Triple(vibrate, sound, browser) }
                .catch { error ->
                    if (error is IOException) {
                        _uiState.update { it.copy(settingsError = true) }
                    } else {
                        throw error
                    }
                }
                .collect { (vibrate, sound, browser) ->
                    _uiState.update {
                        it.copy(
                            vibrateOnScan = vibrate,
                            soundOnScan = sound,
                            preferredBrowserPackage = browser,
                        )
                    }
                }
        }
    }

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
        _uiState.update {
            it.copy(isMenuOpen = open, isSettingsOpen = if (open) false else it.isSettingsOpen)
        }
    }

    fun setSettingsOpen(open: Boolean) {
        _uiState.update {
            it.copy(isSettingsOpen = open, isMenuOpen = if (open) false else it.isMenuOpen)
        }
    }

    fun setVibrateOnScan(enabled: Boolean) {
        saveSetting(PreferenceKeys.VIBRATE_ON_SCAN, enabled)
    }

    fun setSoundOnScan(enabled: Boolean) {
        saveSetting(PreferenceKeys.SOUND_ON_SCAN, enabled)
    }

    fun setPreferredBrowser(packageName: String) {
        saveSetting(PreferenceKeys.PREFERRED_BROWSER_PACKAGE, packageName)
    }

    fun clearSettingsError() {
        _uiState.update { it.copy(settingsError = false) }
    }

    private fun <T> saveSetting(key: Preferences.Key<T>, value: T) {
        viewModelScope.launch {
            try {
                dataStoreManager.save(key, value)
            } catch (_: IOException) {
                _uiState.update { it.copy(settingsError = true) }
            }
        }
    }
}
