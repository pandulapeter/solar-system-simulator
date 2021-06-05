package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import data.CelestialBodyState
import ui.resources.Colors
import kotlin.math.roundToInt

@Composable
fun Orbits(
    windowSize: Size,
    celestialBodyStates: List<CelestialBodyState>,
    transitionProgress: Float
) {
    celestialBodyStates.forEach { celestialBodyState ->
        if (celestialBodyState.celestialBody.orbitCenter != null) {
            Orbit(
                transitionProgress = transitionProgress,
                center = DpOffset(
                    x = windowSize.width.dp * celestialBodyState.centerPosition.x,
                    y = windowSize.height.dp * celestialBodyState.centerPosition.y
                ),
                size = IntSize(
                    width = (windowSize.width * celestialBodyState.celestialBody.orbitRadiusMultiplier).roundToInt(),
                    height = (windowSize.height * celestialBodyState.celestialBody.orbitRadiusMultiplier).roundToInt()
                )
            )

        }
    }
}

@Composable
private fun Orbit(
    transitionProgress: Float,
    center: DpOffset,
    size: IntSize
) {
    Canvas(
        modifier = Modifier
            .offset(
                x = center.x - (size.width.dp / 2),
                y = center.y - (size.height.dp / 2)
            )
            .width(size.width.dp)
            .height(size.height.dp),
        onDraw = {
            drawOval(
                color = Colors.orbit,
                style = Stroke(),
                alpha = transitionProgress
            )
        }
    )
}