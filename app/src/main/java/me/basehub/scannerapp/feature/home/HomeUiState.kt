package me.basehub.scannerapp.feature.home

data class HomeUiState(
    val hasCameraPermission: Boolean = false,
    val permissionRequested: Boolean = false,
    val isFlashlightOn: Boolean = false,
    val isMenuOpen: Boolean = false,
    val isSettingsOpen: Boolean = false,
    val vibrateOnScan: Boolean = true,
    val soundOnScan: Boolean = false,
    val preferredBrowserPackage: String = "",
    val settingsError: Boolean = false,
)
