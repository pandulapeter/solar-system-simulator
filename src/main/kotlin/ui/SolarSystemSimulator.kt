package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import data.CelestialBody
import data.CelestialBodyState
import data.RotationController
import kotlinx.coroutines.isActive
import ui.resources.Colors
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
//    Slider(
//        modifier = Modifier.fillMaxWidth(0.25f),
//        value = simulationSpeedMultiplier,
//        valueRange = 0f..2f,
//        onValueChange = { simulationSpeedMultiplier = it }
//    )
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
        .background(Colors.background1)
) {
    Background(
        windowSize = windowSize
    )
    val transitionProgress = celestialBodyStates.firstOrNull { it.celestialBody == CelestialBody.MOON }?.alphaMultiplier ?: 1f
    val modifiedWindowSize = IntSize(
        width = (windowSize.width * (0.5f + transitionProgress * 0.5f)).roundToInt(),
        height = windowSize.height
    )
    Orbits(
        windowSize = modifiedWindowSize,
        celestialBodyStates = celestialBodyStates,
        transitionProgress = transitionProgress
    )
    CelestialBodies(
        windowSize = modifiedWindowSize,
        celestialBodyStates = celestialBodyStates,
        selectedCelestialBody = selectedCelestialBody,
        onCelestialBodySelected = onCelestialBodySelected
    )
}