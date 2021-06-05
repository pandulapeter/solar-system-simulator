package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntSize
import data.CelestialBody
import data.CelestialBodyState
import data.RotationController
import kotlinx.coroutines.isActive

@Composable
fun SolarSystemSimulator(
    windowSize: IntSize,
    rotationController: RotationController
) {
    var celestialBodyStates by remember { mutableStateOf(emptyList<CelestialBodyState>()) }
    var selectedCelestialBody by remember { mutableStateOf<CelestialBody?>(null) }
    var lastSelectedCelestialBodyName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (isActive) {
            withFrameMillis { time ->
                celestialBodyStates = rotationController.update(
                    time = time,
                    selectedCelestialBody = selectedCelestialBody
                )
            }
        }
    }
    val transitionProgress = celestialBodyStates.firstOrNull { it.celestialBody == CelestialBody.MOON }?.alphaMultiplier ?: 1f
    SolarSystem(
        windowSize = windowSize,
        transitionProgress = transitionProgress,
        celestialBodyStates = celestialBodyStates,
        selectedCelestialBody = selectedCelestialBody,
        onCelestialBodySelected = { clickedCelestialBody ->
            if (
                (clickedCelestialBody.orbitCenter == CelestialBody.SUN || clickedCelestialBody.orbitCenter == null) &&
                (selectedCelestialBody == null || clickedCelestialBody == selectedCelestialBody)
            ) {
                selectedCelestialBody = if (selectedCelestialBody == clickedCelestialBody) null else clickedCelestialBody
                selectedCelestialBody?.let {
                    lastSelectedCelestialBodyName = it.displayName
                }
            }
        },
        onCelestialBackgroundClicked = {
            selectedCelestialBody = null
        }
    )
    Interface(
        windowSize = windowSize,
        transitionProgress = transitionProgress,
        lastSelectedCelestialBodyName = lastSelectedCelestialBodyName
    )
}

@Composable
private fun SolarSystem(
    windowSize: IntSize,
    transitionProgress: Float,
    celestialBodyStates: List<CelestialBodyState>,
    selectedCelestialBody: CelestialBody?,
    onCelestialBodySelected: (CelestialBody) -> Unit,
    onCelestialBackgroundClicked: () -> Unit
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onCelestialBackgroundClicked
        )
) {
    Background(
        windowSize = windowSize
    )
    val modifiedWindowSize = Size(
        width = windowSize.width * (0.5f + transitionProgress * 0.5f),
        height = windowSize.height.toFloat()
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