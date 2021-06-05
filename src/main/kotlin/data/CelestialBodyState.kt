package data

import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class CelestialBodyState(
    val celestialBody: CelestialBody,
    val centerPosition: Offset = Offset(0.5f, 0.5f),
    val alphaMultiplier: Float = 1f,
    val scaleMultiplier: Float = 1f,
    private val orbitProgress: Float = celestialBody.orbitInitialProgress
) {
    val position = Offset(
        x = centerPosition.x + cos(PI * 2f * orbitProgress).toFloat() * celestialBody.orbitRadiusMultiplier * 0.5f,
        y = centerPosition.y + sin(PI * 2f * orbitProgress).toFloat() * celestialBody.orbitRadiusMultiplier * 0.5f
    )

    fun copyWithRotationOnPath(
        frameRateMultiplier: Float,
        orbitCenterRelativePosition: Offset,
        selectedCelestialBody: CelestialBody?
    ): CelestialBodyState {
        var newOffset = orbitProgress + (celestialBody.orbitRotationSpeedMultiplier * frameRateMultiplier)
        while (newOffset >= 1f) {
            newOffset -= 1f
        }
        while (newOffset < 0f) {
            newOffset += 1f
        }
        return copy(
            orbitProgress = newOffset,
            centerPosition = orbitCenterRelativePosition,
            alphaMultiplier = if (selectedCelestialBody == null || selectedCelestialBody == celestialBody) {
                1f.coerceAtMost(alphaMultiplier + ANIMATION_SPEED * frameRateMultiplier)
            } else {
                0f.coerceAtLeast(alphaMultiplier - ANIMATION_SPEED * frameRateMultiplier)
            },
            scaleMultiplier = if (selectedCelestialBody == celestialBody) {
                1f.coerceAtMost(scaleMultiplier + ANIMATION_SPEED * frameRateMultiplier)
            } else {
                0f.coerceAtLeast(scaleMultiplier - ANIMATION_SPEED * frameRateMultiplier)
            }
        )
    }

    companion object {
        private const val ANIMATION_SPEED = 90f
    }
}