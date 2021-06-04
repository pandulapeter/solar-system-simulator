package data

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.imageFromResource

enum class CelestialBody(
    val displayName: String, // Name of the celestial body as it appears on the UI
    val description: String, // Short description for the detail screen
    val asset: ImageBitmap, // The bitmap image asset for the celestial body
    val sizeRadiusMultiplier: Float, // The relative radius of the celestial body to the Sun
    val orbitRadiusMultiplier: Float, // The radius of the circular orbit, relative to the radius of the Solar system
    val orbitInitialProgress: Float, // The starting position (between 0f and 1f)
    val orbitRotationSpeedMultiplier: Float, // The speed of the orbit, relative to the simulation speed baseline
    val orbitCenter: CelestialBody? // Reference to a celestial body that the current one is rotating around
) {
    SUN(
        displayName = "Sun",
        description = "The center of the Solar System",
        asset = imageFromResource("images/sun.webp"),
        sizeRadiusMultiplier = 1f,
        orbitRadiusMultiplier = 0f,
        orbitInitialProgress = 0f,
        orbitRotationSpeedMultiplier = 2f,
        orbitCenter = null
    ),
    MERCURY(
        displayName = "Mercury",
        description = "The first planet",
        asset = imageFromResource("images/mercury.webp"),
        sizeRadiusMultiplier = 0.2f,
        orbitRadiusMultiplier = 0.15f,
        orbitInitialProgress = 0f,
        orbitRotationSpeedMultiplier = 2f,
        orbitCenter = SUN
    ),
    VENUS(
        displayName = "Venus",
        description = "The second planet",
        asset = imageFromResource("images/venus.webp"),
        sizeRadiusMultiplier = 0.25f,
        orbitRadiusMultiplier = 0.2f,
        orbitInitialProgress = 0.5f,
        orbitRotationSpeedMultiplier = 2.5f,
        orbitCenter = SUN
    ),
    EARTH(
        displayName = "Earth",
        description = "The third planet",
        asset = imageFromResource("images/earth.webp"),
        sizeRadiusMultiplier = 0.4f,
        orbitRadiusMultiplier = 0.25f,
        orbitInitialProgress = 0.25f,
        orbitRotationSpeedMultiplier = 1f,
        orbitCenter = SUN
    ),
    MOON(
        displayName = "Moon",
        description = "Earth's only moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.1f,
        orbitRadiusMultiplier = 0.06f,
        orbitInitialProgress = 0.2f,
        orbitRotationSpeedMultiplier = 5f,
        orbitCenter = EARTH
    ),
    MARS(
        displayName = "Mars",
        description = "The fourth planet",
        asset = imageFromResource("images/mars.webp"),
        sizeRadiusMultiplier = 0.4f,
        orbitRadiusMultiplier = 0.3f,
        orbitInitialProgress = 0.75f,
        orbitRotationSpeedMultiplier = 0.7f,
        orbitCenter = SUN
    ),
    MARS_MOON_1(
        displayName = "Mars Moon 1",
        description = "Mars's first moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.08f,
        orbitRadiusMultiplier = 0.06f,
        orbitInitialProgress = 0.2f,
        orbitRotationSpeedMultiplier = 5f,
        orbitCenter = MARS
    ),
    MARS_MOON_2(
        displayName = "Mars Moon 2",
        description = "Mars's second moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.06f,
        orbitRadiusMultiplier = 0.08f,
        orbitInitialProgress = 0.8f,
        orbitRotationSpeedMultiplier = 5f,
        orbitCenter = MARS
    ),
    JUPITER(
        displayName = "Jupiter",
        description = "The fifth planet",
        asset = imageFromResource("images/jupiter.webp"),
        sizeRadiusMultiplier = 0.7f,
        orbitRadiusMultiplier = 0.38f,
        orbitInitialProgress = 0.4f,
        orbitRotationSpeedMultiplier = 0.3f,
        orbitCenter = SUN
    ),
    JUPITER_MOON_1(
        displayName = "Jupiter Moon 1",
        description = "Jupiter's first moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.1f,
        orbitRadiusMultiplier = 0.16f,
        orbitInitialProgress = 0f,
        orbitRotationSpeedMultiplier = 5f,
        orbitCenter = JUPITER
    ),
    JUPITER_MOON_2(
        displayName = "Jupiter Moon 2",
        description = "Jupiter's second moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.12f,
        orbitRadiusMultiplier = 0.18f,
        orbitInitialProgress = 0.33f,
        orbitRotationSpeedMultiplier = 5f,
        orbitCenter = JUPITER
    ),
    JUPITER_MOON_3(
        displayName = "Jupiter Moon 3",
        description = "Jupiter's third moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.08f,
        orbitRadiusMultiplier = 0.14f,
        orbitInitialProgress = 0.66f,
        orbitRotationSpeedMultiplier = 5f,
        orbitCenter = JUPITER
    ),
    SATURN(
        displayName = "Saturn",
        description = "The sixth plane",
        asset = imageFromResource("images/saturn.webp"),
        sizeRadiusMultiplier = 0.6f,
        orbitRadiusMultiplier = 0.5f,
        orbitInitialProgress = 0.3f,
        orbitRotationSpeedMultiplier = 0.9f,
        orbitCenter = SUN
    ),
    SATURN_MOON_1(
        displayName = "Saturn Moon 1",
        description = "Saturn's first moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.1f,
        orbitRadiusMultiplier = 0.1f,
        orbitInitialProgress = 0f,
        orbitRotationSpeedMultiplier = -1f,
        orbitCenter = SATURN
    ),
    SATURN_MOON_2(
        displayName = "Saturn Moon 2",
        description = "Saturn's second moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.08f,
        orbitRadiusMultiplier = 0.12f,
        orbitInitialProgress = 0.5f,
        orbitRotationSpeedMultiplier = -0.8f,
        orbitCenter = SATURN
    ),
    SATURN_MOON_3(
        displayName = "Saturn Moon 3",
        description = "Saturn's third moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.05f,
        orbitRadiusMultiplier = 0.15f,
        orbitInitialProgress = 0.6f,
        orbitRotationSpeedMultiplier = 0.5f,
        orbitCenter = SATURN
    ),
    URANUS(
        displayName = "Uranus",
        description = "The seventh planet",
        asset = imageFromResource("images/uranus.webp"),
        sizeRadiusMultiplier = 0.55f,
        orbitRadiusMultiplier = 0.58f,
        orbitInitialProgress = 0.2f,
        orbitRotationSpeedMultiplier = 0.6f,
        orbitCenter = SUN
    ),
    URANUS_MOON_1(
        displayName = "Uranus Moon 1",
        description = "Uranus's first moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.1f,
        orbitRadiusMultiplier = 0.08f,
        orbitInitialProgress = 0f,
        orbitRotationSpeedMultiplier = 2f,
        orbitCenter = URANUS
    ),
    URANUS_MOON_2(
        displayName = "Uranus Moon 2",
        description = "Uranus's second moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.09f,
        orbitRadiusMultiplier = 0.08f,
        orbitInitialProgress = 0.2f,
        orbitRotationSpeedMultiplier = 2f,
        orbitCenter = URANUS
    ),
    URANUS_MOON_3(
        displayName = "Uranus Moon 3",
        description = "Uranus's third moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.06f,
        orbitRadiusMultiplier = 0.1f,
        orbitInitialProgress = 0.4f,
        orbitRotationSpeedMultiplier = 2f,
        orbitCenter = URANUS
    ),
    URANUS_MOON_4(
        displayName = "Uranus Moon 4",
        description = "Uranus's fourth moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.07f,
        orbitRadiusMultiplier = 0.14f,
        orbitInitialProgress = 0.6f,
        orbitRotationSpeedMultiplier = -2f,
        orbitCenter = URANUS
    ),
    URANUS_MOON_5(
        displayName = "Uranus Moon 5",
        description = "Uranus's fifth moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.05f,
        orbitRadiusMultiplier = 0.18f,
        orbitInitialProgress = 0.8f,
        orbitRotationSpeedMultiplier = 2f,
        orbitCenter = URANUS
    ),
    NEPTUNE(
        displayName = "Neptune",
        description = "The eighth planet",
        asset = imageFromResource("images/neptune.webp"),
        sizeRadiusMultiplier = 0.5f,
        orbitRadiusMultiplier = 0.7f,
        orbitInitialProgress = 0.7f,
        orbitRotationSpeedMultiplier = 0.2f,
        orbitCenter = SUN
    ),
    NEPTUNE_MOON_1(
        displayName = "Uranus Moon 1",
        description = "Uranus's first moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.08f,
        orbitRadiusMultiplier = 0.18f,
        orbitInitialProgress = 0.8f,
        orbitRotationSpeedMultiplier = 0.6f,
        orbitCenter = NEPTUNE
    ),
    NEPTUNE_MOON_2(
        displayName = "Uranus Moon 2",
        description = "Uranus's second moon",
        asset = imageFromResource("images/moon.webp"),
        sizeRadiusMultiplier = 0.05f,
        orbitRadiusMultiplier = 0.13f,
        orbitInitialProgress = 0.3f,
        orbitRotationSpeedMultiplier = 0.6f,
        orbitCenter = NEPTUNE
    )
}