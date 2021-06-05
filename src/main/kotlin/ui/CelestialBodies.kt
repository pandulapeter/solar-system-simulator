package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import data.CelestialBody
import data.CelestialBodyState
import kotlin.math.pow

@Composable
fun CelestialBodies(
    windowSize: IntSize,
    celestialBodyStates: List<CelestialBodyState>,
    selectedCelestialBody: CelestialBody?,
    onCelestialBodySelected: (CelestialBody) -> Unit
) {
    celestialBodyStates
        .sortedBy { it.position.y }
        .sortedBy { selectedCelestialBody == it.celestialBody }
        .forEach { celestialBodyState ->
            CelestialBody(
                windowSize = windowSize,
                celestialBody = celestialBodyState.celestialBody,
                relativePosition = celestialBodyState.position,
                alphaMultiplier = celestialBodyState.alphaMultiplier,
                scaleMultiplier = celestialBodyState.scaleMultiplier.pow(2),
                onCelestialBodySelected = onCelestialBodySelected
            )
        }
}

@Composable
private fun CelestialBody(
    windowSize: IntSize,
    celestialBody: CelestialBody,
    relativePosition: Offset,
    alphaMultiplier: Float,
    scaleMultiplier: Float,
    onCelestialBodySelected: (CelestialBody) -> Unit
) {
    val baseRadius = Integer.min(windowSize.width, windowSize.height) / 10
    val unselectedRadius = baseRadius * celestialBody.sizeRadiusMultiplier * (1f - scaleMultiplier)
    val selectedRadius = baseRadius * 2f * scaleMultiplier
    val radius = (selectedRadius + unselectedRadius).dp
    val interactionSource = remember { MutableInteractionSource() }
    val unselectedX = (relativePosition.x * (1f - scaleMultiplier) * windowSize.width).dp
    val selectedX = (0.5f * scaleMultiplier * windowSize.width).dp
    val unselectedY = (relativePosition.y * (1f - scaleMultiplier) * windowSize.height).dp
    val selectedY = (0.6f * scaleMultiplier * windowSize.height).dp
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