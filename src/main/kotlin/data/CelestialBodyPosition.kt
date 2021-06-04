package data

import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class CelestialBodyPosition(
    val celestialBody: CelestialBody,
    val orbitCenterOffsetMultiplier: Offset = Offset(0.5f, 0.5f),
    private val currentPathRotationProgress: Float = celestialBody.orbitInitialProgress
) {
    val multiplierOffset = Offset(
        x = orbitCenterOffsetMultiplier.x + cos(PI * 2f * currentPathRotationProgress).toFloat() * celestialBody.orbitRadiusMultiplier * 0.5f,
        y = orbitCenterOffsetMultiplier.y + sin(PI * 2f * currentPathRotationProgress).toFloat() * celestialBody.orbitRadiusMultiplier * 0.5f
    )

    fun copyWithRotationOnPath(
        amount: Float,
        orbitCenterOffsetMultiplier: Offset
    ): CelestialBodyPosition {
        var newOffset = currentPathRotationProgress + (celestialBody.orbitRotationSpeedMultiplier * amount)
        while (newOffset >= 1f) {
            newOffset -= 1f
        }
        while (newOffset < 0f) {
            newOffset += 1f
        }
        return copy(
            currentPathRotationProgress = newOffset,
            orbitCenterOffsetMultiplier = orbitCenterOffsetMultiplier
        )
    }
}