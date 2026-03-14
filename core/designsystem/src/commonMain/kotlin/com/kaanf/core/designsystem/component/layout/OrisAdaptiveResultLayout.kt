package com.kaanf.core.designsystem.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.component.button.OrisButton
import com.kaanf.core.designsystem.component.button.OrisButtonStyle
import com.kaanf.core.designsystem.component.icon.OrisGlowIcon
import com.kaanf.core.designsystem.theme.OrisTheme
import com.kaanf.core.designsystem.theme.Primary400
import com.kaanf.core.presentation.util.DeviceConfiguration
import com.kaanf.core.presentation.util.currentDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrisAdaptiveResultLayout(
    content: @Composable ColumnScope.() -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val configuration = currentDeviceConfiguration()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        if (configuration == DeviceConfiguration.MOBILE_PORTRAIT) {
            Column(
                modifier =
                    Modifier
                        .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.weight(.6f))

                content()

                Spacer(modifier = Modifier.weight(1f))

                bottomBar()

                Spacer(modifier = Modifier.height(24.dp))
            }
        } else {
            Column(
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                content()
            }
        }
    }
}

@Composable
@Preview
fun OrisAdaptiveResultLayoutPreview() {
    OrisTheme(isDarkTheme = true) {
        OrisAdaptiveResultLayout(
            content = {
                OrisSimpleResultLayout(
                    title = "Account created!",
                    description = "We've sent an email verification link to your registered email address. Please check your inbox.",
                    icon = {
                        OrisGlowIcon(
                            icon = Icons.Default.Email,
                            themeColor = Primary400,
                        )
                    },
                )
            },
            bottomBar = {
                OrisButton(
                    text = "Devam",
                    onClick = {},
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(12.dp))

                OrisButton(
                    text = "Devam",
                    onClick = {},
                    style = OrisButtonStyle.SECONDARY,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                )
            },
        )
    }
}
