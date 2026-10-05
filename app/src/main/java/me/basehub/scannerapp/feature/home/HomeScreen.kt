package me.basehub.scannerapp.feature.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import me.basehub.scannerapp.R
import me.basehub.scannerapp.core.theme.Spacing
import me.basehub.scannerapp.feature.home.composable.CameraPermissionContent
import me.basehub.scannerapp.utils.composables.AppBadge
import me.basehub.scannerapp.utils.composables.AppBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasPermission by remember { mutableStateOf(false) }
    var permissionRequested by rememberSaveable { mutableStateOf(false) }

    // Read the actual permission whenever the screen becomes active.
    LifecycleResumeEffect(Unit) {
        hasPermission = checkCameraPermission(context)

        onPauseOrDispose { }
    }

    // Prepare the permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
    }

    // Ask once after the initial check, without reopening the dialog on resume.
    LaunchedEffect(Unit) {
        hasPermission = checkCameraPermission(context)
        if (!permissionRequested) {
            permissionRequested = true
            if (!hasPermission) {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    Scaffold(
        modifier = modifier,
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
                        onClick = { /* aksi tombol flashlight */ }
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
            if (hasPermission) {
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

                                cameraProvider.bindToLifecycle(
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
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AppBadge(
                    icon = painterResource(R.drawable.ic_flashlight),
                    contentDescription = "Flash",
                    onClick = { /* aksi tombol flashlight */ }
                )
                AppBadge(
                    icon = painterResource(R.drawable.ic_gallery),
                    contentDescription = "Gallery",
                    onClick = { /* aksi tombol Gallery */ }
                )
            }
        }
    }
}

private fun checkCameraPermission(context: Context): Boolean =
    ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA,
    ) == PackageManager.PERMISSION_GRANTED
