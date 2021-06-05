package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import data.CelestialBodyState
import data.RotationController
import kotlinx.coroutines.isActive
import java.lang.Integer.min
import kotlin.math.roundToInt

@Composable
fun SolarSystemSimulator(
    windowSize: IntSize,
    rotationController: RotationController
) {
    var celestialBodyPositions by remember { mutableStateOf(emptyList<CelestialBodyState>()) }
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
        celestialBodyStates = celestialBodyPositions
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
    celestialBodyStates: List<CelestialBodyState>
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color(0xFF503985))
) {
    StarField(
        windowSize = windowSize
    )
    celestialBodyStates.forEach { celestialBodyWrapper ->
        if (celestialBodyWrapper.celestialBody.orbitCenter != null) {
            Orbit(
                center = DpOffset(
                    x = windowSize.width.dp * celestialBodyWrapper.relativePosition.x,
                    y = windowSize.height.dp * celestialBodyWrapper.relativePosition.y
                ),
                size = IntSize(
                    width = (windowSize.width * celestialBodyWrapper.celestialBody.orbitRadiusMultiplier).roundToInt(),
                    height = (windowSize.height * celestialBodyWrapper.celestialBody.orbitRadiusMultiplier).roundToInt()
                )
            )

        }
    }

    var selectedCelestialBody by remember { mutableStateOf<CelestialBody?>(null) }

    celestialBodyStates
        .sortedBy { it.position.y }
        .sortedBy { selectedCelestialBody == it.celestialBody }
        .forEach { celestialBodyWrapper ->
            CelestialBody(
                windowSize = windowSize,
                celestialBody = celestialBodyWrapper.celestialBody,
                relativePosition = celestialBodyWrapper.position,
                selectedCelestialBody = selectedCelestialBody,
                onCelestialBodySelected = { clickedCelestialBody ->
                    selectedCelestialBody = if (selectedCelestialBody == clickedCelestialBody) null else clickedCelestialBody
                }
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
    relativePosition: Offset,
    selectedCelestialBody: CelestialBody?,
    onCelestialBodySelected: (CelestialBody) -> Unit
) {
    val radius = (min(windowSize.width, windowSize.height) / 10 * celestialBody.sizeRadiusMultiplier).dp
    val interactionSource = remember { MutableInteractionSource() }
    Image(
        modifier = Modifier
            .offset(
                x = (relativePosition.x * windowSize.width).dp - radius,
                y = (relativePosition.y * windowSize.height).dp - radius
            )
            .size(radius * 2)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onCelestialBodySelected(celestialBody) }
            ),
        contentScale = ContentScale.Fit,
        bitmap = celestialBody.asset,
        alpha = if (selectedCelestialBody == celestialBody || selectedCelestialBody == null) 1f else 0.2f,
        contentDescription = celestialBody.displayName
    )
}
