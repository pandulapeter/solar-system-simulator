package data

import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class CelestialBodyState(
    val celestialBody: CelestialBody,
    val relativePosition: Offset = Offset(0.5f, 0.5f),
    private val orbitProgress: Float = celestialBody.orbitInitialProgress
) {
    val position = Offset(
        x = relativePosition.x + cos(PI * 2f * orbitProgress).toFloat() * celestialBody.orbitRadiusMultiplier * 0.5f,
        y = relativePosition.y + sin(PI * 2f * orbitProgress).toFloat() * celestialBody.orbitRadiusMultiplier * 0.5f
    )

    fun copyWithRotationOnPath(
        amount: Float,
        orbitCenterRelativePosition: Offset
    ): CelestialBodyState {
        var newOffset = orbitProgress + (celestialBody.orbitRotationSpeedMultiplier * amount)
        while (newOffset >= 1f) {
            newOffset -= 1f
        }
        while (newOffset < 0f) {
            newOffset += 1f
        }
        return copy(
            orbitProgress = newOffset,
            relativePosition = orbitCenterRelativePosition
        )
    }
}