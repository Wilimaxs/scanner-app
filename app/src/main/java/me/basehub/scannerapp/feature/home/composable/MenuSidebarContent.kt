package me.basehub.scannerapp.feature.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.basehub.scannerapp.BuildConfig
import me.basehub.scannerapp.R
import me.basehub.scannerapp.core.theme.Spacing
import me.basehub.scannerapp.utils.composables.AppBadge

@Composable
fun ColumnScope.MenuSidebarContent(
    onScannerClick: () -> Unit,
    onAboutClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.Small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        AppBadge(
            icon = painterResource(R.drawable.ic_qr_code),
            contentDescription = "QR Scanner",
            iconButtonSize = 48.dp,
            containerPadding = Spacing.ExtraSmall,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall),
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(R.string.app_description),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.sp,
                ),
            )
        }
    }
    Spacer(modifier = Modifier.height(Spacing.Small))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(Spacing.Medium))

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.Small),
    ) {
        Text(
            text = stringResource(R.string.menu_scan_section),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = Spacing.Small),
        )
        Spacer(modifier = Modifier.height(Spacing.Small))
        NavigationDrawerItem(
            label = {
                Text(
                    stringResource(R.string.menu_qr_scanner),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_qr_scanner),
                    contentDescription = "QR Scanner",
                    modifier = Modifier.size(24.dp)
                )
            },
            selected = true,
            onClick = onScannerClick,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        )

        Spacer(modifier = Modifier.height(Spacing.Medium))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(Spacing.Medium))

        Text(
            text = stringResource(R.string.menu_other),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = Spacing.Small),
        )

        Spacer(modifier = Modifier.height(Spacing.Small))

        NavigationDrawerItem(
            label = {
                Text(
                    stringResource(R.string.menu_about),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_about),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp),
                )
            },
            badge = {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            },
            selected = false,
            onClick = onAboutClick,
        )
        NavigationDrawerItem(
            label = {
                Text(
                    stringResource(R.string.menu_privacy_policy),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_privacy),
                    contentDescription = "Privacy Policy",
                    modifier = Modifier.size(24.dp)
                )
            },
            badge = {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            },
            selected = false,
            onClick = onPrivacyPolicyClick,
        )
    }

    HorizontalDivider(modifier = Modifier.padding(horizontal = Spacing.ScreenMargin))
    Spacer(modifier = Modifier.height(Spacing.Medium))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.ScreenMargin),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.jetpack_compose_ui),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = stringResource(R.string.menu_version, BuildConfig.VERSION_NAME),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(modifier = Modifier.height(Spacing.Small))
        Text(
            text = stringResource(R.string.qr_scanner_focused_studio),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }

}
