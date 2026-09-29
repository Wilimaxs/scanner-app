package me.basehub.scannerapp.utils.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.basehub.scannerapp.core.theme.ScannerAppTheme
import me.basehub.scannerapp.core.theme.Spacing

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    reverse: Boolean = false,
    @DrawableRes leadingIcon: Int? = null,
) {
    val colors = MaterialTheme.colorScheme

    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = 48.dp),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (reverse) {
                colors.primary.copy(alpha = 0.12f)
            } else {
                colors.primary
            },
            contentColor = if (reverse) colors.primary else colors.onPrimary,
            disabledContainerColor = colors.surfaceContainerHigh,
            disabledContentColor = colors.onSurfaceVariant,
        ),
    ) {
        if (leadingIcon != null) {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(Spacing.Small))
        }

        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun AppButonPreview() {
    ScannerAppTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize().padding(all = Spacing.ScreenMargin)
            ) {
                AppButton(
                    text = "Connect to Network",
                    onClick = { /* aksi koneksi */ },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(Spacing.Medium))

                AppButton(
                    text = "Scan Another Code",
                    onClick = { /* kembali ke scanner */ },
                    modifier = Modifier.fillMaxWidth(),
                    reverse = true,
                )
            }
        }
    }
}