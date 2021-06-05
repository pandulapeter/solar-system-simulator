package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import ui.resources.Colors

@Composable
fun Background(windowSize: IntSize) {
    Box(
        modifier = Modifier.background(Colors.background1)
    ) {
        Cloud(
            windowSize = windowSize,
            color = Colors.background2,
            offset = 0.24f
        )
        Cloud(
            windowSize = windowSize,
            color = Colors.background3,
            offset = 0.19f
        )
        Cloud(
            windowSize = windowSize,
            color = Colors.background4,
            offset = 0.15f
        )
        Cloud(
            windowSize = windowSize,
            color = Colors.background5,
            offset = 0.12f
        )
        Cloud(
            windowSize = windowSize,
            color = Colors.background6,
            offset = 0.1f
        )
    }
}

@Composable
private fun Cloud(
    windowSize: IntSize,
    color: Color,
    offset: Float
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        drawOval(
            color = color,
            size = Size(
                width = windowSize.width / offset,
                height = windowSize.height / offset
            ),
            topLeft = Offset(
                x = -windowSize.width * offset,
                y = -windowSize.height * offset
            )
        )
    }
}
