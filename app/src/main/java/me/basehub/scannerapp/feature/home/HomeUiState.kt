package me.basehub.scannerapp.feature.home

data class HomeUiState(
    val hasCameraPermission: Boolean = false,
    val permissionRequested: Boolean = false,
    val isFlashlightOn: Boolean = false,
    val isMenuOpen: Boolean = false,
)
