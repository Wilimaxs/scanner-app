package me.basehub.scannerapp.feature.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.basehub.scannerapp.R
import me.basehub.scannerapp.core.theme.Spacing
import me.basehub.scannerapp.utils.composables.AppBadge
import me.basehub.scannerapp.utils.composables.AppButton

@Composable
fun CameraPermissionContent(
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing.Large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AppBadge(
                icon = painterResource(R.drawable.ic_camera_off),
                contentDescription = null,
                iconSize = 64.dp,
                iconButtonSize = 120.dp,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Spacer(modifier = Modifier.height(Spacing.Large))
            Text(
                text = stringResource(R.string.camera_permission_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(Spacing.Small))
            Text(
                text = stringResource(R.string.camera_permission_description),
                modifier = Modifier.widthIn(max = 320.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(Spacing.Large))
            AppButton(
                text = stringResource(R.string.camera_permission_open_settings),
                onClick = onOpenSettings,
                modifier = Modifier
                    .widthIn(max = 320.dp)
                    .fillMaxWidth(),
                leadingIcon = R.drawable.ic_settings,
            )
        }
    }
}
