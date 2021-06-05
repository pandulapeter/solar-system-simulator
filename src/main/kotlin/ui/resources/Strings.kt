package ui.resources

object Strings {

    sealed class CelestialBody {
        abstract val displayName: String
        abstract val description: String

        object Sun : CelestialBody() {
            override val displayName = "Sun"
            override val description = "TODO"
        }

        object Mercury : CelestialBody() {
            override val displayName = "Mercury"
            override val description = "TODO"
        }

        object Venus : CelestialBody() {
            override val displayName = "Venus"
            override val description = "TODO"
        }

        object Earth : CelestialBody() {
            override val displayName = "Earth"
            override val description = "TODO"
        }

        object Mars : CelestialBody() {
            override val displayName = "Mars"
            override val description = "TODO"
        }

        object Jupiter : CelestialBody() {
            override val displayName = "Jupiter"
            override val description = "TODO"
        }

        object Saturn : CelestialBody() {
            override val displayName = "Saturn"
            override val description = "TODO"
        }

        object Uranus : CelestialBody() {
            override val displayName = "Uranus"
            override val description = "TODO"
        }

        object Neptune : CelestialBody() {
            override val displayName = "Neptune"
            override val description = "TODO"
        }
    }
}