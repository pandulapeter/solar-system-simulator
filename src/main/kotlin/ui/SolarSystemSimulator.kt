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
    var selectedCelestialBody by remember { mutableStateOf<CelestialBody?>(null) }

    LaunchedEffect(Unit) {
        while (isActive) {
            withFrameMillis { time ->
                celestialBodyPositions = rotationController.update(
                    time = time,
                    simulationSpeedMultiplier = simulationSpeedMultiplier,
                    selectedCelestialBody = selectedCelestialBody
                )
            }
        }
    }
    SolarSystem(
        windowSize = windowSize,
        celestialBodyStates = celestialBodyPositions,
        selectedCelestialBody = selectedCelestialBody,
        onCelestialBodySelected = { clickedCelestialBody ->
            if (
                (clickedCelestialBody.orbitCenter == CelestialBody.SUN || clickedCelestialBody.orbitCenter == null) &&
                (selectedCelestialBody == null || clickedCelestialBody == selectedCelestialBody)
            ) {
                selectedCelestialBody = if (selectedCelestialBody == clickedCelestialBody) null else clickedCelestialBody
            }
        }
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
    celestialBodyStates: List<CelestialBodyState>,
    selectedCelestialBody: CelestialBody?,
    onCelestialBodySelected: (CelestialBody) -> Unit
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color(0xFF503985))
) {
    StarField(
        windowSize = windowSize
    )
    val moonSelectedState = celestialBodyStates.firstOrNull { it.celestialBody == CelestialBody.MOON }?.alphaMultiplier ?: 1f
    celestialBodyStates.forEach { celestialBodyState ->
        if (celestialBodyState.celestialBody.orbitCenter != null) {
            Orbit(
                transitionProgress = moonSelectedState,
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

    celestialBodyStates
        .sortedBy { it.position.y }
        .sortedBy { selectedCelestialBody == it.celestialBody }
        .forEach { celestialBodyState ->
            CelestialBody(
                windowSize = windowSize,
                celestialBody = celestialBodyState.celestialBody,
                relativePosition = celestialBodyState.position,
                alphaMultiplier = celestialBodyState.alphaMultiplier,
                radiusMultiplier = celestialBodyState.radiusMultiplier,
                onCelestialBodySelected = onCelestialBodySelected
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
                color = orbitColor,
                style = Stroke(),
                alpha = transitionProgress
            )
        }
    )
}

@Composable
private fun CelestialBody(
    windowSize: IntSize,
    celestialBody: CelestialBody,
    relativePosition: Offset,
    alphaMultiplier: Float,
    radiusMultiplier: Float,
    onCelestialBodySelected: (CelestialBody) -> Unit
) {
    val baseRadius = min(windowSize.width, windowSize.height) / 10
    val unselectedRadius = baseRadius * celestialBody.sizeRadiusMultiplier * (1f - radiusMultiplier)
    val selectedRadius = baseRadius * 2f * radiusMultiplier
    val radius = (selectedRadius + unselectedRadius).dp
    val interactionSource = remember { MutableInteractionSource() }
    val unselectedX = (relativePosition.x * (1f - radiusMultiplier) * windowSize.width).dp
    val selectedX = (0.2f * radiusMultiplier * windowSize.width).dp
    val unselectedY = (relativePosition.y * (1f - radiusMultiplier) * windowSize.height).dp
    val selectedY = (0.5f * radiusMultiplier * windowSize.height).dp
    Image(
        modifier = Modifier
            .offset(
                x = unselectedX + selectedX - radius,
                y = unselectedY + selectedY - radius
            )
            .size(radius * 2)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onCelestialBodySelected(celestialBody) }
            ),
        contentScale = ContentScale.Fit,
        bitmap = celestialBody.asset,
        alpha = 0.1f.coerceAtLeast(alphaMultiplier),
        contentDescription = celestialBody.displayName
    )
}
