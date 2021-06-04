package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import data.CelestialBody
import data.CelestialBodyPosition
import data.RotationController
import kotlinx.coroutines.isActive
import java.lang.Integer.min
import kotlin.math.roundToInt

@Composable
fun SolarSystemApplication(
    windowSize: IntSize,
    rotationController: RotationController
) {

    var celestialBodyPositions by remember { mutableStateOf(emptyList<CelestialBodyPosition>()) }
    var simulationSpeedMultiplier by remember { mutableStateOf(0.5f) }

    LaunchedEffect(Unit) {
        while (isActive) {
            withFrameMillis { time ->
                celestialBodyPositions = rotationController.update(time, simulationSpeedMultiplier)
            }
        }
    }
    SolarSystem(
        windowSize = windowSize,
        celestialBodyPositions = celestialBodyPositions
    )
    Slider(
        value = simulationSpeedMultiplier,
        valueRange = 0f..2f,
        onValueChange = { simulationSpeedMultiplier = it }
    )
}

@Composable
private fun SolarSystem(
    windowSize: IntSize,
    celestialBodyPositions: List<CelestialBodyPosition>
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.Black)
) {
    celestialBodyPositions.forEach { celestialBodyWrapper ->
        if (celestialBodyWrapper.celestialBody.orbitCenter != null) {
            CelestialBodyPath(
                center = DpOffset(
                    x = windowSize.width.dp * celestialBodyWrapper.orbitCenterOffsetMultiplier.x,
                    y = windowSize.height.dp * celestialBodyWrapper.orbitCenterOffsetMultiplier.y
                ),
                size = IntSize(
                    width = (windowSize.width * celestialBodyWrapper.celestialBody.orbitRadiusMultiplier).roundToInt(),
                    height = (windowSize.height * celestialBodyWrapper.celestialBody.orbitRadiusMultiplier).roundToInt()
                )
            )

        }
    }
    celestialBodyPositions.sortedBy { it.multiplierOffset.y }.forEach { celestialBodyWrapper ->
        CelestialBody(
            windowSize = windowSize,
            celestialBody = celestialBodyWrapper.celestialBody,
            multiplierOffset = celestialBodyWrapper.multiplierOffset
        )
    }
}

@Composable
private fun CelestialBody(
    windowSize: IntSize,
    celestialBody: CelestialBody,
    multiplierOffset: Offset
) {
    val radius = (min(windowSize.width, windowSize.height) / 15 * celestialBody.sizeRadiusMultiplier).dp
    Box(
        modifier = Modifier
            .offset(
                x = windowSize.width.dp * multiplierOffset.x - radius,
                y = windowSize.height.dp * multiplierOffset.y - radius
            )
            .size(radius * 2)
            .clip(CircleShape)
            .background(celestialBody.color)
    )
}

@Composable
private fun CelestialBodyPath(
    center: DpOffset,
    size: IntSize
) {
    Canvas(
        modifier = Modifier
            .offset(center.x - (size.width.dp / 2), center.y - (size.height.dp / 2))
            .width(size.width.dp)
            .height(size.height.dp),
        onDraw = {
            drawOval(
                color = Color.LightGray,
                style = Stroke()
            )
        }
    )
}