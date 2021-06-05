package data

class RotationController(celestialBodies: List<CelestialBody>) {

    private var previousTime = 0L
    private var celestialBodyPositions = celestialBodies.map { CelestialBodyState(it) }

    fun update(
        time: Long,
        simulationSpeedMultiplier: Float,
        selectedCelestialBody: CelestialBody?
    ): List<CelestialBodyState> {
        val deltaTime = ((time - previousTime) / BASE_SIMULATION_SPEED).toFloat()
        previousTime = time
        celestialBodyPositions = celestialBodyPositions.map { celestialBodyPosition ->
            celestialBodyPositions.firstOrNull {
                it.celestialBody == celestialBodyPosition.celestialBody.orbitCenter
            }.let { orbitingAround ->
                celestialBodyPosition.copyWithRotationOnPath(
                    frameRateMultiplier = deltaTime * simulationSpeedMultiplier,
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