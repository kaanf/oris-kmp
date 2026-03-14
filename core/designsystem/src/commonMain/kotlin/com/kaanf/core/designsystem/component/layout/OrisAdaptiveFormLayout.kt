package com.kaanf.core.designsystem.component.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.component.button.OrisIconButton
import com.kaanf.core.designsystem.component.textfield.OrisTextField
import com.kaanf.core.designsystem.theme.OrisTheme
import com.kaanf.core.designsystem.theme.extended
import com.kaanf.core.presentation.util.DeviceConfiguration
import com.kaanf.core.presentation.util.currentDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrisAdaptiveFormLayout(
    headerText: String,
    descriptionText: String,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
    bottomBar: @Composable () -> Unit = {},
) {
    val configuration = currentDeviceConfiguration()

    when (configuration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            OrisSurface(
                modifier = modifier,
                header = {
                    AuthHeaderSection(
                        headerText = headerText,
                        descriptionText = descriptionText,
                        onBack = onBack,
                    )
                },
                content = {
                    content()
                },
                bottomBar = {
                    bottomBar()
                },
            )
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier =
                    modifier
                        .fillMaxSize()
                        .consumeWindowInsets(WindowInsets.displayCutout)
                        .consumeWindowInsets(WindowInsets.navigationBars),
            ) {
                Column(
                    modifier =
                        Modifier
                            .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    AuthHeaderSection(
                        headerText = headerText,
                        descriptionText = descriptionText,
                    )
                }

                OrisSurface(
                    modifier =
                        Modifier
                            .weight(1f),
                    content = {
                        content()
                    },
                    bottomBar = {
                        bottomBar()
                    },
                )
            }
        }

        else -> Unit
    }
}

@Composable
fun AuthHeaderSection(
    headerText: String,
    descriptionText: String,
    onBack: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier.size(48.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (onBack != null) {
            OrisIconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    }

    Spacer(Modifier.height(12.dp))

    Text(
        text = headerText,
        color = MaterialTheme.colorScheme.extended.primaryTextColor,
        style = MaterialTheme.typography.displaySmall,
    )

    Spacer(Modifier.height(8.dp))

    Text(
        text = descriptionText,
        maxLines = 2,
        minLines = 2,
        color = MaterialTheme.colorScheme.extended.secondaryTextColor,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
@Preview
fun OrisAdaptiveFormLayoutPreview() {
    OrisTheme(isDarkTheme = true) {
        OrisAdaptiveFormLayout(
            headerText = "What’s your name?",
            descriptionText = "Write your name. You can change it back in settings.",
            onBack = null,
            content = {
                OrisTextField(
                    state = rememberTextFieldState(),
                    placeholder = "test@test.com",
                    title = "Email",
                )
            },
        )
    }
}

@Composable
@Preview
fun OrisAdaptiveFormLayoutLightPreview() {
    OrisTheme(isDarkTheme = false) {
        OrisAdaptiveFormLayout(
            headerText = "What’s your name?",
            descriptionText = "Write your name. You can change it back in settings.",
            onBack = null,
            content = {
                OrisTextField(
                    state = rememberTextFieldState(),
                    placeholder = "test@test.com",
                    title = "Email",
                )
            },
        )
    }
}
