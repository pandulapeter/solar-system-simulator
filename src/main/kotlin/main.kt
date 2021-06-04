import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import data.CelestialBody
import data.RotationController
import ui.SolarSystemApplication

fun main() {

    val windowSize = mutableStateOf(IntSize.Zero)
    val rotationController = RotationController(CelestialBody.values().toList())

    Window(
        events = WindowEvents(
            onResize = { windowSize.value = it }
        ),
        size = IntSize(640, 360),
        title = "Solar System Simulator"
    ) {
        SolarSystemApplication(windowSize.value, rotationController)
    }
}