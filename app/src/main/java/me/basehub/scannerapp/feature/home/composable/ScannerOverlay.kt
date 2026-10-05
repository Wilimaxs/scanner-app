package me.basehub.scannerapp.feature.home.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun ScannerOverlay(
    modifier: Modifier = Modifier,
    scrimColor: Color = Color.Black.copy(alpha = 0.55f),
    cornerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val frameSize = min(size.minDimension * 0.72f, 320.dp.toPx())
        val left = (size.width - frameSize) / 2f
        val top = (size.height - frameSize) / 2f
        val right = left + frameSize
        val bottom = top + frameSize

        // The center stays transparent, leaving the camera preview unobstructed.
        val scrim = Path().apply {
            fillType = PathFillType.EvenOdd
            addRect(Rect(0f, 0f, size.width, size.height))
            addRect(Rect(left, top, right, bottom))
        }
        drawPath(path = scrim, color = scrimColor)

        val cornerLength = min(28.dp.toPx(), frameSize / 4f)
        val corners = Path().apply {
            moveTo(left, top + cornerLength)
            lineTo(left, top)
            lineTo(left + cornerLength, top)

            moveTo(right - cornerLength, top)
            lineTo(right, top)
            lineTo(right, top + cornerLength)

            moveTo(right, bottom - cornerLength)
            lineTo(right, bottom)
            lineTo(right - cornerLength, bottom)

            moveTo(left + cornerLength, bottom)
            lineTo(left, bottom)
            lineTo(left, bottom - cornerLength)
        }
        drawPath(
            path = corners,
            color = cornerColor,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round),
        )
    }
}
