package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ui.resources.Colors

@Composable
fun Interface(
    windowSize: IntSize,
    transitionProgress: Float,
    lastSelectedCelestialBodyName: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .scale(1f - transitionProgress * 0.5f)
            .padding(
                horizontal = (windowSize.width * 0.05f).dp,
                vertical = (windowSize.height * 0.2f).dp,
            ),
        color = Colors.title.copy(alpha = 1f - transitionProgress),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h4,
        text = lastSelectedCelestialBodyName
    )
}