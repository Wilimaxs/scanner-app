package me.basehub.scannerapp.feature.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.basehub.scannerapp.R
import me.basehub.scannerapp.core.theme.Spacing
import me.basehub.scannerapp.feature.home.composable.CameraPermissionContent
import me.basehub.scannerapp.feature.home.composable.FlashlightButton
import me.basehub.scannerapp.feature.home.composable.MenuSidebarContent
import me.basehub.scannerapp.feature.home.composable.ScannerOverlay
import me.basehub.scannerapp.utils.composables.AppBadge
import me.basehub.scannerapp.utils.composables.AppBar
import me.basehub.scannerapp.utils.composables.AppSidebar
import me.basehub.scannerapp.utils.composables.SidebarSide

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    onAboutClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    var camera by remember { mutableStateOf<Camera?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Read the actual permission whenever the screen becomes active.
    LifecycleResumeEffect(Unit) {
        viewModel.showCameraPermission(checkCameraPermission(context))

        onPauseOrDispose {
            viewModel.setFlashlightEnabled(false)
            camera?.let {
                if (it.cameraInfo.hasFlashUnit()) {
                    runCatching { it.cameraControl.enableTorch(false) }
                }
            }
        }
    }

    // Prepare the permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.showCameraPermission(granted)
    }

    // Ask once after the initial check, without reopening the dialog on resume.
    LaunchedEffect(Unit) {
        val granted = checkCameraPermission(context)
        viewModel.showCameraPermission(granted)
        if (!viewModel.state.value.permissionRequested) {
            viewModel.markPermissionRequested()
            if (!granted) {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppBar(
                title = "",
                modifier = Modifier.padding(
                    horizontal = Spacing.ScreenMargin,
                    vertical = Spacing.ScreenMargin
                ),
                navigationIcon = {
                    AppBadge(
                        icon = painterResource(R.drawable.ic_hamburger),
                        contentDescription = "Hamburger menu",
                        onClick = { viewModel.setMenuOpen(true) }
                    )
                },
                actions = {
                    AppBadge(
                        icon = painterResource(R.drawable.ic_settings),
                        contentDescription = "Settings menu",
                        onClick = { /* aksi tombol flashlight */ }
                    )
                },
                containerColor = Color.Transparent,
            )
        }
    ) { paddingValues ->
        Box {
            if (uiState.hasCameraPermission) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        val previewView = PreviewView(context)

                        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

                        cameraProviderFuture.addListener(
                            {
                                val cameraProvider = cameraProviderFuture.get()

                                val preview = Preview.Builder()
                                    .build()
                                    .also {
                                        it.surfaceProvider = previewView.surfaceProvider
                                    }
                                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                                cameraProvider.unbindAll()

                                camera = cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    preview
                                )
                            },
                            ContextCompat.getMainExecutor(context)
                        )
                        previewView
                    }
                )
                ScannerOverlay()
            } else {
                CameraPermissionContent(
                    onOpenSettings = {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null),
                            )
                        )
                    },
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .padding(bottom = Spacing.ScreenMargin)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FlashlightButton(
                    camera = camera,
                    hasPermission = uiState.hasCameraPermission,
                    isFlashlightOn = uiState.isFlashlightOn,
                    onFlashlightChanged = viewModel::setFlashlightEnabled,
                    snackbarHostState = snackbarHostState,
                )
                AppBadge(
                    icon = painterResource(R.drawable.ic_gallery),
                    contentDescription = "Gallery",
                    onClick = { /* aksi tombol Gallery */ }
                )
            }
        }
    }

    if (uiState.isMenuOpen) {
        AppSidebar(
            side = SidebarSide.Left,
            onDismiss = { viewModel.setMenuOpen(false) },
        ) {
            MenuSidebarContent(
                onScannerClick = { viewModel.setMenuOpen(false) },
                onAboutClick = {
                    viewModel.setMenuOpen(false)
                    onAboutClick()
                },
                onPrivacyPolicyClick = {
                    viewModel.setMenuOpen(false)
                    onPrivacyPolicyClick()
                },
            )
        }
    }
}

private fun checkCameraPermission(context: Context): Boolean =
    ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA,
    ) == PackageManager.PERMISSION_GRANTED
