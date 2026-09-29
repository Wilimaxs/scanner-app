package me.basehub.scannerapp.utils.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.basehub.scannerapp.R
import me.basehub.scannerapp.theme.ScannerAppTheme
import me.basehub.scannerapp.theme.Spacing

@Composable
fun AppBadge(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Painter? = null,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    iconSize: Dp = 24.dp,
    iconButtonSize: Dp = 56.dp,
    containerPadding: Dp = Spacing.Small,
) {
    val iconOnly = icon != null && text == null

    Box(
        modifier = modifier
            .then(if (iconOnly) Modifier.size(iconButtonSize) else Modifier)
            .clip(CircleShape)
            .background(containerColor)
            .then(
                if (onClick != null) {
                    Modifier.clickable(role = Role.Button, onClick = onClick)
                } else {
                    Modifier
                }
            )
            .then(
                if (iconOnly) Modifier else Modifier.padding(containerPadding)
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = if (text == null) contentDescription else null,
                    tint = contentColor,
                    modifier = Modifier.size(iconSize),
                )
            }

            if (icon != null && text != null) {
                Spacer(modifier = Modifier.width(Spacing.Small))
            }

            if (text != null) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelSmall,
                    color = contentColor,
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun AppBadgePreview() {
    ScannerAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                AppBadge(
                    icon = painterResource(id = R.drawable.ic_flashlight),
                    contentDescription = "Flash",
                    onClick = {},
                    containerColor = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.9f),
                    contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                )
            }
        }
    }
}