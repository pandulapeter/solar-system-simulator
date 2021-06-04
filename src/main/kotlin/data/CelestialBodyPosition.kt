package data

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class CelestialBodyPosition(
    val celestialBody: CelestialBody,
    val orbitCenterXMultiplier: Float = 0.5f,
    val orbitCenterYMultiplier: Float = 0.5f,
    private val currentPathRotationOffset: Float = celestialBody.orbitInitialOffset
) {
    val xMultiplier = orbitCenterXMultiplier + cos(PI * 2f * currentPathRotationOffset).toFloat() * celestialBody.orbitRadiusMultiplier * 0.5f
    val yMultiplier = orbitCenterYMultiplier + sin(PI * 2f * currentPathRotationOffset).toFloat() * celestialBody.orbitRadiusMultiplier * 0.5f

    fun copyWithRotationOnPath(
        amount: Float,
        orbitCenterXMultiplier: Float,
        orbitCenterYMultiplier: Float
    ): CelestialBodyPosition {
        var newOffset = currentPathRotationOffset + (celestialBody.orbitRotationSpeedMultiplier * amount)
        while (newOffset >= 1f) {
            newOffset -= 1f
        }
        while (newOffset < 0f) {
            newOffset += 1f
        }
        return copy(
            currentPathRotationOffset = newOffset,
            orbitCenterXMultiplier = orbitCenterXMultiplier,
            orbitCenterYMultiplier = orbitCenterYMultiplier
        )
    }
}