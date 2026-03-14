package com.kaanf.core.designsystem.component.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.OrisTheme
import com.kaanf.core.designsystem.theme.Primary400
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrisGlowIcon(
    icon: ImageVector,
    themeColor: Color,
    modifier: Modifier = Modifier,
    iconTintColor: Color = Color.White,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(180.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        brush =
                            Brush.radialGradient(
                                colors =
                                    listOf(
                                        themeColor.copy(alpha = 0.60f),
                                        Color.Transparent,
                                    ),
                                radius = 250f,
                            ),
                        shape = CircleShape,
                    ),
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(themeColor),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier.size(48.dp),
            )
        }
    }
}

@Composable
@Preview
fun OrisGlowIconPreview() {
    OrisTheme {
        OrisGlowIcon(
            icon = Icons.Default.Email,
            themeColor = Primary400,
        )
    }
}
