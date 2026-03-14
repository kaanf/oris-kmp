package com.kaanf.core.designsystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.OrisTheme
import com.kaanf.core.designsystem.theme.Primary100
import com.kaanf.core.designsystem.theme.SquircleCornerShape
import com.kaanf.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class OrisButtonStyle {
    PRIMARY,
    SECONDARY,
}

@Composable
fun OrisButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: OrisButtonStyle = OrisButtonStyle.PRIMARY,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val targetContainerColor = when(style) {
        OrisButtonStyle.PRIMARY -> if (enabled) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.extended.disabledFill
        }
        OrisButtonStyle.SECONDARY -> if (enabled) {
            Primary100
        } else {
            MaterialTheme.colorScheme.extended.disabledFill
        }
    }
    val targetContentColor = when(style) {
        OrisButtonStyle.PRIMARY -> if (enabled) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.extended.textDisabled
        }
        OrisButtonStyle.SECONDARY -> if (enabled) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.extended.textDisabled
        }
    }
    val animatedContainerColor by animateColorAsState(
        targetValue = targetContainerColor,
        animationSpec = tween(durationMillis = 200),
        label = "oris_button_container_color"
    )
    val animatedContentColor by animateColorAsState(
        targetValue = targetContentColor,
        animationSpec = tween(durationMillis = 200),
        label = "oris_button_content_color"
    )
    val colors = ButtonDefaults.buttonColors(
        containerColor = animatedContainerColor,
        contentColor = animatedContentColor,
        disabledContainerColor = animatedContainerColor,
        disabledContentColor = animatedContentColor
    )

    /*
    val defaultBorderStroke = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.extended.disabledOutline
    )

    val border = when {
        style == OrisButtonStyle.PRIMARY && !enabled -> defaultBorderStroke
        style == OrisButtonStyle.SECONDARY -> defaultBorderStroke
        else -> null
    }
     */

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = SquircleCornerShape(12.dp),
        colors = colors,
        border = null
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(
                        alpha = if(isLoading) 1f else 0f
                    ),
                strokeWidth = 1.5.dp,
                color = animatedContentColor
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(
                    if(isLoading) 0f else 1f
                )
            ) {
                leadingIcon?.invoke()
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
@Preview
fun OrisPrimaryButtonPreview() {
    OrisTheme(isDarkTheme = true) {
        OrisButton(
            text = "Hello world!",
            onClick = {},
            style = OrisButtonStyle.PRIMARY
        )
    }
}

@Composable
@Preview
fun OrisSecondaryButtonPreview() {
    OrisTheme(isDarkTheme = true) {
        OrisButton(
            text = "Hello world!",
            onClick = {},
            style = OrisButtonStyle.SECONDARY
        )
    }
}
