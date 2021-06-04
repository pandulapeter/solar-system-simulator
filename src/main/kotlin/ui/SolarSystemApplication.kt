package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import data.CelestialBody
import data.CelestialBodyPosition
import data.RotationController
import kotlinx.coroutines.isActive
import java.lang.Integer.min

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
                centerX = windowSize.width.dp * celestialBodyWrapper.orbitCenterXMultiplier,
                centerY = windowSize.height.dp * celestialBodyWrapper.orbitCenterYMultiplier,
                width = windowSize.width.dp * celestialBodyWrapper.celestialBody.orbitRadiusMultiplier,
                height = windowSize.height.dp * celestialBodyWrapper.celestialBody.orbitRadiusMultiplier
            )

        }
    }
    celestialBodyPositions.sortedBy { it.yMultiplier }.forEach { celestialBodyWrapper ->
        CelestialBody(
            windowSize = windowSize,
            celestialBody = celestialBodyWrapper.celestialBody,
            xMultiplier = celestialBodyWrapper.xMultiplier,
            yMultiplier = celestialBodyWrapper.yMultiplier
        )
    }
}

@Composable
private fun CelestialBody(
    windowSize: IntSize,
    celestialBody: CelestialBody,
    xMultiplier: Float,
    yMultiplier: Float,
) {
    val radius = (min(windowSize.width, windowSize.height) / 15 * celestialBody.sizeRadiusMultiplier).dp
    Box(
        modifier = Modifier
            .offset(
                x = windowSize.width.dp * xMultiplier - radius,
                y = windowSize.height.dp * yMultiplier - radius
            )
            .size(radius * 2)
            .clip(CircleShape)
            .background(celestialBody.color)
    )
}

@Composable
private fun CelestialBodyPath(
    centerX: Dp,
    centerY: Dp,
    width: Dp,
    height: Dp
) {
    Canvas(
        modifier = Modifier
            .offset(centerX - (width / 2), centerY - (height / 2))
            .width(width)
            .height(height),
        onDraw = {
            drawOval(
                color = Color.LightGray,
                style = Stroke()
            )
        }
    )
}