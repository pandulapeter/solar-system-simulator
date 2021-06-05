package data

import ui.resources.Constants

class RotationController(celestialBodies: List<CelestialBody>) {

    private var previousTime = 0L
    private var celestialBodyPositions = celestialBodies.map { CelestialBodyState(it) }

    fun update(
        time: Long,
        selectedCelestialBody: CelestialBody?
    ): List<CelestialBodyState> {
        val deltaTime = ((time - previousTime) / BASE_SIMULATION_SPEED).toFloat()
        previousTime = time
        celestialBodyPositions = celestialBodyPositions.map { celestialBodyPosition ->
            celestialBodyPositions.firstOrNull {
                it.celestialBody == celestialBodyPosition.celestialBody.orbitCenter
            }.let { orbitingAround ->
                celestialBodyPosition.copyWithRotationOnPath(
                    frameRateMultiplier = deltaTime * Constants.celestialBodyRotationMultiplier,
                    orbitCenterRelativePosition = orbitingAround?.position ?: celestialBodyPosition.centerPosition,
                    selectedCelestialBody = selectedCelestialBody
                )
            }
        }
        return celestialBodyPositions
    }

    companion object {
        private const val BASE_SIMULATION_SPEED = 5E3
    }
}