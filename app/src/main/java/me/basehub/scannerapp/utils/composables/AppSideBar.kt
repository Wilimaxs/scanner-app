package me.basehub.scannerapp.utils.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import me.basehub.scannerapp.core.theme.ScannerAppTheme
import me.basehub.scannerapp.core.theme.Spacing

enum class SidebarSide {
    Left,
    Right,
}

@Composable
fun AppSidebar(
    side: SidebarSide,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val panelShape = when (side) {
        SidebarSide.Left -> RoundedCornerShape(
            topEnd = Spacing.Large,
            bottomEnd = Spacing.Large,
        )

        SidebarSide.Right -> RoundedCornerShape(
            topStart = Spacing.Large,
            bottomStart = Spacing.Large,
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
        ),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(onClick = onDismiss),
            )

            Surface(
                modifier = Modifier
                    .align(
                        if (side == SidebarSide.Left) {
                            Alignment.CenterStart
                        } else {
                            Alignment.CenterEnd
                        }
                    )
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(),
                shape = panelShape,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 12.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    content = content,
                )
            }
        }
    }
}

@Preview(
    name = "Sidebar - Menu kiri",
    showBackground = true,
    widthDp = 390,
    heightDp = 844,
)
@Composable
private fun MenuSidebarPreview() {
    ScannerAppTheme {
        AppSidebar(
            side = SidebarSide.Left,
            onDismiss = {},
        ) {
            Text(
                text = "QR Scanner",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(Spacing.Large),
            )
            Text(
                text = "SCAN",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(
                    horizontal = Spacing.Large,
                    vertical = Spacing.Small,
                ),
            )
            Text(
                text = "QR Scanner",
                modifier = Modifier.padding(Spacing.Large),
            )
            Text(
                text = "About",
                modifier = Modifier.padding(Spacing.Large),
            )
            Text(
                text = "Privacy Policy",
                modifier = Modifier.padding(Spacing.Large),
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Version 1.0.0",
                modifier = Modifier.padding(Spacing.Large),
            )
        }
    }
}

@Preview(
    name = "Sidebar - Settings kanan",
    showBackground = true,
    widthDp = 390,
    heightDp = 844,
)
@Composable
private fun SettingsSidebarPreview() {
    ScannerAppTheme {
        AppSidebar(
            side = SidebarSide.Right,
            onDismiss = {},
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(Spacing.Large),
            )
            Text(
                text = "SCANNER",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(Spacing.Large),
            )
            Text(
                text = "Vibrate on scan",
                modifier = Modifier.padding(Spacing.Large),
            )
            Text(
                text = "Sound on scan",
                modifier = Modifier.padding(Spacing.Large),
            )
            Text(
                text = "Open links with",
                modifier = Modifier.padding(Spacing.Large),
            )
        }
    }
}