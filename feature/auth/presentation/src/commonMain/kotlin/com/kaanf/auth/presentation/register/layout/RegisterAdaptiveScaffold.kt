package com.kaanf.auth.presentation.register.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.component.layout.AuthHeaderSection
import com.kaanf.core.presentation.util.DeviceConfiguration
import com.kaanf.core.presentation.util.currentDeviceConfiguration

@Composable
fun RegisterAdaptiveScaffold(
    title: String,
    description: String,
    onBack: (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
    bottomBar: @Composable () -> Unit
) {
    when (currentDeviceConfiguration()) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    AuthHeaderSection(
                        headerText = title,
                        descriptionText = description,
                        onBack = onBack
                    )

                    Spacer(Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        content = content
                    )

                        bottomBar()
                }
            }
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        AuthHeaderSection(
                            headerText = title,
                            descriptionText = description,
                            onBack = onBack
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            content = content
                        )

                        bottomBar()
                    }
                }
            }
        }

        else -> Unit
    }
}
