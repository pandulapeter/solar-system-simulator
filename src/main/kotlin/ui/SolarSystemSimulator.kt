package ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.lang.Integer.min
import kotlin.math.roundToInt

@Composable
fun SolarSystemSimulator(
    windowSize: IntSize,
    rotationController: RotationController,
    animationScopes: Map<CelestialBody, CoroutineScope>
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
        celestialBodyPositions = celestialBodyPositions,
        animationScopes = animationScopes
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
    celestialBodyPositions: List<CelestialBodyPosition>,
    animationScopes: Map<CelestialBody, CoroutineScope>
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

    // TODO: Bad code. very bad.
    var selectedCelestialBody by remember { mutableStateOf<CelestialBody?>(null) }
    val radiusMultiplierAnimatables = remember { CelestialBody.values().map { it to Animatable(1f) }.toMap() }

    celestialBodyPositions.sortedBy { it.multiplierOffset.y }.forEach { celestialBodyWrapper ->
        CelestialBody(
            windowSize = windowSize,
            celestialBody = celestialBodyWrapper.celestialBody,
            multiplierOffset = celestialBodyWrapper.multiplierOffset,
            radiusMultiplier = radiusMultiplierAnimatables[celestialBodyWrapper.celestialBody]?.value ?: 1f,
            selectedCelestialBody = selectedCelestialBody,
            onCelestialBodySelected = { celestialBody ->
                if (selectedCelestialBody!=null) {
                    val oldAnimationScope = animationScopes[selectedCelestialBody]!!
                    val oldAnimatable = radiusMultiplierAnimatables[selectedCelestialBody]!!
                    if (celestialBody != selectedCelestialBody) {
                        oldAnimationScope.launch {
                            oldAnimatable.animateTo(1f)
                        }
                    }
                }
                val animationScope = animationScopes[celestialBodyWrapper.celestialBody]!!
                val animatable = radiusMultiplierAnimatables[celestialBodyWrapper.celestialBody]!!
                animationScope.launch {
                    animatable.animateTo(if (celestialBody == celestialBodyWrapper.celestialBody) 4f else 1f)
                }
                selectedCelestialBody = celestialBody
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
    multiplierOffset: Offset,
    radiusMultiplier: Float,
    selectedCelestialBody: CelestialBody?,
    onCelestialBodySelected: (CelestialBody?) -> Unit
) {
    val radius = (min(windowSize.width, windowSize.height) / 10 * celestialBody.sizeRadiusMultiplier).dp * radiusMultiplier
    Image(
        modifier = Modifier
            .offset(
                x = windowSize.width.dp * multiplierOffset.x - radius,
                y = windowSize.height.dp * multiplierOffset.y - radius
            )
            .size(radius * 2)
            .clickable {
                onCelestialBodySelected(if (selectedCelestialBody == celestialBody) null else celestialBody)
            },
        contentScale = ContentScale.Fit,
        bitmap = celestialBody.asset,
        contentDescription = celestialBody.displayName
    )
}
