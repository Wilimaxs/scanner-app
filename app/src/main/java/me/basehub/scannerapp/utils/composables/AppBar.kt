package me.basehub.scannerapp.utils.composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import me.basehub.scannerapp.R
import me.basehub.scannerapp.core.theme.ScannerAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    centerTitle: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onBackClick: (() -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val titleContent: @Composable () -> Unit = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }

    val leadingIcon: @Composable () -> Unit = navigationIcon ?: {
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = contentColor,
                )
            }
        }
    }

    val colors = TopAppBarDefaults.topAppBarColors(
        containerColor = containerColor,
        titleContentColor = contentColor,
        navigationIconContentColor = contentColor,
        actionIconContentColor = contentColor,
    )

    if (centerTitle) {
        CenterAlignedTopAppBar(
            title = titleContent,
            modifier = modifier,
            navigationIcon = leadingIcon,
            actions = actions,
            colors = colors,
        )
    } else {
        TopAppBar(
            title = titleContent,
            modifier = modifier,
            navigationIcon = leadingIcon,
            actions = actions,
            colors = colors,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppBarPreview() {
    ScannerAppTheme {
        AppBar(
            title = "Result Scan",
            onBackClick = {},
            centerTitle = true
        )
    }
}