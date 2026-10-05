package me.basehub.scannerapp.feature.home.composable

import androidx.annotation.StringRes
import androidx.camera.core.Camera
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch
import me.basehub.scannerapp.R
import me.basehub.scannerapp.utils.composables.AppBadge

@Composable
fun FlashlightButton(
    camera: Camera?,
    hasPermission: Boolean,
    isFlashlightOn: Boolean,
    onFlashlightChanged: (Boolean) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val resources = LocalResources.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val currentCamera by rememberUpdatedState(camera)
    var latestRequest by remember { mutableIntStateOf(0) }

    fun showFlashlightMessage(@StringRes messageRes: Int) {
        val message = resources.getString(messageRes)
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(message)
        }
    }

    AppBadge(
        modifier = modifier,
        icon = painterResource(
            if (isFlashlightOn) R.drawable.ic_flashlight
            else R.drawable.ic_flashlight_off
        ),
        contentDescription = stringResource(
            if (isFlashlightOn) R.string.flashlight_turn_off
            else R.string.flashlight_turn_on
        ),
        onClick = {
            val requestId = ++latestRequest
            val requestedState = !isFlashlightOn
            onFlashlightChanged(requestedState)
            val activeCamera = camera
            when {
                !hasPermission || activeCamera == null ->
                    showFlashlightMessage(R.string.flashlight_camera_not_ready)

                !activeCamera.cameraInfo.hasFlashUnit() ->
                    showFlashlightMessage(R.string.flashlight_unavailable)

                else -> {
                    try {
                        val result = activeCamera.cameraControl.enableTorch(requestedState)
                        result.addListener(
                            {
                                try {
                                    result.get()
                                } catch (_: Exception) {
                                    // Ignore failures from an older click or an inactive screen.
                                    if (currentCamera === activeCamera &&
                                        latestRequest == requestId &&
                                        lifecycleOwner.lifecycle.currentState.isAtLeast(
                                            Lifecycle.State.RESUMED
                                        )
                                    ) {
                                        showFlashlightMessage(R.string.flashlight_failed)
                                    }
                                }
                            },
                            ContextCompat.getMainExecutor(context),
                        )
                    } catch (_: Exception) {
                        showFlashlightMessage(R.string.flashlight_failed)
                    }
                }
            }
        },
    )
}
