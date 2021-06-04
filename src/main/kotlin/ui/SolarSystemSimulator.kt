package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
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
fun SolarSystemSimulator(
    windowSize: IntSize,
    rotationController: RotationController
) {
    var celestialBodyPositions by remember { mutableStateOf(emptyList<CelestialBodyPosition>()) }
    var simulationSpeedMultiplier by remember { mutableStateOf(0.2f) }

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
        modifier = Modifier.fillMaxWidth(0.25f),
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
        .background(Color(0xFF503985))
) {
    StarField(
        windowSize = windowSize
    )
    celestialBodyPositions.forEach { celestialBodyWrapper ->
        if (celestialBodyWrapper.celestialBody.orbitCenter != null) {
            Orbit(
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
private fun StarField(
    windowSize: IntSize
) {
    if (windowSize.width > 0 && windowSize.height > 0) {
        // TODO
    }
}

private val orbitColor = Color.White.copy(alpha = 0.2f)

@Composable
private fun Orbit(
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
                color = orbitColor,
                style = Stroke()
            )
        }
    )
}

@Composable
private fun CelestialBody(
    windowSize: IntSize,
    celestialBody: CelestialBody,
    multiplierOffset: Offset
) {
    val radius = (min(windowSize.width, windowSize.height) / 10 * celestialBody.sizeRadiusMultiplier).dp
    Image(
        modifier = Modifier
            .offset(
                x = windowSize.width.dp * multiplierOffset.x - radius,
                y = windowSize.height.dp * multiplierOffset.y - radius
            )
            .size(radius * 2),
        contentScale = ContentScale.Fit,
        bitmap = celestialBody.asset,
        contentDescription = celestialBody.displayName
    )
}
