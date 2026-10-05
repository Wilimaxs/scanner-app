package me.basehub.scannerapp.feature.home.composable

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import me.basehub.scannerapp.R
import me.basehub.scannerapp.core.theme.Spacing

@Composable
fun SettingsSidebarContent(
    vibrateOnScan: Boolean,
    soundOnScan: Boolean,
    preferredBrowserPackage: String,
    onVibrateChanged: (Boolean) -> Unit,
    onSoundChanged: (Boolean) -> Unit,
    onBrowserChanged: (String) -> Unit,
) {
    val context = LocalContext.current
    var showBrowserPicker by rememberSaveable { mutableStateOf(false) }
    val browsers = remember(context, showBrowserPicker) {
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_BROWSER)
        context.packageManager.queryIntentActivities(intent, 0)
            .map { BrowserOption(it.activityInfo.packageName, it.loadLabel(context.packageManager).toString()) }
            .distinctBy { it.packageName }
            .sortedBy { it.label.lowercase() }
    }
    val browserLabel = if (preferredBrowserPackage.isEmpty()) {
        stringResource(R.string.settings_ask_every_time)
    } else {
        browsers.find { it.packageName == preferredBrowserPackage }?.label
            ?: stringResource(R.string.settings_browser_unavailable)
    }

    Text(
        text = stringResource(R.string.settings_title),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(Spacing.Medium),
    )
    HorizontalDivider()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(vertical = Spacing.Medium),
    ) {
        SettingsSectionLabel(stringResource(R.string.settings_scanner_section))
        ScannerSettingSwitch(
            title = stringResource(R.string.settings_vibrate_title),
            description = stringResource(R.string.settings_vibrate_description),
            checked = vibrateOnScan,
            onCheckedChange = onVibrateChanged,
        )
        ScannerSettingSwitch(
            title = stringResource(R.string.settings_sound_title),
            description = stringResource(R.string.settings_sound_description),
            checked = soundOnScan,
            onCheckedChange = onSoundChanged,
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
        )
        SettingsSectionLabel(stringResource(R.string.settings_links_section))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showBrowserPicker = true }
                .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.settings_open_links_title),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(Modifier.height(Spacing.ExtraSmall))
                Text(
                    text = stringResource(R.string.settings_open_links_description),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = browserLabel,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.widthIn(max = 96.dp),
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
        }
    }

    if (showBrowserPicker) {
        AlertDialog(
            onDismissRequest = { showBrowserPicker = false },
            title = { Text(stringResource(R.string.settings_open_links_title)) },
            text = {
                Column(
                    modifier = Modifier
                        .heightIn(max = 360.dp)
                        .verticalScroll(rememberScrollState())
                        .selectableGroup(),
                ) {
                    val options = listOf(
                        BrowserOption("", stringResource(R.string.settings_ask_every_time))
                    ) + browsers
                    options.forEach { browser ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 48.dp)
                                .selectable(
                                    selected = browser.packageName == preferredBrowserPackage,
                                    role = Role.RadioButton,
                                    onClick = {
                                        onBrowserChanged(browser.packageName)
                                        showBrowserPicker = false
                                    },
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                        ) {
                            RadioButton(
                                selected = browser.packageName == preferredBrowserPackage,
                                onClick = null,
                            )
                            Text(browser.label)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showBrowserPicker = false }) {
                    Text(stringResource(R.string.settings_close))
                }
            },
        )
    }
}

@Composable
private fun SettingsSectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = Spacing.Medium, vertical = Spacing.ExtraSmall),
    )
}

@Composable
private fun ScannerSettingSwitch(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange)
            .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(Spacing.ExtraSmall))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = null,
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedBorderColor = Color.Transparent,
            ),
        )
    }
}

private data class BrowserOption(val packageName: String, val label: String)
