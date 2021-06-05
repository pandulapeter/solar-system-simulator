package ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import ui.resources.Colors

@Composable
fun Interface(
    windowSize: IntSize,
    transitionProgress: Float,
    lastSelectedCelestialBodyName: String
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .scale(1f - transitionProgress * 0.5f)
            .padding(
                horizontal = (windowSize.width * 0.05f).dp,
                vertical = (windowSize.height * 0.2f).dp,
            ),
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = lastSelectedCelestialBodyName,
            color = Colors.title.copy(alpha = 1f - transitionProgress),
            fontSize = LocalDensity.current.run {
                min(
                    MaterialTheme.typography.h4.fontSize.value.dp,
                    maxWidth / lastSelectedCelestialBodyName.length
                ).toSp()
            },
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}