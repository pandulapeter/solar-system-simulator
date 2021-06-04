package data

import androidx.compose.ui.graphics.Color

enum class CelestialBody(
    val displayName: String, // Name of the celestial body as it appears on the UI
    val description: String, // Short description for the detail screen
    val color: Color, // The color used when drawing the celestial body
    val sizeRadiusMultiplier: Float, // The relative radius of the celestial body to the Sun
    val orbitRadiusMultiplier: Float, // The radius of the circular orbit, relative to the radius of the Solar system
    val orbitInitialOffset: Float, // The starting position (between 0f and 1f)
    val orbitRotationSpeedMultiplier: Float, // The speed of the orbit, relative to the simulation speed baseline
    val orbitCenter: CelestialBody? // Reference to a celestial body that the current one is rotating around
) {
    SUN(
        displayName = "Sun",
        description = "The center of the Solar System",
        color = Color.White,
        sizeRadiusMultiplier = 1f,
        orbitRadiusMultiplier = 0f,
        orbitInitialOffset = 0f,
        orbitRotationSpeedMultiplier = 2f,
        orbitCenter = null
    ),
    MERCURY(
        displayName = "Mercury",
        description = "The first planet",
        color = Color.LightGray,
        sizeRadiusMultiplier = 0.2f,
        orbitRadiusMultiplier = 0.1f,
        orbitInitialOffset = 0f,
        orbitRotationSpeedMultiplier = 2f,
        orbitCenter = SUN
    ),
    VENUS(
        displayName = "Venus",
        description = "The second planet",
        color = Color.Yellow,
        sizeRadiusMultiplier = 0.25f,
        orbitRadiusMultiplier = 0.18f,
        orbitInitialOffset = 0.1f,
        orbitRotationSpeedMultiplier = 2.5f,
        orbitCenter = SUN
    ),
    EARTH(
        displayName = "Earth",
        description = "The third planet",
        color = Color.Blue,
        sizeRadiusMultiplier = 0.4f,
        orbitRadiusMultiplier = 0.22f,
        orbitInitialOffset = 0.2f,
        orbitRotationSpeedMultiplier = 1f,
        orbitCenter = SUN
    ),
    MOON(
        displayName = "Moon",
        description = "The moon",
        color = Color.LightGray,
        sizeRadiusMultiplier = 0.15f,
        orbitRadiusMultiplier = 0.08f,
        orbitInitialOffset = 0.2f,
        orbitRotationSpeedMultiplier = 5f,
        orbitCenter = EARTH
    ),
    MARS(
        displayName = "Mars",
        description = "The fourth planet",
        color = Color.Red,
        sizeRadiusMultiplier = 0.4f,
        orbitRadiusMultiplier = 0.3f,
        orbitInitialOffset = 0.8f,
        orbitRotationSpeedMultiplier = -0.7f,
        orbitCenter = SUN
    ),
    JUPITER(
        displayName = "Jupiter",
        description = "The fifth planet",
        color = Color.Magenta,
        sizeRadiusMultiplier = 0.6f,
        orbitRadiusMultiplier = 0.4f,
        orbitInitialOffset = 0.5f,
        orbitRotationSpeedMultiplier = 0.3f,
        orbitCenter = SUN
    ),
    IO(
        displayName = "IO",
        description = "Jupiter's first moon",
        color = Color.Green,
        sizeRadiusMultiplier = 0.2f,
        orbitRadiusMultiplier = 0.08f,
        orbitInitialOffset = 0.5f,
        orbitRotationSpeedMultiplier = -0.9f,
        orbitCenter = JUPITER
    ),
    EUROPA(
        displayName = "Europa",
        description = "Jupiter's second moon",
        color = Color.DarkGray,
        sizeRadiusMultiplier = 0.18f,
        orbitRadiusMultiplier = 0.1f,
        orbitInitialOffset = 0.2f,
        orbitRotationSpeedMultiplier = 0.8f,
        orbitCenter = JUPITER
    )
}